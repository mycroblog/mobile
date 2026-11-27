#!/usr/bin/env bash
# The checks that the tasks of tasks/*.yml run: `scripts/check/run.sh <check>`. Each prints what is wrong and how to
# fix it, and exits non-zero when it fails. Run them through `task check` (or one area, `task docs:check`).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$ROOT"
MARKDOWNLINT="markdownlint-cli2@0.23.2"
KNOWLEDGE_LIMIT=4096 # bytes per file of .claude/knowledge, .claude/agents and .claude/skills (ADR-0011 §5)
IOS="$ROOT/app/ios"
IOS_BUILD="$IOS/build"
IOS_SIM_DESTINATION="generic/platform=iOS Simulator"
IOS_BUNDLE_ID="dev.mycroblog.app"
# The simulators the launch check creates and drives, by name: "<name>|<device type>".
IOS_LAUNCH_DEVICES=("mycroblog-check-phone|iPhone 17" "mycroblog-check-ipad|iPad Air 11-inch (M4)")
# The lock that lets only one simulator or emulator run at a time on this Mac, shared with the Android checks.
DEVICE_LOCK="/private/tmp/mycroblog-device.lock"

fail() { printf 'check: %s\n' "$*" >&2; exit 1; }

need() { command -v "$1" >/dev/null 2>&1 || fail "$1 is not installed: $2 (CONTRIBUTING.md says how)."; }

check_markdown() {
  need npx "install Node.js with asdf install nodejs"
  local out
  out="$(mktemp)"
  trap 'rm -f "$out"' RETURN
  (cd "$ROOT" && npx -y "$MARKDOWNLINT" "**/*.md" >/dev/null 2>"$out") || {
    cat "$out" >&2
    fail "fix each line above as its rule says (npx $MARKDOWNLINT --fix \"**/*.md\" fixes most), then check again."
  }
}

check_fullwidth_parens() {
  local hits
  hits="$(git -C "$ROOT" ls-files -z '*.md' | (cd "$ROOT" && xargs -0 grep -n -e '（' -e '）') || true)"
  if [[ -n "$hits" ]]; then
    printf '%s\n' "$hits" >&2
    fail 'full-width parentheses in Markdown: write ( and ) with a space before ( inside a sentence, and none before 、 or 。'
  fi
}

check_knowledge_size() {
  local failed=0 f size
  while IFS= read -r -d '' f; do
    size="$(wc -c <"$f")"
    if (( size > KNOWLEDGE_LIMIT )); then
      printf 'check: %s is %s bytes, over %s: split it by topic and name the new file in its index, or say it in fewer words.\n' \
        "${f#"$ROOT"/}" "$size" "$KNOWLEDGE_LIMIT" >&2
      failed=1
    fi
  done < <(find "$ROOT/.claude/knowledge" "$ROOT/.claude/agents" "$ROOT/.claude/skills" -name '*.md' -print0 2>/dev/null)
  return "$failed"
}

check_shell() {
  need shellcheck "install shellcheck with asdf install shellcheck"
  local files=("$ROOT/scripts/kit")
  shopt -s nullglob
  files+=("$ROOT"/scripts/*.sh "$ROOT"/scripts/*/*.sh)
  shopt -u nullglob
  shellcheck "${files[@]}" || fail "fix each finding above as its SC code's page on shellcheck.net says, then check again."
}

check_tools() {
  need node "install Node.js with asdf install nodejs"
  local out
  out="$(mktemp)"
  trap 'rm -f "$out"' RETURN
  (cd "$ROOT" && node --test tools/drive/ .claude/hooks/ >"$out" 2>&1) || {
    grep -E '^(not ok|# fail|✖)|Error' "$out" >&2 || cat "$out" >&2
    fail 'the tests of the app-driving tool or the hooks failed: run node --test tools/drive/ .claude/hooks/ and fix the code they name.'
  }
}

check_ios_generate() {
  need xcodegen "install XcodeGen with asdf install xcodegen"
  (cd "$IOS" && xcodegen generate --quiet) || fail "XcodeGen could not generate app/ios/project.yml: fix what it says above."
}

# The packages under app/ios/Packages, by name.
ios_packages() {
  local dir
  for dir in "$IOS"/Packages/*/; do
    [[ -f "$dir/Package.swift" ]] && basename "$dir"
  done
}

# Each package may import only the packages it declares as dependencies of the importing target (ADR-0009 §7); a
# module that comes in through another dependency still compiles, so the build alone does not refuse it.
check_ios_imports() {
  need jq "install jq with brew install jq"
  local failed=0 pkg target deps file module ours
  ours=" $(ios_packages | tr '\n' ' ') "
  for pkg in $(ios_packages); do
    while IFS=$'\t' read -r target deps; do
      while IFS= read -r -d '' file; do
        while IFS= read -r module; do
          [[ "$module" == "$target" || "$ours" != *" $module "* || " $deps " == *" $module "* ]] && continue
          printf 'check: %s imports %s, which its target %s does not declare: depend on it in %s if ADR-0009 §7 allows that direction, or move the code.\n' \
            "${file#"$ROOT"/}" "$module" "$target" "app/ios/Packages/$pkg/Package.swift" >&2
          failed=1
        done < <(sed -n -E 's/^[[:space:]]*(@[A-Za-z_]+[[:space:]]+)*((public|package|internal|fileprivate|private)[[:space:]]+)?import[[:space:]]+((typealias|struct|class|enum|protocol|let|var|func)[[:space:]]+)?([A-Za-z_][A-Za-z0-9_]*).*/\6/p' "$file")
      done < <(find "$IOS/Packages/$pkg/Sources/$target" -name '*.swift' -print0 2>/dev/null)
    done < <(cd "$IOS/Packages/$pkg" && swift package dump-package |
      jq -r '.targets[] | [.name, ([.dependencies[] | (.byName // .target // .product)[0]] | join(" "))] | @tsv')
  done
  return "$failed"
}

