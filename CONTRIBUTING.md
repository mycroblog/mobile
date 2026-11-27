# Contributing

## Set up a Mac

The checks run on macOS on Apple silicon. Each step below is what `task check` asks for when it is missing.

1. Install Xcode at the version in [.xcode-version](.xcode-version), the latest released Xcode
   ([ADR-0009](docs/adr/0009-ios-ui-structure.md) §7), from the App Store or
   [developer.apple.com](https://developer.apple.com/download/applications/). Open it once to install its
   components, then select it:

   ```bash
   sudo xcode-select -s /Applications/Xcode.app
   ```

2. Install [Homebrew](https://brew.sh), then [asdf](https://asdf-vm.com), the GitHub CLI and jq:

   ```bash
   brew install asdf gh jq
   ```

   Put asdf's shims first on `PATH`, as [asdf's guide](https://asdf-vm.com/guide/getting-started.html) says for
   your shell, so that the versions of [.tool-versions](.tool-versions) win over other installs.

3. Install the tools of [.tool-versions](.tool-versions) from the repository's root:

   ```bash
   cut -d' ' -f1 .tool-versions | xargs -n1 asdf plugin add
   ```

   ```bash
   asdf install
   ```

   Among them is [XcodeGen](https://github.com/yonaskolb/XcodeGen), which generates the iOS app's Xcode project
   from `app/ios/project.yml`; the generated project is not committed. Run `task ios:generate` to make it, then open
   `app/ios/Mycroblog.xcodeproj`.

4. Install [Android Studio](https://developer.android.com/studio), which puts the Android SDK at
   `~/Library/Android/sdk`, and in its SDK Manager install "Android SDK Command-line Tools (latest)" from the SDK
   Tools tab. Set `ANDROID_HOME` to the SDK
   in your shell's profile (the checks also find it there when it is unset):

   ```bash
   export ANDROID_HOME="$HOME/Library/Android/sdk"
   ```

   Then install the SDK packages that the app is built with and the emulator image that the checks launch it on:

   ```bash
   "$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager" "platforms;android-37.2" "build-tools;37.0.0" \
     platform-tools emulator "system-images;android-37.0;google_apis_playstore;arm64-v8a"
   ```

   The checks create the emulators they need (`drive-phone` and `drive-tablet`) from that image.

5. Sign in to GitHub for `scripts/kit`:

   ```bash
   gh auth login
   ```

## Run the checks

```bash
task check
```

It runs every check, each area in parallel, and names each one that fails with what to do. When they pass, it
launches each app on its simulators or emulators, one device at a time: a launch waits while
`/tmp/mycroblog-device.lock` exists, which another launch holds while its device runs. `task --list` lists the
checks of each area, such as `task docs:check`. `scripts/kit check` runs the same checks.

## Run the tests

The tests have four layers ([ADR-0006](docs/adr/0006-verification-harness.md) §1). The expected data that both apps
must agree on is under [shared/fixtures](shared/fixtures/README.md); the unit tests of both read it from there.

| Layer | What it pins | iOS | Android |
| --- | --- | --- | --- |
| Unit | logic, and the shared fixtures | Swift Testing, `APITests`, `DomainTests` and `FeaturesUnitTests` under `app/ios/Packages/*/Tests` | JUnit on the JVM, `app/and/<module>/src/test` |
| Screenshot | how a screen renders, as a reference image | swift-snapshot-testing, `app/ios/Packages/Features/Tests/FeaturesTests` | Roborazzi on Robolectric, `app/and/<module>/src/test`, the modules of `ANDROID_SCREENSHOT_MODULES` in `scripts/check/run.sh` |
| Integration | the exchange with a local my | XCTest, `app/ios/Packages/API/Tests/APIIntegrationTests` | JUnit on the JVM, `app/and/core/{api,data}/src/integrationTest` |
| UI flow | a main flow through the running app | XCUITest, `app/ios/UITests` | Compose UI tests on `drive-phone`, `app/and/app/src/androidTest` |

`task ios:test` and `task android:test` run the first three layers, and `task check` runs both. The UI-flow layer is
slower, so it runs in a task of its own: `task check:flow` runs it for both apps (`task ios:test-flow`,
`task android:test-flow`). Whatever runs on a simulator or emulator takes the same device lock as the launches.

### A local my

The integration tests talk to my's demo server, with its fakes and seeded scenario (my ADR-0015), which
`task my:up` starts from a checkout of [mycroblog/my](https://github.com/mycroblog/my) at the commit of
`shared/api/my-commit` and `task my:down` stops. The tasks that need it start it, and later runs reuse it. It needs:

- my checked out next to this repository, as `../my`, or `MY` set to a checkout of it.
- Go and bun at the versions of my's `.tool-versions`: run `asdf install` in `.tmp/my`, the worktree of my that
  `task my:up` makes, when it says one is missing.

The first start builds my and takes about two minutes. `MY_PORT` changes its port from 8790.

### Screenshots

`task ios:test` and `task android:test` print the path of each reference image. When a screenshot test fails, they
also print the new image and the difference of each failing one: open them next to the reference. When the new
rendering is the right one, record the references again, open each image, and commit them with the change:

```bash
task ios:record-screenshots
```

```bash
task android:record-screenshots
```

- iOS: the references are shot on the simulator `mycroblog-check-phone` (iPhone 17) with the iOS of the Xcode in
  `.xcode-version`; another device or iOS renders them differently, so record them again when that Xcode changes. The
  tests run without a host app, where Liquid Glass, such as the tab bar's, does not render: shoot the screens below the
  tab container.
- Android: the references are under `app/and/<module>/src/test/screenshots`, and a failure's new image (`_actual`)
  and comparison (`_compare`) under `app/and/<module>/build/outputs/roborazzi`.
