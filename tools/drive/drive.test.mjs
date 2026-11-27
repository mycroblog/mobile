// Tests of the app-driving tool's client without a device: the OS commands and runner requests each step makes,
// and what a failing step says. `scripts/kit check` runs them with `node --test`.
import assert from 'node:assert/strict';
import { test } from 'node:test';
import { STEPS, main } from './drive.mjs';
import { ios } from './ios.mjs';
import { android } from './android.mjs';

const SCREEN = {
  role: 'Screen',
  children: [{ role: 'Application', label: 'Demo', states: ['focused'], children: [{ role: 'Button', label: 'Compose', frame: [10, 20, 30, 40] }] },
    { role: 'Other', label: 'Hi', id: 'NotificationShortLookView' }],
};
const PNG = Buffer.from('png').toString('base64');

function fakeDeps({ state, sh = () => '', send } = {}) {
  const calls = { sh: [], send: [], files: {} };
  const states = { ...state };
  const deps = {
    root: '/repo',
    env: { HOME: '/home', ANDROID_HOME: '/sdk' },
    stateDir: '/state',
    sh(cmd, args, opts) {
      calls.sh.push([cmd, ...args].join(' '));
      return sh(cmd, args, opts) ?? '';
    },
    spawnDetached: (cmd, args) => (calls.sh.push(`spawn ${[cmd, ...args].join(' ')}`), 4242),
    alive: () => true,
    kill: () => {},
    async send(port, request) {
      calls.send.push(request);
      return send ? send(request) : { ok: true, tree: SCREEN, screenshot: PNG };
    },
    waitForRunner: async () => {},
    readState: (name) => states[name] ?? null,
    writeState: (name, s) => (states[name] = s),
    writeFile: (file, data) => (calls.files[file] = data),
    now: () => new Date('2026-11-27T00:00:00Z'),
    out: (s) => (calls.out = (calls.out ?? '') + s),
    err: (s) => (calls.err = (calls.err ?? '') + s),
  };
  return { deps, calls, states };
}

const IOS = { ios: { kind: 'phone', device: 'UDID', port: 22087, runnerPid: 1, app: 'dev.demo' } };
const IOS_DUO = { ios: { ...IOS.ios, kind: 'duo' } };
const IOS_IPAD = { ios: { ...IOS.ios, kind: 'ipad' } };
const AND = { android: { kind: 'phone', device: 'emulator-5600', port: 9018, runnerPid: 1, app: 'dev.demo' } };
const AND_FOLD = { android: { ...AND.android, kind: 'fold' } };
const LAUNCHCTL = '123\t0\tUIKitApplication:dev.demo[abcd][rb-legacy]\n';
const androidSh = (cmd, args) => {
  const line = args.join(' ');
  if (line.includes('resolve-activity')) return 'priority=0\ndev.demo/.MainActivity\n';
  if (line.includes('pidof')) return '321\n';
  if (line.includes('mCurrentFocus')) return androidSh.focus.shift() ?? '';
  return '';
};

async function run(argv, opts) {
  const f = fakeDeps(opts);
  const code = await main(argv, f.deps);
  return { ...f, code, out: f.calls.out ?? '', err: f.calls.err ?? '' };
}

test('every step of ADR-0011 §3 exists on both OSes, with --help', async () => {
  const changes = ['tap', 'type', 'scroll', 'back', 'open', 'share', 'notify', 'fold', 'unfold', 'half-open', 'rotate',
    'resize', 'appearance', 'text-size', 'rtl', 'background', 'foreground', 'kill'];
  for (const name of changes) assert.ok(STEPS.some((s) => s.name === name), name);
  for (const s of STEPS) {
    assert.equal(typeof ios.steps[s.name], 'function', `ios ${s.name}`);
    assert.equal(typeof android.steps[s.name], 'function', `android ${s.name}`);
    const f = await run(['ios', s.name, '--help']);
    assert.equal(f.code, 0);
    assert.match(f.out, new RegExp(`^Usage: scripts/drive <ios\\|android> ${s.name} `));
    assert.equal(f.calls.sh.length + f.calls.send.length, 0, `${s.name} --help does nothing`);
  }
  assert.match((await run(['--help'])).out, /lifecycle:\n {2}background/);
});

test('a step prints the tree and saves the screenshot', async () => {
  const f = await run(['ios', 'tap', 'Compose'], { state: IOS });
  assert.equal(f.code, 0);
  assert.deepEqual(f.calls.send, [{ op: 'tap', label: 'Compose', app: 'dev.demo' }]);
  assert.match(f.out, /^Screen\n {2}Application "Demo" focused\n {4}Button "Compose" @10,20,30,40\n {2}Other "Hi" \[NotificationShortLookView\]\nscreenshot: \/state\/shots\/.+-ios-tap\.png\n$/);
  assert.equal(Object.values(f.calls.files)[0].toString(), 'png');
});

