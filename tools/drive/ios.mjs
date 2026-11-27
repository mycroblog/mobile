// The iOS Simulator side of the app-driving tool: simctl and devicectl for the device, the XCUITest runner of
// tools/drive/ios for the screen, and idb for the iPhone Duo hinge, which no public Apple tool can set (ADR-0011 §3).
import path from 'node:path';
import { StepError, need, oneOf, parseData, parseSize, runnerOf, target } from './common.mjs';

const DEVICES = { phone: 'iPhone 18 Pro', duo: 'iPhone Duo', ipad: 'iPad Air 11-inch (M4)' };
const ORIENTATIONS = ['portrait', 'landscape-left', 'landscape-right', 'upside-down'];
const TEXT_SIZES = {
  default: 'large',
  larger: 'extra-extra-large',
  largest: 'extra-extra-extra-large',
  accessibility: 'accessibility-extra-extra-extra-large',
};
const RTL_ARGS = ['-AppleTextDirection', 'YES', '-NSForceRightToLeftWritingDirection', 'YES'];
const HINGE = { fold: 0, 'half-open': 120, unfold: 180 };

function context(deps) {
  const runner = runnerOf(deps, 'ios');
  const port = Number(deps.env.DRIVE_IOS_PORT ?? 22087);
  const simctl = (...args) => deps.sh('xcrun', ['simctl', ...args]);
  const udid = () => runner.state().device;
  const look = () => runner.call('look');

  function pidOf(app) {
    // launchctl inside the simulator lists a running app as "<pid> <status> UIKitApplication:<bundle id>[...]".
    const line = simctl('spawn', udid(), 'launchctl', 'list')
      .split('\n')
      .find((l) => l.includes(`UIKitApplication:${app}[`));
    const pid = line?.trim().split(/\s+/)[0];
    return pid && pid !== '-' ? Number(pid) : null;
  }

  async function requireRunning(step) {
    const app = runner.app();
    if (pidOf(app) === null) {
      const reply = await runner.call('look', {}, { withApp: false });
      throw new StepError(`${step}: ${app} is not running`, reply);
    }
    return app;
  }

  function device(kind) {
    const name = `drive-${kind}`;
    const { devices } = JSON.parse(simctl('list', 'devices', '-j'));
    for (const list of Object.values(devices)) {
      const found = list.find((d) => d.name === name && d.isAvailable !== false);
      if (found) return found.udid;
    }
    const { runtimes } = JSON.parse(simctl('list', 'runtimes', '-j'));
    const runtime = runtimes
      .filter((r) => r.platform === 'iOS' && r.isAvailable)
      .filter((r) => (r.supportedDeviceTypes ?? []).some((t) => t.name === DEVICES[kind]))
      .sort((a, b) => a.version.localeCompare(b.version, undefined, { numeric: true }))
      .pop();
    if (!runtime) throw new StepError(`no iOS runtime has the device type "${DEVICES[kind]}": install one in Xcode`);
    return simctl('create', name, DEVICES[kind], runtime.identifier).trim();
  }

  return { deps, runner, port, simctl, udid, look, pidOf, requireRunning, device };
}