# Runs an xcodebuild build for the iOS Simulator and prints only its errors when it fails.
ios_xcodebuild() {
  local out
  out="$(mktemp)"
  if ! xcodebuild -destination "$IOS_SIM_DESTINATION" "$@" build >"$out" 2>&1; then
    grep -E 'error:|BUILD FAILED' "$out" >&2 || tail -n 30 "$out" >&2
    rm -f "$out"
    return 1
  fi
  rm -f "$out"
}

# Each package builds on its own, with a build folder of its own that holds only what it declares; an import of a
# package outside its dependencies then fails to build, in a clean build and in an incremental one alike.
check_ios_packages() {
  local failed=0 pkg
  for pkg in $(ios_packages); do
    (cd "$IOS/Packages/$pkg" && ios_xcodebuild -scheme "$pkg" -derivedDataPath "$IOS_BUILD/packages/$pkg" -quiet) || {
      printf 'check: the package %s does not build on its own: fix the errors above; an unresolved module means it imports a package it does not declare (ADR-0009 §7).\n' "$pkg" >&2
      failed=1
    }
  done
  return "$failed"
}

check_ios_build() {
  [[ -d "$IOS/Mycroblog.xcodeproj" ]] || check_ios_generate
  ios_xcodebuild -project "$IOS/Mycroblog.xcodeproj" -scheme Mycroblog -derivedDataPath "$IOS_BUILD/app" -quiet ||
    fail "the iOS app does not build: fix the errors above."
}

# The newest installed iOS simulator runtime of the selected Xcode's SDK.
ios_sim_runtime() {
  local sdk
  sdk="$(xcrun --sdk iphonesimulator --show-sdk-version)"
  xcrun simctl list runtimes -j | jq -r --arg v "$sdk" \
    '[.runtimes[] | select(.platform == "iOS" and .isAvailable and .version == $v)] | sort_by(.identifier | length) | .[0].identifier // empty'
}

# The UDID of the simulator of this name, created when it does not exist.
ios_sim_device() {
  local name="$1" type="$2" runtime udid
  udid="$(xcrun simctl list devices -j | jq -r --arg n "$name" '[.devices[][] | select(.name == $n and .isAvailable)][0].udid // empty')"
  if [[ -z "$udid" ]]; then
    runtime="$(ios_sim_runtime)"
    [[ -n "$runtime" ]] || fail "no iOS Simulator runtime for the iOS SDK of the selected Xcode: install it with xcodebuild -downloadPlatform iOS."
    udid="$(xcrun simctl create "$name" "$type" "$runtime")" || fail "could not create the simulator $name ($type): install the device type with Xcode, or change IOS_LAUNCH_DEVICES in ${BASH_SOURCE[0]#"$ROOT"/}."
  fi
  printf '%s\n' "$udid"
}

# Takes the lock that lets one simulator or emulator run at a time on this Mac, waiting while another check holds it.
take_device_lock() {
  local waited=0
  until mkdir "$DEVICE_LOCK" 2>/dev/null; do
    (( waited == 0 )) && printf 'check: waiting for %s, held by another simulator or emulator run.\n' "$DEVICE_LOCK" >&2
    (( waited >= 1800 )) && fail "$DEVICE_LOCK has been held for 30 minutes: if no simulator or emulator is running, rmdir $DEVICE_LOCK and check again."
    sleep 5
    waited=$((waited + 5))
  done
}

IOS_BOOTED=""
ios_launch_cleanup() {
  [[ -n "$IOS_BOOTED" ]] && xcrun simctl shutdown "$IOS_BOOTED" >/dev/null 2>&1
  rmdir "$DEVICE_LOCK" 2>/dev/null || true
}

