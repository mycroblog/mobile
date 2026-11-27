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
DEVICE_LOCK="/tmp/mycroblog-device.lock" # /private/tmp on a Mac

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

# The hand-written Swift of the repository, committed or not, NUL-separated: everything but the generated client
# (ADR-0003 §2) and what .gitignore leaves out, such as the build output. With an argument, only the files under it.
IOS_SWIFT_FORMAT_CONFIG="$ROOT/.swift-format"
ios_swift_files() {
  local file
  while IFS= read -r -d '' file; do
    [[ -f "$ROOT/$file" ]] && printf '%s\0' "$file"
  done < <(git -C "$ROOT" ls-files -z --cached --others --exclude-standard -- "${@:-.}" |
    grep -z '\.swift$' | grep -zv '^app/ios/Packages/API/Sources/GeneratedAPI/')
}

# swift format, from the selected Xcode's toolchain, with the configuration of .swift-format.
ios_swift_format() {
  need swift "install Xcode $(tr -d '[:space:]' <"$ROOT/.xcode-version") (CONTRIBUTING.md)"
  (cd "$ROOT" && ios_swift_files | xargs -0 swift format "$@" --configuration "$IOS_SWIFT_FORMAT_CONFIG")
}

check_ios_lint() {
  ios_swift_format lint --strict --parallel ||
    fail 'the Swift above is not formatted as .swift-format says: run task ios:format, fix by hand what it leaves, then check again.'
}

check_ios_format() {
  ios_swift_format format --in-place --parallel || fail 'swift format could not format the files above: fix what it says.'
  ios_swift_format lint --strict --parallel ||
    fail 'swift format formatted the code but cannot fix the findings above by itself: fix each by hand, then check again.'
}