const steps = {
  async start(ctx, _p, flags) {
    const { deps } = ctx;
    const kind = oneOf(flags.device === undefined ? 'phone' : flags.device, Object.keys(DEVICES), 'start');
    const old = deps.readState('ios');
    if (old?.runnerPid && deps.alive(old.runnerPid)) {
      throw new StepError(`an ios session is running on ${old.kind}: run "scripts/drive ios stop" first`);
    }
    const udid = ctx.device(kind);
    try {
      ctx.simctl('boot', udid);
    } catch {
      // already booted
    }
    ctx.simctl('bootstatus', udid, '-b');
    const project = path.join(deps.root, 'tools/drive/ios/DriveRunner.xcodeproj');
    const derived = path.join(deps.stateDir, 'ios-build');
    deps.sh('xcodebuild', ['build-for-testing', '-project', project, '-scheme', 'DriveRunner',
      '-destination', `id=${udid}`, '-derivedDataPath', derived, '-quiet'], { stdio: ['ignore', 'pipe', 'pipe'] });
    const products = path.join(derived, 'Build/Products');
    const xctestrun = deps.sh('find', [products, '-name', '*.xctestrun', '-maxdepth', '1']).trim().split('\n')[0];
    if (!xctestrun) throw new StepError(`the runner built, but no .xctestrun is in ${products}`);
    const runnerPid = deps.spawnDetached('xcodebuild', ['test-without-building', '-xctestrun', xctestrun,
      '-destination', `id=${udid}`, '-only-testing:DriveRunner/RunnerTests/testServe'], {
      log: path.join(deps.stateDir, 'ios-runner.log'),
      env: { TEST_RUNNER_DRIVE_PORT: String(ctx.port) },
    });
    deps.writeState('ios', { kind, device: udid, port: ctx.port, runnerPid, app: null });
    await deps.waitForRunner(ctx.port, () => deps.alive(runnerPid));
    return ctx.runner.call('look');
  },

  async stop(ctx) {
    const { deps } = ctx;
    const state = deps.readState('ios');
    if (!state) return { message: 'no ios session' };
    try {
      await deps.send(state.port, { op: 'quit' }, 5_000);
    } catch {
      // the runner is gone already
    }
    if (state.runnerPid) deps.kill(state.runnerPid);
    try {
      ctx.simctl('shutdown', state.device);
    } catch {
      // already shut down
    }
    deps.writeState('ios', null);
    return { message: `stopped; the simulator drive-${state.kind} is kept for the next start` };
  },

  async install(ctx, [file]) {
    ctx.simctl('install', ctx.udid(), need(file, 'the path of a .app', 'install'));
    return ctx.look();
  },

  async launch(ctx, [app]) {
    need(app, 'a bundle id', 'launch');
    ctx.deps.writeState('ios', { ...ctx.runner.state(), app });
    return ctx.runner.call('launch', { args: [] });
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
    return ctx.runner.call('open', { url: need(url, 'a url', 'open') });
  },

  async share(ctx, [content], flags) {
    need(content, 'the text or url to share', 'share');
    return ctx.runner.call('share', { url: content, target: need(flags.to, '--to <app name>', 'share') });
  },

  async notify(ctx, [title, body], flags) {
    need(title, 'a title', 'notify');
    const payload = { aps: { alert: { title, body: body ?? '' }, sound: 'default' }, ...parseData(flags) };
    const file = path.join(ctx.deps.stateDir, 'push.json');
    ctx.deps.writeFile(file, JSON.stringify(payload));
    ctx.simctl('push', ctx.udid(), ctx.runner.app(), file);
    return ctx.look();
  },

  fold: (ctx) => hinge(ctx, 'fold'),
  unfold: (ctx) => hinge(ctx, 'unfold'),
  'half-open': (ctx) => hinge(ctx, 'half-open'),

  async rotate(ctx, [value]) {
    return ctx.runner.call('orientation', { value: oneOf(value, ORIENTATIONS, 'rotate') });
  },

  async resize(ctx, [value]) {
    if (ctx.runner.state().kind !== 'ipad') {
      throw new StepError('resize works on an iPad window: run "scripts/drive ios start --device ipad"');
    }
    if (value === 'reset') throw new StepError('resize on iOS takes <width>x<height> in points; an iPad window has no reset');
    return ctx.runner.call('resize', parseSize(value, 'resize'));
  },

  async appearance(ctx, [mode]) {
    ctx.simctl('ui', ctx.udid(), 'appearance', oneOf(mode, ['light', 'dark'], 'appearance'));
    return ctx.look();
  },

  async 'text-size'(ctx, [size]) {
    ctx.simctl('ui', ctx.udid(), 'content_size', TEXT_SIZES[oneOf(size, Object.keys(TEXT_SIZES), 'text-size')]);
    return ctx.look();
  },

  async rtl(ctx, [mode]) {
    oneOf(mode, ['on', 'off'], 'rtl');
    return ctx.runner.call('launch', { args: mode === 'on' ? RTL_ARGS : [] });
  },

  async background(ctx) {
    await ctx.requireRunning('background');
    return ctx.runner.call('home');
  },

  foreground: (ctx) => ctx.runner.call('activate'),

  async kill(ctx) {
    const app = await ctx.requireRunning('kill');
    await ctx.runner.call('home');
    const pid = ctx.pidOf(app);
    // Simulator apps are processes of the Mac: SIGKILL is what the system sends under memory pressure.
    if (pid !== null) ctx.deps.sh('kill', ['-9', String(pid)]);
    return ctx.runner.call('look', {}, { withApp: false });
  },
};

async function hinge(ctx, posture) {
  if (ctx.runner.state().kind !== 'duo') {
    throw new StepError(`${posture} works on the iPhone Duo: run "scripts/drive ios start --device duo"`);
  }
  try {
    ctx.deps.sh('idb', ['hinge', String(HINGE[posture]), '--udid', ctx.udid()]);
  } catch (e) {
    if (e.code === 'ENOENT') {
      throw new StepError('idb is not installed: brew install facebook/fb/idb-companion, then pipx install fb-idb');
    }
    throw new StepError(`idb hinge failed: ${String(e.stderr ?? e.message).trim()}`);
  }
  return ctx.look();
}

export const ios = { context, steps };
