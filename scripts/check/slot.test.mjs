// Tests of the seats of this Mac (scripts/check/slot.sh, ADR-0012 §2), with a fake xcrun and no device.
import assert from 'node:assert/strict';
import { execFileSync, spawnSync } from 'node:child_process';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';

const SLOT = path.join(path.dirname(fileURLToPath(import.meta.url)), 'slot.sh');
const UDID_A = '11111111-1111-1111-1111-111111111111';
const UDID_B = '22222222-2222-2222-2222-222222222222';

function setup({ booted = [] } = {}) {
  const dir = fs.mkdtempSync(path.join(os.tmpdir(), 'slot-test-'));
  const bin = path.join(dir, 'bin');
  fs.mkdirSync(bin);
  const log = path.join(dir, 'xcrun.log');
  const list = booted.map((u) => `    iPhone 17 (${u}) (Booted) `).join('\n');
  fs.writeFileSync(path.join(bin, 'xcrun'), `#!/bin/sh
echo "$*" >> '${log}'
[ "$2" = list ] && printf '%s\\n' '== Devices ==' '${list}'
exit 0
`, { mode: 0o755 });
  const env = {
    PATH: `${bin}:/usr/bin:/bin`,
    HOME: dir,
    ANDROID_HOME: path.join(dir, 'no-sdk'),
    MYCROBLOG_SLOT_DIR: path.join(dir, 'seats'),
    MYCROBLOG_SLOT_WAIT: '0',
  };
  const run = (args, { input, extra } = {}) => spawnSync('bash', [SLOT, ...args], { env: { ...env, ...extra }, input, encoding: 'utf8' });
  const owner = (seat) => fs.readFileSync(path.join(env.MYCROBLOG_SLOT_DIR, seat, 'owner'), 'utf8');
  const shutdowns = () => (fs.existsSync(log) ? fs.readFileSync(log, 'utf8') : '').split('\n').filter((l) => l.includes('shutdown'));
  return { run, owner, shutdowns, env };
}

// A process id that no process has: one that has exited.
const deadPid = () => {
  const child = spawnSync('sh', ['-c', 'echo $$'], { encoding: 'utf8' });
  return child.stdout.trim();
};
const live = String(process.pid);

test('a free seat is taken and given back only by its holder', () => {
  const s = setup();
  assert.equal(s.run(['take', 'compile', live, 'xcodebuild']).status, 0);
  assert.match(s.owner('compile'), new RegExp(`^pid=${live}\nsince=\\d+\nwhat=xcodebuild\n$`));
  assert.equal(s.run(['take', 'compile', live, 'again']).status, 0, 'its holder takes it again at once');
  s.run(['release', 'compile', '1']);
  assert.match(s.owner('compile'), /what=xcodebuild/, 'another process cannot give it back');
  s.run(['release', 'compile', live]);
  assert.equal(fs.existsSync(path.join(s.env.MYCROBLOG_SLOT_DIR, 'compile')), false);
});

test('a held seat makes the next taker wait, and says who holds it', () => {
  const s = setup();
  s.run(['take', 'device', live, 'the iOS tests']);
  const r = s.run(['take', 'device', '1', 'scripts/drive']);
  assert.notEqual(r.status, 0);
  assert.match(r.stderr, new RegExp(`waiting for the device seat, held by process ${live} for the iOS tests`));
  assert.match(r.stderr, /slot.sh status says what runs/);
});

test('a seat whose holder died is taken over, and its device is shut down first', () => {
  const s = setup();
  const dead = deadPid();
  s.run(['take', 'device', live, 'scripts/drive']);
  s.run(['set', 'device', live, `pid=${dead}`, `device=ios:${UDID_A}`]);
  const r = s.run(['take', 'device', live, 'the iOS tests']);
  assert.equal(r.status, 0, r.stderr);
  assert.match(r.stderr, new RegExp(`took over the stale device seat of process ${dead} \\(scripts/drive\\), and shut down ios:${UDID_A}`));
  assert.deepEqual(s.shutdowns(), [`simctl shutdown ${UDID_A}`]);
  assert.match(s.owner('device'), /what=the iOS tests/);
});