# ADR-0009 §1: the screens of the app (its sources, not its tests) are laid out by the size classes and the
# container's size, never by the device idiom, the orientation, the screen's size or a fixed width.
# scripts/check/ios-layout.pl holds the rule and what it allows.
check_ios_layout() {
  need perl "perl ships with macOS"
  local files=() file
  while IFS= read -r -d '' file; do
    files+=("$file")
  done < <(ios_swift_files app/ios/App/Sources 'app/ios/Packages/*/Sources/*')
  (( ${#files[@]} )) || return 0
  (cd "$ROOT" && perl "$ROOT/scripts/check/ios-layout.pl" "${files[@]}")
}

# The edges of the package graph that ADR-0009 §7 allows, as "<package>|<the packages it may declare>". The
# extensions use only Domain and API (ADR-0007 §1), which therefore declare no UI and not AppData; only the app and
# Features declare AppData, the store and the sync (ADR-0008 §5); the screens read the store, never the API
# (ADR-0008 §1); DesignSystem stands on nothing. A new package needs a line here, with the ADR that allows its edges.
IOS_PACKAGE_EDGES=(
  "Domain|"
  "DesignSystem|"
  "API|Domain"
  "AppData|Domain API"
  "Features|Domain AppData DesignSystem"
)
# What a target of app/ios/project.yml other than the app and its tests may declare: an extension (ADR-0007 §1).
IOS_EXTENSION_PACKAGES="Domain API"

ios_allowed_edges() {
  local edge
  for edge in "${IOS_PACKAGE_EDGES[@]}"; do
    [[ "${edge%%|*}" == "$1" ]] && { printf '%s\n' "${edge#*|}"; return 0; }
  done
  return 1
}

# The declared graph against the allowed edges: the local packages each Package.swift declares, and the packages each
# target of project.yml declares. Then that the generated client stays inside the API package: its types are
# `package` (openapi-generator-config.yaml), no product of the API package holds the target GeneratedAPI, and no
# file outside the package imports it.
check_ios_dependencies() {
  need jq "install jq with brew install jq"
  need xcodegen "install XcodeGen with asdf install xcodegen"
  local failed=0 pkg allowed dep path target type deps file
  for pkg in $(ios_packages); do
    if ! allowed="$(ios_allowed_edges "$pkg")"; then
      printf 'check: the package %s has no line in IOS_PACKAGE_EDGES of scripts/check/run.sh: add the packages it may declare, as ADR-0009 §7 allows.\n' "$pkg" >&2
      failed=1
      continue
    fi
    while IFS= read -r path; do
      dep="$(basename "$path")"
      if [[ "$(dirname "$path")" != "$IOS/Packages" ]]; then
        printf 'check: app/ios/Packages/%s/Package.swift declares the local package %s outside app/ios/Packages: move it there and give it a line in IOS_PACKAGE_EDGES.\n' "$pkg" "${path#"$ROOT"/}" >&2
        failed=1
      elif [[ " $allowed " != *" $dep "* ]]; then
        printf 'check: app/ios/Packages/%s/Package.swift declares %s, against the direction of ADR-0009 §7 (%s may declare: %s): remove it and move the code that needs it into a package allowed to.\n' \
          "$pkg" "$dep" "$pkg" "${allowed:-no other package}" >&2
        failed=1
      fi
    done < <(cd "$IOS/Packages/$pkg" && swift package dump-package | jq -r '.dependencies[].fileSystem[0].path // empty')
  done
  while IFS=$'\t' read -r target type deps; do
    [[ "$type" == application || "$type" == bundle.* ]] && continue
    for dep in $deps; do
      [[ " $IOS_EXTENSION_PACKAGES " == *" $dep "* ]] && continue
      printf 'check: the target %s (%s) of app/ios/project.yml declares the package %s: an extension uses only %s (ADR-0007 §1, ADR-0009 §7); the store and the screens are the app'"'"'s.\n' \
        "$target" "$type" "$dep" "$IOS_EXTENSION_PACKAGES" >&2
      failed=1
    done
  done < <(cd "$IOS" && xcodegen dump --type json |
    jq -r '.targets | to_entries[] | [.key, .value.type, ([.value.dependencies[]? | .package // empty] | join(" "))] | @tsv')
  grep -Eq '^accessModifier:[[:space:]]*package[[:space:]]*$' "$IOS_API/openapi-generator-config.yaml" || {
    printf 'check: app/ios/Packages/API/openapi-generator-config.yaml must say accessModifier: package, so that only the API package sees the generated types (ADR-0009 §7): set it back and run task api:generate.\n' >&2
    failed=1
  }
  if (cd "$IOS_API" && swift package dump-package) | jq -e '.products[] | select(.targets | index("GeneratedAPI"))' >/dev/null; then
    printf 'check: a product of app/ios/Packages/API/Package.swift holds the target GeneratedAPI: remove it; the product API exposes hand-written types only (ADR-0009 §7).\n' >&2
    failed=1
  fi
  while IFS= read -r -d '' file; do
    [[ "$file" == app/ios/Packages/API/* ]] && continue
    grep -Eq '^[[:space:]]*(@[A-Za-z_]+[[:space:]]+)*((public|package|internal|fileprivate|private)[[:space:]]+)?import[[:space:]]+((typealias|struct|class|enum|protocol|let|var|func)[[:space:]]+)?GeneratedAPI\b' "$ROOT/$file" || continue
    printf 'check: %s imports GeneratedAPI, which only the API package may use (ADR-0009 §7): use the hand-written types of the product API, and add one there when it lacks it.\n' "$file" >&2
    failed=1
  done < <(ios_swift_files app/ios)
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

# The Swift client of the API (ADR-0003 §2): swift-openapi-generator, pinned by the tool package APIGenerator, writes
# it from my's document into the committed target GeneratedAPI of the API package.
API_DOCUMENT="$ROOT/shared/api/openapi.yaml"
IOS_API="$IOS/Packages/API"
IOS_API_GENERATED="$IOS_API/Sources/GeneratedAPI"
IOS_API_GENERATOR="$IOS/Tools/APIGenerator"

# Generates the Swift client into the directory $1 (relative to the root of the repository), which it creates; prints the generator's output when it fails.
ios_api_generate_into() {
  local out="$1" log
  [[ "$out" == /* ]] || out="$ROOT/$out"
  need swift "install Xcode $(tr -d '[:space:]' <"$ROOT/.xcode-version") (CONTRIBUTING.md)"
  mkdir -p "$out"
  log="$(mktemp)"
  if ! (cd "$IOS_API_GENERATOR" && swift run -c release swift-openapi-generator generate \
    --config "$IOS_API/openapi-generator-config.yaml" --output-directory "$out" "$API_DOCUMENT") >"$log" 2>&1; then
    grep -E 'error' "$log" >&2 || tail -n 30 "$log" >&2
    rm -f "$log"
    fail "swift-openapi-generator could not generate the Swift client from shared/api/openapi.yaml: fix what it says above in my, then import the document again (task api:import)."
  fi
  rm -f "$log"
}

# Writes the Swift client into the API package, replacing what is there, or into the directory $1 when given.
check_ios_api_generate() {
  local tmp
  if [[ -n "${1:-}" ]]; then
    ios_api_generate_into "$1"
    return
  fi
  tmp="$(mktemp -d)"
  trap 'rm -rf "$tmp"' RETURN
  ios_api_generate_into "$tmp"
  find "$IOS_API_GENERATED" -maxdepth 1 -name '*.swift' -delete 2>/dev/null || true
  mkdir -p "$IOS_API_GENERATED"
  cp "$tmp"/*.swift "$IOS_API_GENERATED/"
}

# The committed Swift client is what shared/api/openapi.yaml generates, file for file.
check_ios_api() {
  local tmp differences
  tmp="$(mktemp -d)"
  trap 'rm -rf "$tmp"' RETURN
  ios_api_generate_into "$tmp"
  differences="$(diff -rq "$tmp" "$IOS_API_GENERATED" 2>&1 | sed -e "s#$tmp#(generated)#g" -e "s#$ROOT/##g" || true)"
  if [[ -n "$differences" ]]; then
    printf '%s\n' "$differences" >&2
    fail "the Swift client in ${IOS_API_GENERATED#"$ROOT"/} is not what shared/api/openapi.yaml generates: run task api:generate and commit what it changes, never edit the generated files or the document by hand (ADR-0003)."
  fi
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

# The tests of the iOS app (ADR-0006 §1), on one simulator: the reference images of the screenshot tests are shot on
# it, so another device type or OS renders them differently.
IOS_TEST_DEVICE="mycroblog-check-phone|iPhone 17"
IOS_TEST_RESULTS="$IOS_BUILD/test"
IOS_SNAPSHOTS="$IOS/Packages/Features/Tests/FeaturesTests/__Snapshots__"
IOS_TEST_UDID=""

# Takes the device lock and boots the test simulator; it is shut down and the lock released when the check exits.
ios_test_boot() {
  need jq "install jq with brew install jq"
  local name="${IOS_TEST_DEVICE%%|*}" type="${IOS_TEST_DEVICE#*|}"
  take_device_lock
  trap ios_launch_cleanup EXIT
  IOS_TEST_UDID="$(ios_sim_device "$name" "$type")"
  IOS_BOOTED="$IOS_TEST_UDID"
  xcrun simctl bootstatus "$IOS_TEST_UDID" -b >/dev/null || fail "the simulator $name did not boot: open Simulator and boot $name to see why."
}

# Runs `xcodebuild test` for the layer $1 on the booted test simulator, with the rest as its arguments, into the result
# bundle $IOS_TEST_RESULTS/$1.xcresult; prints how long it took, and the failed tests and their issues when it fails.
# Variables named TEST_RUNNER_<NAME> in its environment reach the tests as <NAME>.
ios_test_layer() {
  local layer="$1" log result start status=0
  shift
  mkdir -p "$IOS_TEST_RESULTS"
  log="$IOS_TEST_RESULTS/$layer.log"
  result="$IOS_TEST_RESULTS/$layer.xcresult"
  rm -rf "$result"
  start=$SECONDS
  # One simulator, the booted one: no clones for parallel testing.
  xcodebuild test -destination "id=$IOS_TEST_UDID" -parallel-testing-enabled NO -disable-concurrent-destination-testing \
    -resultBundlePath "$result" "$@" >"$log" 2>&1 || status=$?
  if (( status != 0 )); then
    # The failed tests with their issues: an issue of Swift Testing goes on in the lines that start with ↳ or spaces.
    awk '/^✘|error:|Test Case .* failed|BUILD FAILED/ { print; more = 1; next }
      more && /^(↳|  )/ { print; next } { more = 0 }' "$log" >&2
    grep -qE '^✘|error:|Test Case .* failed|BUILD FAILED' "$log" || tail -n 30 "$log" >&2
    printf 'ios: the %s tests failed in %s s; the log is %s and the result bundle %s (open it in Xcode).\n' \
      "$layer" "$(( SECONDS - start ))" "${log#"$ROOT"/}" "${result#"$ROOT"/}" >&2
    return "$status"
  fi
  printf 'ios: the %s tests passed in %s s.\n' "$layer" "$(( SECONDS - start ))"
}

# The unit tests: the API package's tests of the generated types against shared/fixtures.
ios_test_unit() {
  (cd "$IOS/Packages/API" && ios_test_layer unit -scheme API -derivedDataPath "$IOS_BUILD/packages/API" -only-testing:APITests) ||
    fail "the unit tests failed: fix the code, or the fixture under shared/fixtures when what it pins has changed in my (above)."
}

# The screenshot tests of the Features package. The record mode $1 is one of swift-snapshot-testing's: missing (a
# missing reference is recorded and fails), all (every reference is recorded again), failed or never.
ios_test_screenshot() {
  local record="${1:-missing}" artifacts="$IOS_TEST_RESULTS/screenshots" status=0 image
  rm -rf "$artifacts"
  mkdir -p "$artifacts"
  (cd "$IOS/Packages/Features" && TEST_RUNNER_SNAPSHOT_ARTIFACTS="$artifacts" TEST_RUNNER_SNAPSHOT_TESTING_RECORD="$record" \
    ios_test_layer screenshot -scheme Features -derivedDataPath "$IOS_BUILD/packages/Features") || status=$?
  printf 'ios: the reference images:\n'
  find "$IOS_SNAPSHOTS" -name '*.png' 2>/dev/null | sort | sed 's/^/  /'
  (( status == 0 )) && return 0
  # Recording fails each test it records; it succeeded when every issue is a recording.
  if [[ "$record" == all ]]; then
    local issues recorded
    issues="$(grep -cE '^✘ Test .* recorded an issue' "$IOS_TEST_RESULTS/screenshot.log" || true)"
    recorded="$(grep -cE '^↳ (Record mode is on|No reference was found)' "$IOS_TEST_RESULTS/screenshot.log" || true)"
    (( issues > 0 && issues == recorded )) && return 0
    fail "the screenshot tests failed for another reason than recording (above): fix it, then record again."
  fi
  # The new image is in $artifacts; the difference is only an attachment of the result bundle.
  xcrun xcresulttool export attachments --path "$IOS_TEST_RESULTS/screenshot.xcresult" \
    --output-path "$artifacts/attachments" >/dev/null 2>&1 || true
  printf 'ios: the new images and their differences, to open next to the reference:\n' >&2
  while IFS= read -r image; do printf '  %s\n' "$image" >&2; done < <(
    find "$artifacts" -path "$artifacts/attachments" -prune -o -name '*.png' -print | sort
    [[ -f "$artifacts/attachments/manifest.json" ]] &&
      jq -r --arg dir "$artifacts/attachments" '.[] | .testIdentifier as $test | .attachments[]
        | select(.suggestedHumanReadableName | startswith("difference"))
        | "\($dir)/\(.exportedFileName) (the difference of \($test))"' "$artifacts/attachments/manifest.json"
  )
  fail "the screenshot tests failed: compare the images above; if the new rendering is right, record it with task ios:record-screenshots and commit the reference."
}

# The integration tests: the API package's client against the local my of scripts/my.sh, which the task starts.
ios_test_integration() {
  local url
  url="$("$ROOT/scripts/my.sh" url)" || fail "no local my answers: start it with task my:up (task ios:test does)."
  (cd "$IOS/Packages/API" && TEST_RUNNER_MY_URL="$url" \
    ios_test_layer integration -scheme API -derivedDataPath "$IOS_BUILD/packages/API" -only-testing:APIIntegrationTests) ||
    fail "the integration tests against my at $url failed: read the issues above; scripts/my.sh says where my's log is (.tmp/my.log)."
}

# The unit, screenshot and integration tests, one simulator under the device lock.
check_ios_test() {
  ios_test_boot
  ios_test_unit
  ios_test_screenshot
  ios_test_integration
}

# Records every reference image of the screenshot tests again; commit what changes.
check_ios_record_screenshots() {
  ios_test_boot
  ios_test_screenshot all
  printf 'ios: recorded the reference images above: look at each, then commit them.\n'
}

# The UI-flow tests: XCUITest drives the app, built for testing, on the test simulator.
check_ios_test_flow() {
  [[ -d "$IOS/Mycroblog.xcodeproj" ]] || check_ios_generate
  ios_test_boot
  ios_test_layer flow -project "$IOS/Mycroblog.xcodeproj" -scheme Mycroblog -derivedDataPath "$IOS_BUILD/app" ||
    fail "the UI-flow tests failed: the result bundle above holds a screen recording and the screens of each step."
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
    # XcodeGen builds the iOS project, which exists only on a Mac.
    [[ "$tool" == xcodegen && "$(uname -s)" != Darwin ]] && continue
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
# The emulator image of this machine's architecture: arm64 on Apple silicon, x86_64 on a Linux CI runner.
case "$(uname -m)" in
  arm64 | aarch64) ANDROID_ABI="arm64-v8a" ;;
  *) ANDROID_ABI="x86_64" ;;
esac
ANDROID_IMAGE="system-images;android-37.0;google_apis_playstore;$ANDROID_ABI"
# The emulator's renderer: the Mac's GPU, or ANDROID_EMULATOR_GPU=swiftshader_indirect where there is none (CI).
ANDROID_EMULATOR_GPU="${ANDROID_EMULATOR_GPU:-host}"
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

# Downloads the jar $1 from the URL $2 to $4 once, and refuses it when its SHA-256 is not $3.
need_pinned_jar() {
  local name="$1" url="$2" sha256="$3" jar="$4"
  need java "install the JDK of .tool-versions with asdf install java"
  need curl "install curl"
  if [[ ! -f "$jar" ]]; then
    mkdir -p "$(dirname "$jar")"
    curl -fsSL -o "$jar.part" "$url" || fail "could not download $name from $url: check the network, then run again."
    mv "$jar.part" "$jar"
  fi
  [[ "$(shasum -a 256 "$jar" | cut -d ' ' -f 1)" == "$sha256" ]] || {
    rm -f "$jar"
    fail "the $name jar from $url does not have the pinned SHA-256 and was removed: run again; if it fails again, do not change the pinned SHA-256 without checking the jar."
  }
}

# ktlint, pinned by version and by the SHA-256 of its jar on Maven Central, formats and checks the hand-written
# Kotlin of app/and in the style of .editorconfig; the generated client and the build output are left alone.
KTLINT_VERSION="1.8.0"
KTLINT_SHA256="369ad2b789f95a011f807e1fcb690ccef80bd7cd014fd139e73ae82dcc0baeab"
KTLINT_URL="https://repo1.maven.org/maven2/com/pinterest/ktlint/ktlint-cli/$KTLINT_VERSION/ktlint-cli-$KTLINT_VERSION-all.jar"
KTLINT_JAR="$ANDROID_APP/build/tools/ktlint-$KTLINT_VERSION.jar"
KTLINT_FILES=('**/*.kt' '**/*.kts' '!**/src/generated/**' '!**/build/**' '!**/.gradle/**')

android_ktlint() {
  need_pinned_jar ktlint "$KTLINT_URL" "$KTLINT_SHA256" "$KTLINT_JAR"
  (cd "$ANDROID_APP" && java -jar "$KTLINT_JAR" --relative --log-level=error "$@" "${KTLINT_FILES[@]}")
}

# Fails on Kotlin that is not formatted as ktlint formats it, and on what Android Lint finds in any module.
check_android_lint() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}"
  android_ktlint ||
    fail 'the Kotlin above (paths under app/and) is not formatted as .editorconfig says: run task android:format, fix by hand what it cannot (each line names its ktlint rule), then check again.'
  android_gradle lint --continue ||
    fail 'Android Lint found the issues above: fix each as its message says (the report of each module is under app/and/<module>/build/reports), then check again. Suppress one only with @Suppress("<issue id>") and a comment that says why.'
}

check_android_format() {
  android_ktlint --format || fail 'ktlint could not fix the lines above: fix each by hand as its rule says.'
  printf 'android: formatted the Kotlin of app/and with ktlint %s\n' "$KTLINT_VERSION"
}

# Layout by window size class (ADR-0010 §4): hand-written Kotlin must not lay out by orientation, screen size or the
# display. Each entry is "<extended regex>|<what to use instead>". Android Lint also holds the orientation lock
# (LockedOrientationActivity, SourceLockedOrientationActivity) and the screen size (ConfigurationScreenWidthHeight),
# without naming the ADR.
ANDROID_LAYOUT_FORBIDDEN=(
  '\.orientation\b|ORIENTATION_(PORTRAIT|LANDSCAPE|SQUARE|UNDEFINED)|orientation is not a layout signal: switch on currentWindowAdaptiveInfoV2().windowSizeClass (height compact for a landscape phone)'
  '\b(screenWidthDp|screenHeightDp|smallestScreenWidthDp)\b|the screen size is not the window size: switch on currentWindowAdaptiveInfoV2().windowSizeClass, and size a component from LocalWindowInfo.current.containerSize'
  '\bscreenLayout\b|SCREENLAYOUT_|the device type is not a layout signal: switch on currentWindowAdaptiveInfoV2().windowSizeClass'
  '\b(widthPixels|heightPixels|getRealMetrics|getRealSize|defaultDisplay|getDefaultDisplay)\b|the display is not the window: switch on currentWindowAdaptiveInfoV2().windowSizeClass, and size a component from LocalWindowInfo.current.containerSize'
  '\b(requestedOrientation|setRequestedOrientation)\b|SCREEN_ORIENTATION_|the app works in every orientation and size (API 36 ignores the lock from 600dp): lay out by currentWindowAdaptiveInfoV2().windowSizeClass instead of locking the orientation'
)

check_android_layout() {
  local failed=0 entry pattern advice hits f
  local files=()
  while IFS= read -r -d '' f; do files+=("$f"); done < <(git -C "$ROOT" ls-files -z --cached --others --exclude-standard -- 'app/and/*.kt' ':!:app/and/**/src/generated/**')
  for entry in "${ANDROID_LAYOUT_FORBIDDEN[@]}"; do
    pattern="${entry%|*}"
    advice="${entry##*|}"
    hits="$(grep -snE -- "$pattern" "${files[@]}" || true)"
    if [[ -n "$hits" ]]; then
      printf '%s\n' "$hits" >&2
      printf 'check: %s (ADR-0010 §4).\n' "$advice" >&2
      failed=1
    fi
  done
  (( failed == 0 )) || fail 'the Kotlin above lays out by device, orientation or screen rather than by window size class (ADR-0010 §4): change it as each line under it says.'
}

# The Kotlin client of the client API (ADR-0003 §2): Fabrikt, pinned by version and by the SHA-256 of its jar on
# Maven Central, generates it from shared/api/openapi.yaml into the committed sources of :core:api.
FABRIKT_VERSION="29.2.0"
FABRIKT_SHA256="3166eac8c37ebdae70cfc3c52a953c25ab20f03494675a56f1c8b3c9de603458"
FABRIKT_URL="https://repo1.maven.org/maven2/io/fabrikt/fabrikt/$FABRIKT_VERSION/fabrikt-$FABRIKT_VERSION.jar"
FABRIKT_JAR="$ANDROID_APP/build/tools/fabrikt-$FABRIKT_VERSION.jar"
ANDROID_API_SPEC="$ROOT/shared/api/openapi.yaml"
ANDROID_API_SOURCES="$ANDROID_APP/core/api/src/generated/kotlin"
ANDROID_API_PACKAGE="dev.mycroblog.core.api"
FABRIKT_OPTIONS=(
  --targets HTTP_MODELS --targets CLIENT
  --http-client-target KTOR --http-client-opts GROUP_BY_TAG
  --serialization-library KOTLINX_SERIALIZATION --instant-library KOTLIN_TIME_INSTANT
  --http-model-opts FAULT_TOLERANT_ENUMS --validation-library NO_VALIDATION
)

# Downloads the Fabrikt jar once, and refuses it when its SHA-256 is not the pinned one.
need_fabrikt() {
  need_pinned_jar Fabrikt "$FABRIKT_URL" "$FABRIKT_SHA256" "$FABRIKT_JAR"
}

# Writes the Kotlin client that shared/api/openapi.yaml generates into the directory $1, replacing what it held.
android_api_generate() {
  local dest="$1" work
  need_fabrikt
  work="$(mktemp -d)"
  # The English locale keeps Fabrikt's messages, and anything it derives from the locale, the same on every machine.
  java -Duser.language=en -Duser.country=US -Dfile.encoding=UTF-8 -jar "$FABRIKT_JAR" \
    --api-file "$ANDROID_API_SPEC" --base-package "$ANDROID_API_PACKAGE" --output-directory "$work" \
    "${FABRIKT_OPTIONS[@]}" >"$work/log" 2>&1 || {
    cat "$work/log" >&2
    rm -rf "$work"
    fail "Fabrikt could not generate the Kotlin client from shared/api/openapi.yaml: fix what it says above (the document comes from my; never edit it here)."
  }
  rm -rf "$dest"
  mkdir -p "$(dirname "$dest")"
  mv "$work/src/main/kotlin" "$dest"
  rm -rf "$work"
}

# Writes the client into the committed sources, or into the directory $1, which must be new or empty.
check_android_api_generate() {
  local out="${1:-}"
  if [[ -n "$out" ]]; then
    [[ ! -e "$out" || ( -d "$out" && -z "$(ls -A "$out")" ) ]] ||
      fail "OUT=$out is not a new or empty directory: name one that is, so that nothing in it is replaced."
    android_api_generate "$out"
  else
    android_api_generate "$ANDROID_API_SOURCES"
  fi
  printf 'android: generated the Kotlin client from shared/api/openapi.yaml into %s\n' "${out:-${ANDROID_API_SOURCES#"$ROOT"/}}"
}

# Regenerates the Kotlin client into a temporary directory and fails when it differs from the committed one.
ANDROID_API_CHECK_DIR=""
check_android_api_check() {
  local tmp diffs
  tmp="$(mktemp -d)"
  ANDROID_API_CHECK_DIR="$tmp"
  trap 'rm -rf "$ANDROID_API_CHECK_DIR"' EXIT
  android_api_generate "$tmp/kotlin"
  diffs="$(diff -rq "$ANDROID_API_SOURCES" "$tmp/kotlin" 2>&1 | sed -e "s|$ANDROID_API_SOURCES|${ANDROID_API_SOURCES#"$ROOT"/}|g" -e "s|$tmp/kotlin|(generated)|g" || true)"
  if [[ -n "$diffs" ]]; then
    printf '%s\n' "$diffs" | head -n 20 >&2
    (( $(wc -l <<<"$diffs") > 20 )) && printf '... and %s more\n' "$(( $(wc -l <<<"$diffs") - 20 ))" >&2
    fail "the Kotlin client in ${ANDROID_API_SOURCES#"$ROOT"/} is not what shared/api/openapi.yaml generates: run task api:generate and commit the result (never edit the generated code by hand)."
  fi
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

# Boots the AVD drive-<kind> (the one scripts/drive uses) as emulator-$ANDROID_CONSOLE_PORT, creating it as a
# <profile> when it is missing, and waits until it has booted. release_device stops it.
boot_android() {
  local profile="$2" avd="drive-$1" serial="emulator-$ANDROID_CONSOLE_PORT" adb deadline log
  adb="$ANDROID_SDK/platform-tools/adb"
  if ! "$ANDROID_SDK/emulator/emulator" -list-avds | grep -qx "$avd"; then
    echo no | "$ANDROID_SDK/cmdline-tools/latest/bin/avdmanager" create avd -n "$avd" -k "$ANDROID_IMAGE" -d "$profile" >/dev/null ||
      fail "could not create the AVD $avd from $ANDROID_IMAGE: create it in Android Studio's Device Manager as a $profile, then check again."
  fi
  log="$(mktemp)"
  "$ANDROID_SDK/emulator/emulator" -avd "$avd" -port "$ANDROID_CONSOLE_PORT" -no-window -no-snapshot-save -no-boot-anim \
    -no-audio -gpu "$ANDROID_EMULATOR_GPU" >"$log" 2>&1 &
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
}

# Boots the AVD drive-<kind>, installs and launches the app, confirms that it runs with its activity in front, and
# stops the emulator.
launch_android_on() {
  local profile="$2" avd="drive-$1" serial="emulator-$ANDROID_CONSOLE_PORT" adb
  adb="$ANDROID_SDK/platform-tools/adb"
  boot_android "$1" "$profile"
  "$adb" -s "$serial" install -r "$ANDROID_APP/app/build/outputs/apk/debug/app-debug.apk" >/dev/null ||
    fail "could not install the app on $avd: run adb -s $serial install app/and/app/build/outputs/apk/debug/app-debug.apk to see why."
  # Right after sys.boot_completed the package manager can still refuse to start an activity: wait until it resolves
  # the app's, and try the start a few times before calling it a failure.
  local tries=0
  until "$adb" -s "$serial" shell cmd package resolve-activity --brief -n "$ANDROID_PACKAGE/.MainActivity" 2>/dev/null |
    grep -q "$ANDROID_PACKAGE/"; do
    (( ++tries < 30 )) || fail "Android on $avd does not find $ANDROID_PACKAGE/.MainActivity: is the APK installed?"
    sleep 2
  done
  tries=0
  until "$adb" -s "$serial" shell am start -W -n "$ANDROID_PACKAGE/.MainActivity" 2>/dev/null | grep -q 'Status: ok'; do
    (( ++tries < 3 )) || fail "the app did not start on $avd: adb -s $serial logcat -b crash says why."
    sleep 5
  done
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

# The test layers of the Android app that run on the JVM (ADR-0006 §1): the unit tests, the screenshot tests and the
# integration tests, the last against the local my at MY_URL (task android:test starts it with task my:up).
ANDROID_SCREENSHOT_MODULES=("core/designsystem")

# Prints the path of each image a reviewer opens: every reference, and each new image and comparison of a failure.
print_android_screenshots() {
  local module f
  for module in "${ANDROID_SCREENSHOT_MODULES[@]}"; do
    while IFS= read -r -d '' f; do
      printf 'android: screenshot %s\n' "${f#"$ROOT"/}"
    done < <(find "$ANDROID_APP/$module/src/test/screenshots" -name '*.png' -print0 2>/dev/null | sort -z)
    while IFS= read -r -d '' f; do
      printf 'android: screenshot differs, open %s\n' "${f#"$ROOT"/}" >&2
    done < <(find "$ANDROID_APP/$module/build/outputs/roborazzi" \( -name '*_actual.png' -o -name '*_compare.png' \) -print0 2>/dev/null | sort -z)
  done
}

check_android_test() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}"
  local url="${MY_URL:-}" module tasks=(:core:api:test :core:api:integrationTest)
  [[ -n "$url" ]] || url="$("$ROOT/scripts/my.sh" url)"
  for module in "${ANDROID_SCREENSHOT_MODULES[@]}"; do
    tasks+=(":${module//\//:}:verifyRoborazziDebug")
    # A comparison left by an earlier run must not be taken for one of this run.
    find "$ANDROID_APP/$module/build/outputs/roborazzi" \( -name '*_actual.png' -o -name '*_compare.png' \) -delete 2>/dev/null || true
  done
  if ! MY_URL="$url" android_gradle "${tasks[@]}" --continue; then
    print_android_screenshots
    fail "the Android tests failed: fix what each failed test says above. A screenshot that differs on purpose is recorded again with task android:record-screenshots; open each image named above before committing it."
  fi
  print_android_screenshots
}

# Records the reference images of the screenshot tests again, replacing the committed ones.
check_android_record_screenshots() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}"
  local module tasks=()
  for module in "${ANDROID_SCREENSHOT_MODULES[@]}"; do
    tasks+=(":${module//\//:}:recordRoborazziDebug")
  done
  android_gradle "${tasks[@]}" || fail 'recording the screenshots failed: fix what Gradle says above.'
  print_android_screenshots
}

# The UI-flow tests of the Android app (ADR-0006 §1), the instrumented tests of app/src/androidTest, on the phone
# emulator drive-phone under the device lock.
check_android_test_flow() {
  need_android_sdk "${ANDROID_SDK_PACKAGES[@]}" "$ANDROID_IMAGE"
  android_gradle :app:assembleDebug :app:assembleDebugAndroidTest ||
    fail 'the Android build failed: fix what Gradle says above (cd app/and && ./gradlew :app:assembleDebugAndroidTest), then check again.'
  trap release_device EXIT
  take_device_lock
  boot_android phone pixel_9
  ANDROID_SERIAL="emulator-$ANDROID_CONSOLE_PORT" android_gradle :app:connectedDebugAndroidTest ||
    fail "the Android UI-flow tests failed on drive-phone: fix what each failed test says above; the report is app/and/app/build/reports/androidTests/connected/debug/index.html."
  printf 'check: the UI-flow tests pass on drive-phone (pixel_9)\n'
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
  ios-lint) check_ios_lint ;;
  ios-format) check_ios_format ;;
  ios-layout) check_ios_layout ;;
  ios-dependencies) check_ios_dependencies ;;
  ios-packages) check_ios_packages ;;
  ios-build) check_ios_build ;;
  ios-launch) check_ios_launch ;;
  ios-test) check_ios_test ;;
  ios-record-screenshots) check_ios_record_screenshots ;;
  ios-test-flow) check_ios_test_flow ;;
  ios-api-generate) check_ios_api_generate "${2:-}" ;;
  ios-api-check) check_ios_api ;;
  android-build) check_android_build ;;
  android-lint) check_android_lint ;;
  android-format) check_android_format ;;
  android-layout) check_android_layout ;;
  android-launch) check_android_launch ;;
  android-test) check_android_test ;;
  android-record-screenshots) check_android_record_screenshots ;;
  android-test-flow) check_android_test_flow ;;
  android-api-generate) check_android_api_generate "${2:-}" ;;
  android-api-check) check_android_api_check ;;
  *) fail "unknown check ${1:-(none)}: run the checks with task check (task --list shows them)." ;;
esac
