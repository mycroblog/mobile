// The app-driving tool of ADR-0011 §3: one step per call on the iOS Simulator or the Android Emulator. Every step
// prints the accessibility tree and the path of a screenshot taken after the screen settled.
import { execFileSync, spawn } from 'node:child_process';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { renderTree } from './tree.mjs';
import { send, waitForRunner } from './runner.mjs';
import { StepError } from './common.mjs';
import { ios } from './ios.mjs';
import { android } from './android.mjs';

const ROOT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const PLATFORMS = { ios, android };

// Each step: what it changes (ADR-0011 §3), its arguments, and its help. The platforms implement them.
export const STEPS = [
  ['session', 'start', '[--device <kind>]', 'Create (once) and boot a device of this tool, build and start the runner. Kinds: ios phone|duo|ipad, android phone|fold|resizable|tablet.'],
  ['session', 'stop', '', 'Stop the runner and shut the device down.'],
  ['session', 'install', '<path>', 'Install an app (.app for iOS, .apk for Android) on the device.'],
  ['session', 'launch', '<app id>', 'Launch the app (bundle id or package) and make it the app every later step acts on.'],
  ['session', 'look', '', 'Change nothing; show the screen.'],
  ['operation', 'tap', '<label> | --id <id> | --at <x>,<y>', 'Tap an element by its label or id, or a point of the screenshot. The app is searched first, then the system UI over it.'],
  ['operation', 'type', '<text> [--into <label> | --into-id <id>]', 'Type text into the focused field, or into the named field after focusing it.'],
  ['operation', 'scroll', '<up|down|left|right> [--in <label>]', 'Scroll so that the content in that direction comes into view.'],
  ['operation', 'back', '', 'Go back: the system back on Android, the navigation bar back button (else an edge swipe) on iOS.'],
  ['input', 'open', '<url>', 'Open a link (custom scheme or https) as the system would.'],
  ['input', 'share', '<text or url> --to <app name>', 'Share into an app through the system share sheet.'],
  ['input', 'notify', '<title> <body> [--data <key>=<value>]...', 'Deliver a notification (iOS: a simulated push to the app; Android: a notification, with the shade opened).'],
  ['size', 'fold', '', 'Fold the device (iPhone Duo: close the hinge; Android: a foldable AVD).'],
  ['size', 'unfold', '', 'Unfold the device fully.'],
  ['size', 'half-open', '', 'Open the hinge half way (tabletop posture).'],
  ['size', 'rotate', '<portrait|landscape-left|landscape-right|upside-down>', 'Rotate the device.'],
  ['size', 'resize', '<width>x<height> | reset', 'Resize the app window (iPad windowed apps, in points) or the Android display (in pixels).'],
  ['appearance', 'appearance', '<light|dark>', 'Switch dark mode.'],
  ['appearance', 'text-size', '<default|larger|largest|accessibility>', 'Change the text size (Dynamic Type, font scale).'],
  ['appearance', 'rtl', '<on|off>', 'Lay the app out right to left. iOS relaunches the app with forced RTL; Android sets an Arabic app locale.'],
  ['lifecycle', 'background', '', 'Send the app to the background (home).'],
  ['lifecycle', 'foreground', '', 'Bring the app back as the user would; after `kill` this starts it again from its saved state.'],
  ['lifecycle', 'kill', '', 'Background the app, then end its process the way the system does under memory pressure.'],
].map(([group, name, args, help]) => ({ group, name, args, help }));

export function usage(step) {
  if (step) return `Usage: scripts/drive <ios|android> ${step.name} ${step.args}\n\n${step.help}\n`;
  const lines = ['Usage: scripts/drive <ios|android> <step> [arguments]', '',
    'One step per call. Every step prints the accessibility tree of the screen once it settled, then',
    '"screenshot: <path>". A failed step says why and what is on the screen instead.', ''];
  let group;
  for (const s of STEPS) {
    if (s.group !== group) lines.push(`${(group = s.group)}:`);
    lines.push(`  ${`${s.name} ${s.args}`.padEnd(58)} ${s.help.split('. ')[0].replace(/\.$/, '')}.`);
  }
  lines.push('', 'scripts/drive <os> <step> --help shows one step.');
  return lines.join('\n') + '\n';
}