test('an element that is not there fails with what is on the screen instead', async () => {
  const send = () => ({ ok: false, error: 'no element with label "Send"', tree: SCREEN, screenshot: PNG });
  for (const os of ['ios', 'android']) {
    const f = await run([os, 'tap', 'Send'], { state: { ...IOS, ...AND }, send });
    assert.equal(f.code, 1);
    assert.match(f.err, /^drive: no element with label "Send"\nOn the screen instead:\nScreen\n.*Button "Compose"/s);
  }
});

test('a step on an app that is not running fails with what is on the screen instead', async () => {
  const send = (r) => (r.op === 'look' ? { ok: true, tree: SCREEN, screenshot: PNG }
    : { ok: false, error: 'dev.demo is not running', tree: SCREEN, screenshot: PNG });
  for (const os of ['ios', 'android']) {
    const f = await run([os, 'back'], { state: { ...IOS, ...AND }, send });
    assert.equal(f.code, 1);
    assert.match(f.err, /dev\.demo is not running\nOn the screen instead:\nScreen/);
  }
  // kill and background check the process themselves before acting.
  const ios = await run(['ios', 'kill'], { state: IOS, sh: () => '-\t0\tcom.apple.other\n' });
  assert.match(ios.err, /^drive: kill: dev\.demo is not running\nOn the screen instead:\nScreen/);
  assert.ok(!ios.calls.send.some((r) => r.op === 'home'));
  const and = await run(['android', 'background'], { state: AND, sh: (c, a) => { if (a.join(' ').includes('pidof')) throw new Error('exit 1'); return ''; } });
  assert.match(and.err, /^drive: background: dev\.demo is not running\nOn the screen instead:/);
});

test('a push the app may not show yet says how to allow it', async () => {
  const sh = (cmd, args) => {
    if (args.includes('push')) throw Object.assign(new Error('failed'), { stderr: 'Source is not authorized.' });
    return '';
  };
  const f = await run(['ios', 'notify', 'Hi'], { state: IOS, sh });
  assert.match(f.err, /^drive: dev\.demo may not show notifications yet: let it ask, tap Allow, then notify again\nOn the screen instead:/);
});

test('a stale idb companion is restarted once', async () => {
  let failed = false;
  const sh = (cmd, args) => {
    if (cmd === 'idb' && args[0] === 'hinge' && !failed) {
      failed = true;
      throw Object.assign(new Error('failed'), { stderr: 'The dtuhidd connection has been invalidated' });
    }
    return '';
  };
  const f = await run(['ios', 'unfold'], { state: IOS_DUO, sh: (c, a) => (a.length === 3 ? '180.0\n' : sh(c, a)) });
  assert.equal(f.code, 0, f.err);
  assert.deepEqual(f.calls.sh, ['idb hinge 180 --udid UDID', 'idb kill', 'idb hinge 180 --udid UDID', 'idb hinge --udid UDID', 'sleep 1.5']);
});

test('a hinge that does not move is set again, then refused', async () => {
  const reads = ['0.0', '180.0'];
  const ok = await run(['ios', 'unfold'], { state: IOS_DUO, sh: (c, a) => (a.length === 3 ? reads.shift() : '') });
  assert.equal(ok.code, 0, ok.err);
  assert.equal(ok.calls.sh.filter((c) => c === 'idb hinge 180 --udid UDID').length, 2);
  const stuck = await run(['ios', 'unfold'], { state: IOS_DUO, sh: (c, a) => (a.length === 3 ? '0.0' : '') });
  assert.match(stuck.err, /the hinge did not move to 180 degrees/);
});

test('without a session a step says how to start one', async () => {
  const f = await run(['android', 'look']);
  assert.equal(f.code, 1);
  assert.equal(f.err, 'drive: no android session: run "scripts/drive android start" first\n');
});

test('bad arguments are refused with the fix', async () => {
  assert.match((await run(['ios', 'rotate', 'sideways'], { state: IOS })).err, /rotate takes one of portrait, landscape-left/);
  assert.match((await run(['ios', 'tap'], { state: IOS })).err, /tap needs a label, --id <id> or --at <x>,<y>/);
  assert.match((await run(['ios', 'fold'], { state: IOS })).err, /start --device duo/);
  assert.match((await run(['android', 'fold'], { state: AND })).err, /start --device fold/);
  assert.match((await run(['ios', 'resize', '800x600'], { state: IOS })).err, /start --device ipad/);
  assert.match((await run(['ios', 'share', 'x'], { state: IOS })).err, /share needs --to <app name>/);
  assert.match((await run(['windows', 'look'])).err, /not an OS of this tool/);
  assert.match((await run(['ios', 'jump'])).err, /"jump" is not a step: one of start, stop/);
});