test('a seat untouched for longer than its idle limit is taken over', () => {
  const s = setup();
  s.run(['take', 'device', live, 'scripts/drive']);
  s.run(['set', 'device', live, 'idle=60', `device=ios:${UDID_A}`]);
  const ownerFile = path.join(s.env.MYCROBLOG_SLOT_DIR, 'device', 'owner');
  const old = new Date(Date.now() - 120_000);
  fs.utimesSync(ownerFile, old, old);
  assert.notEqual(s.run(['take', 'device', '1', 'x'], { extra: { MYCROBLOG_SLOT_WAIT: '0' } }).stderr.match(/took over/), null);
  fs.rmSync(path.join(s.env.MYCROBLOG_SLOT_DIR, 'device'), { recursive: true });
  s.run(['take', 'device', live, 'scripts/drive']);
  s.run(['set', 'device', live, 'idle=60']);
  s.run(['touch', 'device']);
  assert.notEqual(s.run(['take', 'device', '1', 'x']).status, 0, 'a touched seat is not stale');
});

test('only the holder changes the record, and pid= hands the seat over', () => {
  const s = setup();
  s.run(['take', 'device', live, 'scripts/drive']);
  const refused = s.run(['set', 'device', '1', 'device=ios:X']);
  assert.notEqual(refused.status, 0);
  assert.match(refused.stderr, /process 1 does not hold the device seat/);
  assert.notEqual(s.run(['set', 'device', live, 'colour=red']).status, 0);
  assert.equal(s.run(['set', 'device', live, 'pid=4242', 'device=ios:X']).status, 0);
  assert.match(s.owner('device'), /pid=4242/);
  assert.match(s.owner('device'), /device=ios:X/);
  assert.doesNotMatch(s.owner('device'), new RegExp(`pid=${live}`));
});

test('the orphans are the booted devices that no live seat holds', () => {
  const s = setup({ booted: [UDID_A, UDID_B] });
  assert.equal(s.run(['orphans']).stdout, `ios:${UDID_A}\nios:${UDID_B}\n`);
  s.run(['take', 'device', live, 'the iOS tests']);
  s.run(['set', 'device', live, `device=ios:${UDID_A}`]);
  assert.equal(s.run(['orphans']).stdout, `ios:${UDID_B}\n`);
  assert.match(s.run(['status']).stdout, new RegExp(`device: held by process ${live} for \\d+ s, ios:${UDID_A}: the iOS tests`));
});

test('the Stop hook makes the agent deal with orphans, once per stop', () => {
  const s = setup({ booted: [UDID_B] });
  const r = s.run(['stop-hook'], { input: '{"session_id":"s","stop_hook_active":false}' });
  const out = JSON.parse(r.stdout);
  assert.equal(out.decision, 'block');
  assert.match(out.reason, new RegExp(`ios:${UDID_B}. If you or a role you started booted one, shut it down`));
  assert.equal(s.run(['stop-hook'], { input: '{"stop_hook_active": true}' }).stdout, '');
  assert.equal(setup().run(['stop-hook'], { input: '{}' }).stdout, '', 'no orphans, nothing to say');
});

test('on CI every command is a no-op', () => {
  const s = setup();
  assert.equal(s.run(['take', 'compile', live, 'x'], { extra: { CI: 'true' } }).status, 0);
  assert.equal(fs.existsSync(path.join(s.env.MYCROBLOG_SLOT_DIR, 'compile')), false);
});

test('an unknown seat or command says what exists', () => {
  const s = setup();
  assert.match(s.run(['take', 'gpu', live]).stderr, /no seat gpu: the seats are compile device/);
  assert.match(s.run(['jump']).stderr, /unknown command jump: take, set, touch, release/);
});

execFileSync('bash', ['-n', SLOT]);
