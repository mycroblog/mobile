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
`/private/tmp/mycroblog-device.lock` exists, which another launch holds while its device runs. `task --list` lists the
checks of each area, such as `task docs:check`. `scripts/kit check` runs the same checks.