// What each change of ADR-0011 §3 asks of the OS and the runner.
const IOS_CASES = [
  [['tap', '--at', '12,34'], IOS, [], [{ op: 'tap', x: 12, y: 34 }]],
  [['type', 'two words', '--into', 'Title'], IOS, [], [{ op: 'type', text: 'two words', label: 'Title' }]],
  [['scroll', 'down', '--in', 'Timeline'], IOS, [], [{ op: 'scroll', direction: 'down', label: 'Timeline' }]],
  [['back'], IOS, [], [{ op: 'back' }]],
  [['open', 'demo://post/1'], IOS, [], [{ op: 'open', url: 'demo://post/1' }]],
  [['share', 'https://example.com/', '--to', 'Demo'], IOS, [], [{ op: 'share', url: 'https://example.com/', target: 'Demo' }]],
  [['notify', 'Hi', 'there', '--data', 'post=7'], IOS, ['xcrun simctl push UDID dev.demo /state/push.json'], [{ op: 'look' }]],
  [['fold'], IOS_DUO, ['idb hinge 0 --udid UDID', 'idb hinge --udid UDID', 'sleep 1.5'], [{ op: 'look' }]],
  [['unfold'], IOS_DUO, ['idb hinge 180 --udid UDID', 'idb hinge --udid UDID', 'sleep 1.5'], [{ op: 'look' }]],
  [['half-open'], IOS_DUO, ['idb hinge 120 --udid UDID', 'idb hinge --udid UDID', 'sleep 1.5'], [{ op: 'look' }]],
  [['rotate', 'landscape-left'], IOS, [], [{ op: 'orientation', value: 'landscape-left' }]],
  [['resize', '600x500'], IOS_IPAD, [], [{ op: 'resize', width: 600, height: 500 }]],
  [['appearance', 'dark'], IOS, ['xcrun simctl ui UDID appearance dark'], [{ op: 'look' }]],
  [['text-size', 'accessibility'], IOS, ['xcrun simctl ui UDID content_size accessibility-extra-extra-extra-large'], [{ op: 'look' }]],
  [['rtl', 'on'], IOS, [], [{ op: 'launch', args: ['-AppleTextDirection', 'YES', '-NSForceRightToLeftWritingDirection', 'YES'] }]],
  [['background'], IOS, ['xcrun simctl spawn UDID launchctl list'], [{ op: 'home' }]],
  [['foreground'], IOS, [], [{ op: 'activate' }]],
  [['kill'], IOS, ['xcrun simctl spawn UDID launchctl list', 'xcrun simctl spawn UDID launchctl list', 'kill -9 123'],
    [{ op: 'home' }, { op: 'look', noApp: true }]],
];

const ANDROID_CASES = [
  [['tap', '--id', 'send'], AND, [], [{ op: 'tap', id: 'send' }]],
  [['type', 'two words'], AND, [], [{ op: 'type', text: 'two words' }]],
  [['scroll', 'up'], AND, [], [{ op: 'scroll', direction: 'up' }]],
  [['back'], AND, [], [{ op: 'back' }]],
  [['open', 'https://demo.example/post/1'], AND, [
    "shell pm set-app-links --package 'dev.demo' 2 'demo.example'",
    "shell am start -W -a android.intent.action.VIEW -c android.intent.category.BROWSABLE -d 'https://demo.example/post/1'"],
    [{ op: 'look' }]],
  [['share', 'it is good', '--to', 'Demo'], AND, [
    "shell am start -W -a android.intent.action.CHOOSER --eu android.intent.extra.INTENT 'intent:#Intent;action=android.intent.action.SEND;type=text/plain;S.android.intent.extra.TEXT=it%20is%20good;end'"],
    [{ op: 'tap', label: 'Demo' }]],
  [['notify', 'Hi', 'there'], AND, [
    "shell cmd notification post -S bigtext -t 'Hi' drive 'there'",
    'shell cmd statusbar expand-notifications'], [{ op: 'look' }]],
  [['fold'], AND_FOLD, ['emu fold', 'shell dumpsys window | grep mCurrentFocus', 'shell wm dismiss-keyguard',
    'shell dumpsys window | grep mCurrentFocus'], [{ op: 'look' }]],
  [['unfold'], AND_FOLD, ['emu unfold'], [{ op: 'look' }]],
  [['half-open'], AND_FOLD, ['emu posture 2'], [{ op: 'look' }]],
  [['rotate', 'landscape-right'], AND, ['shell cmd window user-rotation lock 3'], [{ op: 'look' }]],
  [['resize', '1600x1200'], AND, ['shell wm size 1600x1200'], [{ op: 'look' }]],
  [['resize', 'reset'], AND, ['shell wm size reset'], [{ op: 'look' }]],
  [['appearance', 'dark'], AND, ['shell cmd uimode night yes'], [{ op: 'look' }]],
  [['text-size', 'largest'], AND, ['shell settings put system font_scale 1.5'], [{ op: 'look' }]],
  [['rtl', 'on'], AND, ["shell cmd locale set-app-locales 'dev.demo' --locales ar"], [{ op: 'look' }]],
  [['rtl', 'off'], AND, ["shell cmd locale set-app-locales 'dev.demo' --locales ''"], [{ op: 'look' }]],
  [['background'], AND, ["shell pidof 'dev.demo'"], [{ op: 'home' }]],
  [['foreground'], AND, [
    "shell cmd package resolve-activity --brief -c android.intent.category.LAUNCHER 'dev.demo'",
    "shell am start -W -n 'dev.demo/.MainActivity'"], [{ op: 'look' }]],
  [['kill'], AND, ["shell pidof 'dev.demo'", "shell am kill 'dev.demo'"], [{ op: 'home' }, { op: 'look', noApp: true }]],
];

