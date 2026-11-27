#!/usr/bin/env bash
# The seats of this Mac (ADR-0012 §2): one for a compile (xcodebuild, Gradle) and one for a booted device (a simulator
# or an emulator), shared by every check and by scripts/drive across all sessions. A seat is a directory, taken with
# mkdir, whose file `owner` says who holds it:
#   pid=<the process that holds it>  since=<epoch>  what=<what it is for>  device=<ios:UDID|android:SERIAL>  idle=<s>
# A seat whose pid is dead, or that has not been touched for `idle` seconds, is stale; the next taker shuts its device
# down and takes it over. On CI (CI is set) every command does nothing: each job is a machine of its own.
#
#   slot.sh take <seat> <pid> <what>   wait until <seat> is free (or stale), then hold it for <pid>
#   slot.sh set <seat> <pid> <key>=<value>...   change the owner record; pid=<new pid> hands the seat over
#   slot.sh touch <seat>               mark the seat as used now
#   slot.sh release <seat> <pid>       give the seat back, when <pid> holds it
#   slot.sh status                     who holds each seat, and every booted device
#   slot.sh orphans                    the booted devices that no live seat holds, one per line
#   slot.sh stop-hook                  the Stop hook of .claude/settings.json: makes the agent shut down orphans
set -euo pipefail

SLOT_DIR="${MYCROBLOG_SLOT_DIR:-/tmp/mycroblog-slots}"
SLOT_WAIT="${MYCROBLOG_SLOT_WAIT:-1800}" # seconds a taker waits before it gives up
SEATS=(compile device)

fail() { printf 'slot: %s\n' "$*" >&2; exit 1; }

seat_dir() {
  local seat="$1" s
  for s in "${SEATS[@]}"; do [[ "$s" == "$seat" ]] && { printf '%s/%s\n' "$SLOT_DIR" "$seat"; return; }; done
  fail "no seat $seat: the seats are ${SEATS[*]}."
}

# The value of <key> in the owner record of the seat directory $1, or nothing.
field() { sed -n "s/^$2=//p" "$1/owner" 2>/dev/null | head -n 1; }

alive() { [[ -n "$1" ]] && kill -0 "$1" 2>/dev/null; }

# GNU stat first: on Linux, `stat -f` reads the file system and succeeds with another number.
mtime() { stat -c %Y "$1" 2>/dev/null || stat -f %m "$1" 2>/dev/null || echo 0; }

# Whether the seat directory $1 is stale: its holder is dead, or it has not been touched for its idle limit.
stale() {
  local dir="$1" pid idle
  [[ -f "$dir/owner" ]] || {
    # A taker writes the owner right after mkdir; a directory without one for a minute belongs to a taker that died.
    (( $(date +%s) - $(mtime "$dir") > 60 ))
    return
  }
  pid="$(field "$dir" pid)"
  alive "$pid" || return 0
  idle="$(field "$dir" idle)"
  [[ -n "$idle" ]] && (( $(date +%s) - $(mtime "$dir/owner") > idle ))
}

adb_path() {
  local sdk="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
  printf '%s/platform-tools/adb\n' "$sdk"
}

# Shuts down the device <ios:UDID|android:SERIAL>.
shut_down() {
  case "$1" in
    ios:*) xcrun simctl shutdown "${1#ios:}" >/dev/null 2>&1 || true ;;
    android:*) "$(adb_path)" -s "${1#android:}" emu kill >/dev/null 2>&1 || true ;;
  esac
}

# Takes over the stale seat directory $1 that held <pid> $2: moves it aside, which only one taker can do, shuts its
# device down, and removes it. Returns non-zero when another taker moved it first, or it is no longer the stale one.
reclaim() {
  local dir="$1" seen="$2" aside device
  aside="$dir.stale.$$"
  mv "$dir" "$aside" 2>/dev/null || return 1
  if [[ "$(field "$aside" pid)" != "$seen" ]]; then
    # It was taken again between the look and the move: put it back.
    mv "$aside" "$dir" 2>/dev/null || rm -rf "$aside"
    return 1
  fi
  device="$(field "$aside" device)"
  [[ -n "$device" ]] && shut_down "$device"
  printf 'slot: took over the stale %s seat of process %s (%s)%s\n' "$(basename "$dir")" "${seen:-?}" \
    "$(field "$aside" what)" "${device:+, and shut down $device}" >&2
  rm -rf "$aside"
}

cmd_take() {
  local seat="$1" pid="$2" what="${3:-}" dir waited=0 holder
  [[ -n "${CI:-}" ]] && return 0
  dir="$(seat_dir "$seat")"
  mkdir -p "$SLOT_DIR"
  until mkdir "$dir" 2>/dev/null; do
    holder="$(field "$dir" pid)"
    [[ "$holder" == "$pid" ]] && return 0 # it holds the seat already
    if stale "$dir"; then
      reclaim "$dir" "$holder" || true
      continue
    fi
    (( waited == 0 )) && printf 'slot: waiting for the %s seat, held by process %s for %s (ADR-0012 §2)\n' \
      "$seat" "$holder" "$(field "$dir" what)" >&2
    (( waited >= SLOT_WAIT )) &&
      fail "the $seat seat was held for $SLOT_WAIT s by process $holder ($(field "$dir" what)): scripts/check/slot.sh status says what runs; if nothing does, kill $holder."
    sleep 5
    waited=$(( waited + 5 ))
  done
  printf 'pid=%s\nsince=%s\nwhat=%s\n' "$pid" "$(date +%s)" "$what" >"$dir/owner"
}