export function parseArgs(argv) {
  const positional = [];
  const flags = {};
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a.startsWith('--')) {
      const [key, inline] = a.slice(2).split(/=(.*)/s);
      const value = inline ?? (argv[i + 1] !== undefined && !argv[i + 1].startsWith('--') ? argv[++i] : true);
      if (key in flags) flags[key] = [].concat(flags[key], value);
      else flags[key] = value;
    } else positional.push(a);
  }
  return { positional, flags };
}

export function defaultDeps(env = process.env) {
  const stateDir = env.DRIVE_STATE_DIR ?? path.join(os.tmpdir(), 'mycroblog-drive');
  return {
    root: ROOT,
    env,
    stateDir,
    sh(cmd, args, opts = {}) {
      return execFileSync(cmd, args, { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'], maxBuffer: 64 << 20, ...opts });
    },
    spawnDetached(cmd, args, { log, env: extra, cwd } = {}) {
      fs.mkdirSync(path.dirname(log), { recursive: true });
      const fd = fs.openSync(log, 'a');
      const child = spawn(cmd, args, { detached: true, stdio: ['ignore', fd, fd], env: { ...env, ...extra }, cwd });
      child.unref();
      return child.pid;
    },
    alive(pid) {
      try {
        process.kill(pid, 0);
        return true;
      } catch {
        return false;
      }
    },
    kill(pid, signal = 'SIGTERM') {
      try {
        process.kill(pid, signal);
      } catch {
        // already gone
      }
    },
    send,
    waitForRunner,
    readState(name) {
      try {
        return JSON.parse(fs.readFileSync(path.join(stateDir, `${name}.json`), 'utf8'));
      } catch {
        return null;
      }
    },
    writeState(name, state) {
      fs.mkdirSync(stateDir, { recursive: true });
      if (state === null) fs.rmSync(path.join(stateDir, `${name}.json`), { force: true });
      else fs.writeFileSync(path.join(stateDir, `${name}.json`), JSON.stringify(state, null, 2));
    },
    writeFile(file, data) {
      fs.mkdirSync(path.dirname(file), { recursive: true });
      fs.writeFileSync(file, data);
    },
    now: () => new Date(),
    out: (s) => process.stdout.write(s),
    err: (s) => process.stderr.write(s),
  };
}

function render(deps, platform, stepName, reply) {
  let text = renderTree(reply.tree);
  if (reply.screenshot) {
    const stamp = deps.now().toISOString().replace(/[:.]/g, '-');
    const file = path.join(deps.stateDir, 'shots', `${stamp}-${platform}-${stepName}.png`);
    deps.writeFile(file, Buffer.from(reply.screenshot, 'base64'));
    text += `\nscreenshot: ${file}`;
  }
  return text + '\n';
}

export async function main(argv, deps = defaultDeps()) {
  const [platformName, stepName, ...rest] = argv;
  if (!platformName || platformName === '--help' || platformName === '-h' || platformName === 'help') {
    deps.out(usage());
    return 0;
  }
  const platform = PLATFORMS[platformName];
  if (!platform) {
    deps.err(`drive: "${platformName}" is not an OS of this tool: use ios or android (scripts/drive --help).\n`);
    return 2;
  }
  const step = STEPS.find((s) => s.name === stepName);
  if (!step) {
    deps.err(`drive: ${stepName ? `"${stepName}" is not a step` : 'name a step'}: one of ${STEPS.map((s) => s.name).join(', ')}.\n`
      + 'scripts/drive --help says what each does.\n');
    return 2;
  }
  const { positional, flags } = parseArgs(rest);
  if (flags.help || flags.h) {
    deps.out(usage(step));
    return 0;
  }
  const ctx = platform.context(deps);
  try {
    const reply = await platform.steps[step.name](ctx, positional, flags);
    deps.out(reply?.tree ? render(deps, platformName, step.name, reply) : `${reply?.message ?? 'done'}\n`);
    return 0;
  } catch (e) {
    if (e instanceof StepError || e.reply) {
      let text = `drive: ${e.message}`;
      if (e.reply?.tree) text += `\nOn the screen instead:\n${render(deps, platformName, step.name, e.reply)}`;
      else text += '\n';
      deps.err(text);
      return 1;
    }
    deps.err(`drive: ${e.message}\n`);
    return 1;
  }
}
