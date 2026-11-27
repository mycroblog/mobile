#!/usr/bin/env node
// PreToolUse hook of the roles that judge the running apps (ADR-0011 §2, §6): the evaluators, the walkers and the
// parity checker. They may run only the app-driving tool and read only its screenshots and their own knowledge, so
// they judge what the app does, not how it was built. Usage in a role's frontmatter: `node <this file> <role>`.
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');

export function stateDir(env = process.env) {
  return path.resolve(env.DRIVE_STATE_DIR ?? path.join(os.tmpdir(), 'mycroblog-drive'));
}

const real = (p) => {
  try {
    return fs.realpathSync(p);
  } catch {
    return path.resolve(p);
  }
};
const inside = (file, dir) => {
  const rel = path.relative(real(dir), real(file));
  return rel !== '' && !rel.startsWith('..') && !path.isAbsolute(rel);
};

// Returns null to let the call through, else the reason it is refused, which says what to use instead.
export function decide(role, call, { root = ROOT, env = process.env } = {}) {
  const tool = call.tool_name;
  const input = call.tool_input ?? {};
  const instead = `As ${role}, use scripts/drive (scripts/drive --help), the screenshots it prints, and .claude/knowledge/${role}/.`;
  if (tool === 'Bash') {
    const command = String(input.command ?? '').trim();
    const drive = /^(?:\S*\/)?scripts\/drive(?:\s|$)/.test(command);
    if (drive && !/[;&|`<>\n]|\$\(/.test(command)) return null;
    return drive
      ? `One scripts/drive step per call, with no ; & | > < \` or $( in it. ${instead}`
      : `${role} judges the running app and runs only scripts/drive, not "${command.split(/\s/)[0]}". ${instead}`;
  }
  if (tool === 'Read') {
    const file = path.resolve(root, String(input.file_path ?? ''));
    if (inside(file, stateDir(env)) || inside(file, path.join(root, '.claude/knowledge', role))) return null;
    return `${role} does not read the repository or its source: ${file} is neither a screenshot of scripts/drive nor its knowledge. ${instead}`;
  }
  return `${role} has no ${tool}: it judges the running app, not its source. ${instead}`;
}

if (process.argv[1] === fileURLToPath(import.meta.url)) {
  const role = process.argv[2] ?? 'this role';
  const reason = decide(role, JSON.parse(fs.readFileSync(0, 'utf8')));
  if (reason) {
    process.stdout.write(JSON.stringify({
      hookSpecificOutput: { hookEventName: 'PreToolUse', permissionDecision: 'deny', permissionDecisionReason: reason },
    }));
  }
}
