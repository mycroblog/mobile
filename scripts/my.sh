#!/usr/bin/env bash
# A local my for the integration and UI-flow tests (ADR-0006 §1): `scripts/my.sh up` starts my's demo server, the test
# build of bin/my with its fakes (my ADR-0015 §6) on a fresh SQLite database, seeded with its scenario (§5), and prints
# its URL; `scripts/my.sh down` stops it; `scripts/my.sh url` prints the URL of the one running.
#
# It runs the my commit of shared/api/my-commit, so that the server speaks the API the clients were generated from: a
# detached worktree of the my checkout (MY, default ../my) in .tmp/my. MY_PORT (default 8790) is its port.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MY="${MY:-$ROOT/../my}"
PORT="${MY_PORT:-8790}"
STATE="$ROOT/.tmp"
TREE="$STATE/my"
PIDFILE="$STATE/my.pid"
LOG="$STATE/my.log"
READY_TIMEOUT=900 # seconds; the first start builds bin/my and the web client

fail() { printf 'my: %s\n' "$*" >&2; exit 1; }

url() { printf 'http://localhost:%s\n' "$PORT"; }

running() { [[ -f "$PIDFILE" ]] && kill -0 "$(cat "$PIDFILE")" 2>/dev/null; }

answers() { curl -fsS -o /dev/null --max-time 5 "$(url)/api/v1/instance" 2>/dev/null; }

# The worktree of my at the recorded commit, made or moved there.
prepare_tree() {
  local commit
  commit="$(tr -d '[:space:]' <"$ROOT/shared/api/my-commit")"
  git -C "$MY" rev-parse --git-dir >/dev/null 2>&1 ||
    fail "$MY is not a checkout of mycroblog/my: git clone https://github.com/mycroblog/my next to this repository, or set MY to one."
  git -C "$MY" cat-file -e "$commit^{commit}" 2>/dev/null || git -C "$MY" fetch -q origin ||
    fail "could not fetch $commit into $MY: fetch it there by hand, then try again."
  git -C "$MY" cat-file -e "$commit^{commit}" 2>/dev/null ||
    fail "$MY has no commit $commit (shared/api/my-commit): it is not a commit of mycroblog/my, or $MY's origin is another repository."
  mkdir -p "$STATE"
  if [[ -e "$TREE/.git" ]]; then
    git -C "$TREE" checkout -q --detach "$commit"
  else
    git -C "$MY" worktree add -q --detach "$TREE" "$commit"
  fi
  need bun "install the tools of $TREE/.tool-versions with asdf install (in $TREE)"
  need go "install the tools of $TREE/.tool-versions with asdf install (in $TREE)"
  (cd "$TREE" && bun install --frozen-lockfile >/dev/null) ||
    fail "bun install failed in $TREE: run it there to see why."
}

need() { (cd "$TREE" && command -v "$1" >/dev/null 2>&1) || fail "$1 is not installed: $2."; }

up() {
  if running && answers; then
    url
    return
  fi
  running && down
  answers && fail "something else answers on port $PORT: stop it, or set MY_PORT to a free port."
  prepare_tree
  : >"$LOG"
  # Job control gives the server its own process group, which down signals as a whole.
  set -m
  (cd "$TREE" && DEMO_PORT="$PORT" exec nohup scripts/task.sh web:demo) >>"$LOG" 2>&1 &
  echo $! >"$PIDFILE"
  set +m
  local waited=0
  until grep -q 'demo: ready at' "$LOG"; do
    if ! running; then
      tail -n 30 "$LOG" >&2
      fail "my stopped before it was ready: read $LOG."
    fi
    (( waited >= READY_TIMEOUT )) && { down; fail "my was not ready in $((READY_TIMEOUT / 60)) minutes: read $LOG."; }
    sleep 2
    waited=$((waited + 2))
  done
  url
}

down() {
  if running; then
    local pid
    pid="$(cat "$PIDFILE")"
    # The demo stops the server and its fakes on SIGTERM; signal its whole process group.
    kill -TERM -- "-$pid" 2>/dev/null || kill -TERM "$pid" 2>/dev/null || true
    for _ in $(seq 1 30); do kill -0 "$pid" 2>/dev/null || break; sleep 1; done
    if kill -0 "$pid" 2>/dev/null; then kill -KILL -- "-$pid" 2>/dev/null || true; fi
  fi
  rm -f "$PIDFILE"
}

case "${1:-}" in
  up) up ;;
  down) down ;;
  url)
    if ! { running && answers; }; then fail "no local my is running: start it with task my:up."; fi
    url ;;
  *) fail "usage: scripts/my.sh up | down | url" ;;
esac
