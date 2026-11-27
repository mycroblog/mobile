// The Android Emulator side of the app-driving tool: adb and the emulator console for the device, the UI Automator
// runner of tools/drive/android for the screen (ADR-0011 §3).
import path from 'node:path';
import { StepError, need, oneOf, parseData, parseSize, runnerOf, target } from './common.mjs';

const DEVICES = { phone: 'pixel_9', fold: 'pixel_9_pro_fold', resizable: 'resizable', tablet: 'pixel_tablet' };
const ROTATIONS = { portrait: 0, 'landscape-left': 1, 'upside-down': 2, 'landscape-right': 3 };
const FONT_SCALES = { default: '1.0', larger: '1.3', largest: '1.5', accessibility: '2.0' };
const RUNNER = 'dev.mycroblog.drive.runner';
const DEVICE_PORT = 9008;

// One shell word for `adb shell`, which joins its arguments into one command line for the device's shell.
export const quote = (s) => `'${String(s).replace(/'/g, `'\\''`)}'`;

function context(deps) {
  const runner = runnerOf(deps, 'android');
  const sdk = deps.env.ANDROID_HOME ?? path.join(deps.env.HOME ?? '', 'Library/Android/sdk');
  const adbPath = path.join(sdk, 'platform-tools/adb');
  const port = Number(deps.env.DRIVE_ANDROID_PORT ?? 9018);
  const consolePort = Number(deps.env.DRIVE_ANDROID_CONSOLE_PORT ?? 5600);
  const serial = () => runner.state().device;
  const adb = (...args) => deps.sh(adbPath, ['-s', serial(), ...args]);
  const shell = (...words) => adb('shell', words.join(' '));
  const look = () => runner.call('look');

  const running = (app) => {
    try {
      return shell('pidof', quote(app)).trim() !== '';
    } catch {
      return false; // pidof exits 1 when there is no such process
    }
  };

  async function requireRunning(step) {
    const app = runner.app();
    if (!running(app)) {
      const reply = await runner.call('look', {}, { withApp: false });
      throw new StepError(`${step}: ${app} is not running`, reply);
    }
    return app;
  }

  function launcherActivity(app) {
    const out = shell('cmd', 'package', 'resolve-activity', '--brief', '-c', 'android.intent.category.LAUNCHER', quote(app));
    const component = out.trim().split('\n').pop();
    if (!component.includes('/')) throw new StepError(`${app} has no launcher activity on the device: install it first`);
    return component;
  }

  return { deps, runner, sdk, adbPath, port, consolePort, serial, adb, shell, look, running, requireRunning, launcherActivity };
}

function latestImage(deps, sdk) {
  const base = path.join(sdk, 'system-images');
  const out = deps.sh('find', [base, '-mindepth', '3', '-maxdepth', '3', '-type', 'd', '-path', '*google_apis_playstore/arm64-v8a']);
  const dirs = out.trim().split('\n').filter(Boolean).map((d) => path.relative(base, d).split(path.sep));
  dirs.sort((a, b) => a[0].localeCompare(b[0], undefined, { numeric: true }));
  const last = dirs.pop();
  if (!last) throw new StepError(`no google_apis_playstore arm64-v8a system image under ${base}: install one with sdkmanager`);
  return `system-images;${last.join(';')}`;
}

const steps = {
  async start(ctx, _p, flags) {
    const { deps, sdk } = ctx;
    const kind = oneOf(flags.device === undefined ? 'phone' : flags.device, Object.keys(DEVICES), 'start');
    const old = deps.readState('android');
    if (old?.runnerPid && deps.alive(old.runnerPid)) {
      throw new StepError(`an android session is running on ${old.kind}: run "scripts/drive android stop" first`);
    }
    const avd = `drive-${kind}`;
    const avds = deps.sh(path.join(sdk, 'emulator/emulator'), ['-list-avds']).split('\n');
    if (!avds.includes(avd)) {
      deps.sh(path.join(sdk, 'cmdline-tools/latest/bin/avdmanager'),
        ['create', 'avd', '-n', avd, '-k', latestImage(deps, sdk), '-d', DEVICES[kind]], { input: 'no\n' });
    }
    const device = `emulator-${ctx.consolePort}`;
    // The resizable AVD changes size only through its window, so it keeps one.
    const window = kind === 'resizable' ? [] : ['-no-window'];
    const emulatorPid = deps.spawnDetached(path.join(sdk, 'emulator/emulator'),
      ['-avd', avd, '-port', String(ctx.consolePort), '-no-snapshot-save', '-no-boot-anim', ...window],
      { log: path.join(deps.stateDir, 'android-emulator.log') });
    deps.writeState('android', { kind, device, port: ctx.port, emulatorPid, runnerPid: null, app: null });
    ctx.adb('wait-for-device');
    const deadline = Date.now() + 300_000;
    while (ctx.shell('getprop', 'sys.boot_completed').trim() !== '1') {
      if (Date.now() > deadline) throw new StepError(`${avd} did not finish booting in 5 minutes`);
      deps.sh('sleep', ['1']);
    }
    const project = path.join(deps.root, 'tools/drive/android');
    deps.sh(path.join(project, 'gradlew'), ['-q', ':runner:assembleDebug', ':runner:assembleDebugAndroidTest'],
      { cwd: project, env: { ...deps.env, ANDROID_HOME: sdk } });
    const apks = path.join(project, 'runner/build/outputs/apk');
    ctx.adb('install', '-r', '-t', path.join(apks, 'debug/runner-debug.apk'));
    ctx.adb('install', '-r', '-t', path.join(apks, 'androidTest/debug/runner-debug-androidTest.apk'));
    ctx.adb('forward', `tcp:${ctx.port}`, `tcp:${DEVICE_PORT}`);
    const runnerPid = deps.spawnDetached(ctx.adbPath, ['-s', device, 'shell', 'am', 'instrument', '-w',
      '-e', 'class', `${RUNNER}.Server#serve`, '-e', 'port', String(DEVICE_PORT),
      `${RUNNER}.test/androidx.test.runner.AndroidJUnitRunner`], { log: path.join(deps.stateDir, 'android-runner.log') });
    deps.writeState('android', { ...deps.readState('android'), runnerPid });
    await deps.waitForRunner(ctx.port, () => deps.alive(runnerPid));
    return ctx.runner.call('look');
  },

  async stop(ctx) {
    const { deps } = ctx;
    const state = deps.readState('android');
    if (!state) return { message: 'no android session' };
    try {
      await deps.send(state.port, { op: 'quit' }, 5_000);
    } catch {
      // the runner is gone already
    }
    try {
      ctx.adb('emu', 'kill');
    } catch {
      // the emulator is gone already
    }
    for (const pid of [state.runnerPid, state.emulatorPid]) if (pid) deps.kill(pid);
    deps.writeState('android', null);
    return { message: `stopped; the AVD drive-${state.kind} is kept for the next start` };
  },

  async install(ctx, [file]) {
    ctx.adb('install', '-r', '-t', need(file, 'the path of an .apk', 'install'));
    return ctx.look();
  },

  async launch(ctx, [app]) {
    need(app, 'a package', 'launch');
    const component = ctx.launcherActivity(app);
    ctx.deps.writeState('android', { ...ctx.runner.state(), app });
    ctx.shell('am', 'start', '-W', '-n', quote(component));
    return ctx.look();
  },

  look: (ctx) => ctx.runner.call('look', {}, { withApp: Boolean(ctx.runner.state().app) }),

  async tap(ctx, positional, flags) {
    const t = target(positional, flags, 'tap');
    if (!t) need(undefined, 'a label, --id <id> or --at <x>,<y>', 'tap');
    return ctx.runner.call('tap', t);
  },

  async type(ctx, [text], flags) {
    need(text, 'the text', 'type');
    return ctx.runner.call('type', { text, ...(target([], flags, 'type', { labelFlag: 'into', idFlag: 'into-id', atFlag: null }) ?? {}) });
  },

  async scroll(ctx, [direction], flags) {
    oneOf(direction, ['up', 'down', 'left', 'right'], 'scroll');
    return ctx.runner.call('scroll', { direction, ...(target([], flags, 'scroll', { labelFlag: 'in', atFlag: null }) ?? {}) });
  },

  back: (ctx) => ctx.runner.call('back'),

  async open(ctx, [url]) {
    need(url, 'a url', 'open');
    const https = /^https:\/\/([^/]+)/.exec(url);
    // The emulator cannot verify a domain: approve it for the app, as a verified App Link would be.
    if (https && ctx.runner.state().app) {
      ctx.shell('pm', 'set-app-links', '--package', quote(ctx.runner.app()), '2', quote(https[1]));
    }
    ctx.shell('am', 'start', '-W', '-a', 'android.intent.action.VIEW', '-c', 'android.intent.category.BROWSABLE', '-d', quote(url));
    return ctx.look();
  },

  async share(ctx, [content], flags) {
    need(content, 'the text or url to share', 'share');
    const to = need(flags.to, '--to <app name>', 'share');
    const send = `intent:#Intent;action=android.intent.action.SEND;type=text/plain;S.android.intent.extra.TEXT=${encodeURIComponent(content)};end`;
    ctx.shell('am', 'start', '-W', '-a', 'android.intent.action.CHOOSER', '--eu', 'android.intent.extra.INTENT', quote(send));
    return ctx.runner.call('tap', { label: to });
  },

  async notify(ctx, [title, body], flags) {
    need(title, 'a title', 'notify');
    const data = parseData(flags);
    const extras = Object.entries(data).flatMap(([k, v]) => ['--es', quote(k), quote(v)]);
    const app = ctx.runner.state().app;
    // Tapping the notification opens the app with the data as extras, as a push's notification would.
    const open = app ? ['-c', 'activity', quote(`-n ${ctx.launcherActivity(app)} ${extras.join(' ')}`.trim())] : [];
    ctx.shell('cmd', 'notification', 'post', '-S', 'bigtext', '-t', quote(title), ...open, 'drive', quote(body ?? title));
    ctx.shell('cmd', 'statusbar', 'expand-notifications');
    return ctx.look();
  },

  async fold(ctx) {
    requireFold(ctx, 'fold');
    ctx.adb('emu', 'fold');
    // Folding locks the screen on this image; the user's app shows again on the cover screen.
    ctx.shell('wm', 'dismiss-keyguard');
    return ctx.look();
  },

  async unfold(ctx) {
    requireFold(ctx, 'unfold');
    ctx.adb('emu', 'unfold');
    return ctx.look();
  },

  async 'half-open'(ctx) {
    requireFold(ctx, 'half-open');
    ctx.adb('emu', 'posture', '2');
    return ctx.look();
  },

  async rotate(ctx, [value]) {
    ctx.shell('cmd', 'window', 'user-rotation', 'lock', String(ROTATIONS[oneOf(value, Object.keys(ROTATIONS), 'rotate')]));
    return ctx.look();
  },

  async resize(ctx, [value]) {
    if (value === 'reset') ctx.shell('wm', 'size', 'reset');
    else {
      const { width, height } = parseSize(value, 'resize');
      ctx.shell('wm', 'size', `${width}x${height}`);
    }
    return ctx.look();
  },

  async appearance(ctx, [mode]) {
    ctx.shell('cmd', 'uimode', 'night', oneOf(mode, ['light', 'dark'], 'appearance') === 'dark' ? 'yes' : 'no');
    return ctx.look();
  },

  async 'text-size'(ctx, [size]) {
    ctx.shell('settings', 'put', 'system', 'font_scale', FONT_SCALES[oneOf(size, Object.keys(FONT_SCALES), 'text-size')]);
    return ctx.look();
  },

  async rtl(ctx, [mode]) {
    oneOf(mode, ['on', 'off'], 'rtl');
    ctx.shell('cmd', 'locale', 'set-app-locales', quote(ctx.runner.app()), '--locales', mode === 'on' ? 'ar' : quote(''));
    return ctx.look();
  },

  async background(ctx) {
    await ctx.requireRunning('background');
    return ctx.runner.call('home');
  },

  async foreground(ctx) {
    ctx.shell('am', 'start', '-W', '-n', quote(ctx.launcherActivity(ctx.runner.app())));
    return ctx.look();
  },

  async kill(ctx) {
    const app = await ctx.requireRunning('kill');
    await ctx.runner.call('home');
    // `am kill` ends a background process the way the system does, so the app restores its saved state.
    ctx.shell('am', 'kill', quote(app));
    return ctx.runner.call('look', {}, { withApp: false });
  },
};

function requireFold(ctx, step) {
  if (ctx.runner.state().kind !== 'fold') {
    throw new StepError(`${step} works on a foldable AVD: run "scripts/drive android start --device fold"`);
  }
}

export const android = { context, steps };