cmd_set() {
  local seat="$1" pid="$2" dir kv key
  shift 2
  [[ -n "${CI:-}" ]] && return 0
  dir="$(seat_dir "$seat")"
  [[ "$(field "$dir" pid)" == "$pid" ]] || fail "process $pid does not hold the $seat seat, so it cannot change it."
  for kv in "$@"; do
    key="${kv%%=*}"
    [[ "$key" =~ ^(pid|what|device|idle)$ ]] || fail "the owner record has no key $key."
    { grep -v "^$key=" "$dir/owner" || true; printf '%s\n' "$kv"; } >"$dir/owner.new"
    mv "$dir/owner.new" "$dir/owner"
  done
}

cmd_touch() {
  [[ -n "${CI:-}" ]] && return 0
  local dir
  dir="$(seat_dir "$1")"
  [[ -f "$dir/owner" ]] && touch "$dir/owner"
  return 0
}

cmd_release() {
  local seat="$1" pid="$2" dir
  [[ -n "${CI:-}" ]] && return 0
  dir="$(seat_dir "$seat")"
  [[ "$(field "$dir" pid)" == "$pid" ]] && rm -rf "$dir"
  return 0
}

# Every booted device, one per line as ios:UDID or android:SERIAL.
booted() {
  if command -v xcrun >/dev/null 2>&1; then
    xcrun simctl list devices booted 2>/dev/null | sed -n 's/.*(\([0-9A-F-]\{36\}\)) (Booted).*/ios:\1/p'
  fi
  local adb
  adb="$(adb_path)"
  [[ -x "$adb" ]] && "$adb" devices 2>/dev/null | awk '$1 ~ /^emulator-/ && $2 == "device" { print "android:" $1 }'
  return 0
}

cmd_orphans() {
  local dir held="" device
  dir="$(seat_dir device)"
  [[ -d "$dir" ]] && ! stale "$dir" && held="$(field "$dir" device)"
  while IFS= read -r device; do
    [[ -n "$device" && "$device" != "$held" ]] && printf '%s\n' "$device"
  done < <(booted)
  return 0
}

cmd_status() {
  local seat dir pid since device state
  for seat in "${SEATS[@]}"; do
    dir="$(seat_dir "$seat")"
    if [[ ! -d "$dir" ]]; then
      printf '%s: free\n' "$seat"
      continue
    fi
    pid="$(field "$dir" pid)"
    since="$(field "$dir" since)"
    device="$(field "$dir" device)"
    state=held
    stale "$dir" && state=stale
    printf '%s: %s by process %s for %s s%s: %s\n' "$seat" "$state" "${pid:-?}" "$(( $(date +%s) - ${since:-0} ))" \
      "${device:+, $device}" "$(field "$dir" what)"
  done
  printf 'booted devices:\n'
  booted | sed 's/^/  /'
  printf 'orphans (booted, held by no seat):\n'
  cmd_orphans | sed 's/^/  /'
}

cmd_stop_hook() {
  local input orphans
  input="$(cat)"
  # Once per stop: when the agent is already continuing because of this hook, let it stop.
  printf '%s' "$input" | grep -q '"stop_hook_active": *true' && return 0
  orphans="$(cmd_orphans | tr '\n' ' ')"
  [[ -z "$orphans" ]] && return 0
  local reason="These devices are booted, but no check or scripts/drive holds them (ADR-0012 §4, §5): ${orphans% }. If you or a role you started booted one, shut it down now (scripts/drive <os> stop, xcrun simctl shutdown <UDID>, or adb -s <serial> emu kill) and say so; if none is yours, say which are running."
  jq -cn --arg reason "$reason" '{decision: "block", reason: $reason}'
}

case "${1:-}" in
  take) [[ $# -ge 3 ]] || fail "take needs <seat> <pid> [what]"; cmd_take "$2" "$3" "${4:-}" ;;
  set) [[ $# -ge 4 ]] || fail "set needs <seat> <pid> <key>=<value>..."; cmd_set "${@:2}" ;;
  touch) [[ $# -eq 2 ]] || fail "touch needs <seat>"; cmd_touch "$2" ;;
  release) [[ $# -eq 3 ]] || fail "release needs <seat> <pid>"; cmd_release "$2" "$3" ;;
  status) cmd_status ;;
  orphans) cmd_orphans ;;
  stop-hook) cmd_stop_hook ;;
  *) fail "unknown command ${1:-(none)}: take, set, touch, release, status, orphans or stop-hook (the comment at the top says what each does)." ;;
esac