# Installs the app on each simulator of IOS_LAUNCH_DEVICES in turn, launches it, and confirms that its process is
# still running a few seconds later; one simulator runs at a time and each is shut down after.
check_ios_launch() {
  need jq "install jq with brew install jq"
  local app="$IOS_BUILD/app/Build/Products/Debug-iphonesimulator/Mycroblog.app" entry name type udid pid running
  [[ -d "$app" ]] || check_ios_build
  take_device_lock
  trap ios_launch_cleanup EXIT
  for entry in "${IOS_LAUNCH_DEVICES[@]}"; do
    name="${entry%%|*}"
    type="${entry#*|}"
    udid="$(ios_sim_device "$name" "$type")"
    IOS_BOOTED="$udid"
    xcrun simctl bootstatus "$udid" -b >/dev/null || fail "the simulator $name did not boot: open Simulator and boot $name to see why."
    xcrun simctl install "$udid" "$app" || fail "could not install the app on $name."
    pid="$(xcrun simctl launch "$udid" "$IOS_BUNDLE_ID" | sed -n 's/.*: //p')"
    [[ -n "$pid" ]] || fail "the app did not launch on $name: run xcrun simctl launch --console $udid $IOS_BUNDLE_ID to see why."
    sleep 3
    running="$(xcrun simctl spawn "$udid" launchctl list | awk -v id="UIKitApplication:$IOS_BUNDLE_ID" 'index($3, id) == 1 { print $1 }')"
    [[ "$running" == "$pid" ]] ||
      fail "the app launched on $name as process $pid but is not running 3 s later: see its crash log in ~/Library/Logs/DiagnosticReports, or run xcrun simctl launch --console $udid $IOS_BUNDLE_ID."
    printf 'ios: the app runs on %s (%s) as process %s.\n' "$name" "$type" "$pid"
    xcrun simctl shutdown "$udid" >/dev/null
    IOS_BOOTED=""
  done
}

# The version a tool of .tool-versions reports, in the form .tool-versions pins it, or nothing when it does not run.
installed_version() {
  case "$1" in
    nodejs) node --version 2>/dev/null | sed 's/^v//' ;;
    shellcheck) shellcheck --version 2>/dev/null | sed -n 's/^version: //p' ;;
    java) java -version 2>&1 | sed -n 's/.*version "\([^"]*\)".*/\1/p' | head -n 1 ;;
    task) task --version 2>/dev/null | sed -e 's/^Task version: //' -e 's/^v//' -e 's/ .*//' ;;
    xcodegen) xcodegen --version 2>/dev/null | sed -n 's/^Version: //p' ;;
    *) printf 'unknown\n' ;;
  esac
}

# The part of a pinned version that the tool itself reports: temurin-21.0.13+9.0.LTS is java 21.0.13.
comparable_version() {
  case "$1" in
    java) sed -e 's/^[a-z]*-//' -e 's/+.*//' <<<"$2" ;;
    *) printf '%s\n' "$2" ;;
  esac
}

check_tool_versions() {
  local failed=0 tool pinned want have
  while read -r tool pinned _; do
    [[ -z "$tool" || "$tool" == \#* ]] && continue
    want="$(comparable_version "$tool" "$pinned")"
    have="$(installed_version "$tool" || true)"
    if [[ "$have" == "unknown" ]]; then
      printf 'check: .tool-versions pins %s, which this check does not know: add it to installed_version in %s.\n' \
        "$tool" "${BASH_SOURCE[0]#"$ROOT"/}" >&2
      failed=1
    elif [[ -z "$have" ]]; then
      printf 'check: %s %s is not installed: asdf plugin add %s && asdf install %s %s (CONTRIBUTING.md).\n' \
        "$tool" "$pinned" "$tool" "$tool" "$pinned" >&2
      failed=1
    elif [[ "$have" != "$want" ]]; then
      printf 'check: %s is %s, but .tool-versions pins %s: asdf install %s %s, and make sure asdf shims come first on PATH (CONTRIBUTING.md).\n' \
        "$tool" "$have" "$pinned" "$tool" "$pinned" >&2
      failed=1
    fi
  done <"$ROOT/.tool-versions"
  return "$failed"
}

check_xcode_version() {
  local want have
  want="$(tr -d '[:space:]' <"$ROOT/.xcode-version")"
  have="$(xcodebuild -version 2>/dev/null | sed -n 's/^Xcode //p' | head -n 1 || true)"
  if [[ -z "$have" ]]; then
    fail "Xcode is not selected: install Xcode $want from the App Store or developer.apple.com, then sudo xcode-select -s /Applications/Xcode.app (CONTRIBUTING.md)."
  elif [[ "$have" != "$want" ]]; then
    fail "the selected Xcode is $have, but .xcode-version pins $want, the latest released Xcode (ADR-0009 §7): install it and sudo xcode-select -s <its path>, or update .xcode-version in a pull request of its own when a newer Xcode is released."
  fi
}

case "${1:-}" in
  markdown) check_markdown ;;
  fullwidth-parens) check_fullwidth_parens ;;
  knowledge-size) check_knowledge_size ;;
  shell) check_shell ;;
  tools) check_tools ;;
  tool-versions) check_tool_versions ;;
  xcode-version) check_xcode_version ;;
  ios-generate) check_ios_generate ;;
  ios-imports) check_ios_imports ;;
  ios-packages) check_ios_packages ;;
  ios-build) check_ios_build ;;
  ios-launch) check_ios_launch ;;
  *) fail "unknown check ${1:-(none)}: run the checks with task check (task --list shows them)." ;;
esac
