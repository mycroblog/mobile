// Tests of the hook that keeps the evaluators, walkers and parity checker out of the source.
import assert from 'node:assert/strict';
import { execFileSync } from 'node:child_process';
import path from 'node:path';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { decide } from './app-only.mjs';

const root = '/repo';
const env = { DRIVE_STATE_DIR: '/state' };
const ask = (tool, input) => decide('ios-evaluator', { tool_name: tool, tool_input: input }, { root, env });

test('the app-driving tool, its screenshots and the role knowledge pass', () => {
  assert.equal(ask('Bash', { command: 'scripts/drive ios tap "Post"' }), null);
  assert.equal(ask('Bash', { command: '/repo/scripts/drive android look' }), null);
  assert.equal(ask('Read', { file_path: '/state/shots/2026-ios-tap.png' }), null);
  assert.equal(ask('Read', { file_path: '/repo/.claude/knowledge/ios-evaluator/index.md' }), null);
});

test('the source and everything else are refused with what to use instead', () => {
  for (const [tool, input] of [
    ['Read', { file_path: '/repo/app/ios/Sources/Home.swift' }],
    ['Read', { file_path: '/repo/.claude/knowledge/swift-developer/index.md' }],
    ['Read', { file_path: '/state/../repo/AGENTS.md' }],
    ['Bash', { command: 'cat app/ios/Sources/Home.swift' }],
    ['Bash', { command: 'git log -p' }],
    ['Bash', { command: 'scripts/drive ios look; cat AGENTS.md' }],
    ['Bash', { command: 'scripts/drive ios type "$(cat secrets)"' }],
    ['Bash', { command: 'scripts/drive ios look > /tmp/x' }],
    ['Grep', { pattern: 'func', path: '/repo' }],
    ['Glob', { pattern: '**/*.kt' }],
    ['Edit', { file_path: '/repo/x' }],
    ['WebFetch', { url: 'https://github.com/mycroblog/mobile' }],
  ]) {
    const reason = ask(tool, input);
    assert.ok(reason, `${tool} ${JSON.stringify(input)} passed`);
    assert.match(reason, /use scripts\/drive \(scripts\/drive --help\), the screenshots it prints, and \.claude\/knowledge\/ios-evaluator\//);
  }
});

test('as a hook it prints a deny decision, and nothing for a call it lets through', () => {
  const hook = path.join(path.dirname(fileURLToPath(import.meta.url)), 'app-only.mjs');
  const run = (call) => execFileSync('node', [hook, 'android-walker'], { input: JSON.stringify(call), encoding: 'utf8' });
  const denied = JSON.parse(run({ tool_name: 'Bash', tool_input: { command: 'ls' } }));
  assert.equal(denied.hookSpecificOutput.permissionDecision, 'deny');
  assert.match(denied.hookSpecificOutput.permissionDecisionReason, /^android-walker judges the running app/);
  assert.equal(run({ tool_name: 'Bash', tool_input: { command: 'scripts/drive android look' } }), '');
});