function expectRequests(sent, expected) {
  assert.deepEqual(sent, expected.map(({ noApp, ...r }) => (noApp ? r : { ...r, app: 'dev.demo' })));
}

test('each change on iOS runs its simctl, idb or runner step', async () => {
  for (const [argv, state, commands, requests] of IOS_CASES) {
    let hinge = '';
    const sh = (cmd, args) => {
      if (cmd !== 'idb') return LAUNCHCTL;
      if (args.length === 4) hinge = args[1];
      return hinge; // reading the hinge gives the angle last set
    };
    const f = await run(['ios', ...argv], { state, sh });
    assert.equal(f.code, 0, `${argv.join(' ')}: ${f.err}`);
    assert.deepEqual(f.calls.sh, commands, argv.join(' '));
    expectRequests(f.calls.send, requests);
  }
  const push = await run(['ios', 'notify', 'Hi', 'there', '--data', 'post=7', '--data', 'kind=reply'], { state: IOS });
  assert.deepEqual(JSON.parse(push.calls.files['/state/push.json']),
    { aps: { alert: { title: 'Hi', body: 'there' }, sound: 'default' }, post: 7, kind: 'reply' });
});

test('each change on Android runs its adb or runner step', async () => {
  for (const [argv, state, commands, requests] of ANDROID_CASES) {
    androidSh.focus = ['mCurrentFocus=Window{1 u0 NotificationShade}', 'mCurrentFocus=Window{2 u0 dev.demo/.Main}'];
    const f = await run(['android', ...argv], { state, sh: androidSh });
    assert.equal(f.code, 0, `${argv.join(' ')}: ${f.err}`);
    assert.deepEqual(f.calls.sh, commands.map((c) => `/sdk/platform-tools/adb -s emulator-5600 ${c}`), argv.join(' '));
    expectRequests(f.calls.send, requests);
  }
});

test('an emulator that adb never sees fails the start after the boot timeout, and is stopped', async () => {
  const f = fakeDeps({
    sh: (cmd, args) => {
      if (args.includes('-list-avds')) return 'drive-phone\n';
      if (args.join(' ').includes('getprop')) throw new Error("adb: device 'emulator-5600' not found");
      return '';
    },
  });
  let clock = 0;
  f.deps.now = () => new Date((clock += 60_000));
  const killed = [];
  f.deps.kill = (pid) => killed.push(pid);
  const code = await main(['android', 'start'], f.deps);
  assert.notEqual(code, 0);
  assert.match(f.calls.err, /drive-phone did not boot in 5 minutes \(adb never saw it\), so it was stopped: read \/state\/android-emulator.log/);
  assert.deepEqual(killed, [4242]);
  assert.ok(!f.calls.sh.some((c) => c.includes('wait-for-device')), 'no adb wait-for-device, which never gives up');
});

test('an emulator that exits while booting fails the start at once', async () => {
  const f = fakeDeps({ sh: (cmd, args) => (args.includes('-list-avds') ? 'drive-phone\n' : '') });
  f.deps.alive = () => false;
  const code = await main(['android', 'start'], f.deps);
  assert.notEqual(code, 0);
  assert.match(f.calls.err, /the emulator of drive-phone exited while booting: read \/state\/android-emulator.log/);
});
