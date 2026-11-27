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

# The Android app (app/and) and the SDK packages it is built and launched with (CONTRIBUTING.md).
ANDROID_SDK="${ANDROID_HOME:-$HOME/Library/Android/sdk}"
ANDROID_APP="$ROOT/app/and"
ANDROID_PACKAGE="dev.mycroblog.app"
ANDROID_SDK_PACKAGES=("platforms;android-37.2" "build-tools;37.0.0" "platform-tools" "emulator" "cmdline-tools;latest")
ANDROID_IMAGE="system-images;android-37.0;google_apis_playstore;arm64-v8a"
ANDROID_CONSOLE_PORT=5620

need_android_sdk() {
  local pkg
  [[ -d "$ANDROID_SDK" ]] ||
    fail "the Android SDK is not at $ANDROID_SDK: install it, or set ANDROID_HOME to where it is (CONTRIBUTING.md)."
  for pkg in "$@"; do
    [[ -d "$ANDROID_SDK/${pkg//;//}" ]] ||
      fail "the Android SDK package $pkg is not installed: $ANDROID_SDK/cmdline-tools/latest/bin/sdkmanager \"$pkg\" (CONTRIBUTING.md)."
  done
}

android_gradle() {
  (cd "$ANDROID_APP" && ANDROID_HOME="$ANDROID_SDK" ./gradlew --console=plain -q "$@")
}

check_android_build() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}"
  # The build also fails when a module depends on one that ADR-0010 §6 forbids, saying what to do instead.
  android_gradle assembleDebug ||
    fail 'the Android build failed: fix what Gradle says above (cd app/and && ./gradlew assembleDebug), then check again.'
}

EMULATOR_PID=""
HOLDS_DEVICE_LOCK=""

stop_emulator() {
  if [[ -n "$EMULATOR_PID" ]] && kill -0 "$EMULATOR_PID" 2>/dev/null; then
    "$ANDROID_SDK/platform-tools/adb" -s "emulator-$ANDROID_CONSOLE_PORT" emu kill >/dev/null 2>&1 ||
      kill "$EMULATOR_PID" 2>/dev/null || true
    wait "$EMULATOR_PID" 2>/dev/null || true
  fi
  EMULATOR_PID=""
}

release_device() {
  stop_emulator
  if [[ -n "$HOLDS_DEVICE_LOCK" ]]; then
    rmdir "$DEVICE_LOCK" 2>/dev/null || true
    HOLDS_DEVICE_LOCK=""
  fi
}

# Takes the lock that lets one simulator or emulator run at a time on this Mac, waiting while another check holds it.
take_device_lock() {
  local waited=0
  until mkdir "$DEVICE_LOCK" 2>/dev/null; do
    (( waited == 0 )) && printf 'check: waiting for %s, held while another simulator or emulator runs\n' "$DEVICE_LOCK" >&2
    (( waited >= 1800 )) &&
      fail "$DEVICE_LOCK was held for 30 minutes: if no simulator or emulator is running, rmdir it and check again."
    sleep 5
    waited=$(( waited + 5 ))
  done
  HOLDS_DEVICE_LOCK=1
}

# Boots the AVD drive-<kind> (the one scripts/drive uses), creating it when it is missing, installs and launches the
# app, confirms that it runs with its activity in front, and stops the emulator.
launch_android_on() {
  local profile="$2" avd="drive-$1" serial="emulator-$ANDROID_CONSOLE_PORT" adb deadline log
  adb="$ANDROID_SDK/platform-tools/adb"
  if ! "$ANDROID_SDK/emulator/emulator" -list-avds | grep -qx "$avd"; then
    echo no | "$ANDROID_SDK/cmdline-tools/latest/bin/avdmanager" create avd -n "$avd" -k "$ANDROID_IMAGE" -d "$profile" >/dev/null ||
      fail "could not create the AVD $avd from $ANDROID_IMAGE: create it in Android Studio's Device Manager as a $profile, then check again."
  fi
  log="$(mktemp)"
  "$ANDROID_SDK/emulator/emulator" -avd "$avd" -port "$ANDROID_CONSOLE_PORT" -no-window -no-snapshot-save -no-boot-anim \
    -no-audio -gpu host >"$log" 2>&1 &
  EMULATOR_PID=$!
  "$adb" start-server >/dev/null 2>&1
  deadline=$(( SECONDS + 300 ))
  until [[ "$("$adb" -s "$serial" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" == 1 ]]; do
    if ! kill -0 "$EMULATOR_PID" 2>/dev/null; then
      tail -n 20 "$log" >&2
      fail "the emulator of $avd stopped before it booted (above): if $avd runs elsewhere, stop it, then check again."
    fi
    (( SECONDS < deadline )) || fail "$avd did not boot in 5 minutes: run it from Android Studio's Device Manager to see why."
    sleep 2
  done
  rm -f "$log"
  "$adb" -s "$serial" install -r "$ANDROID_APP/app/build/outputs/apk/debug/app-debug.apk" >/dev/null ||
    fail "could not install the app on $avd: run adb -s $serial install app/and/app/build/outputs/apk/debug/app-debug.apk to see why."
  "$adb" -s "$serial" shell am start -W -n "$ANDROID_PACKAGE/.MainActivity" | grep -q 'Status: ok' ||
    fail "the app did not start on $avd: adb -s $serial logcat -b crash says why."
  sleep 3 # a crash right after the first frame
  if [[ -z "$("$adb" -s "$serial" shell pidof "$ANDROID_PACKAGE" | tr -d '\r')" ]] ||
    ! "$adb" -s "$serial" shell dumpsys activity activities | grep -E 'topResumedActivity|ResumedActivity' |
      grep -q "$ANDROID_PACKAGE/.MainActivity"; then
    "$adb" -s "$serial" logcat -b crash -d >&2 || true
    fail "the app stopped or left the front on $avd right after it started: fix the crash above, then check again."
  fi
  printf 'check: %s runs on %s (%s)\n' "$ANDROID_PACKAGE" "$avd" "$profile"
  stop_emulator
}

check_android_launch() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}" "$ANDROID_IMAGE"
  android_gradle :app:assembleDebug ||
    fail 'the Android build failed: fix what Gradle says above (cd app/and && ./gradlew :app:assembleDebug), then check again.'
  trap release_device EXIT
  take_device_lock
  launch_android_on phone pixel_9
  launch_android_on tablet pixel_tablet
  release_device
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
  android-build) check_android_build ;;
  android-launch) check_android_launch ;;
  *) fail "unknown check ${1:-(none)}: run the checks with task check (task --list shows them)." ;;
esac
