# Android runner of the app-driving tool

The Android side of the app-driving tool of [ADR-0011](../../../docs/adr/0011-agent-harness.md) §3: a UI Automator
test, `dev.mycroblog.drive.runner.Server#serve`, that stays running and serves the runner protocol (one JSON request
per line, one JSON reply per line) on the device's 127.0.0.1. The client is `scripts/drive`.

The module `runner` is an app whose instrumentation targets itself, so the runner lives in its own process and keeps
running when the app under test is killed. Only one UiAutomation client can run on a device at a time.

## Build

With JDK 21 and `ANDROID_HOME` set to the Android SDK (platform `android-37.2`):

```sh
./gradlew :runner:assembleDebug :runner:assembleDebugAndroidTest
```

The APKs:

- `runner/build/outputs/apk/debug/runner-debug.apk`
- `runner/build/outputs/apk/androidTest/debug/runner-debug-androidTest.apk`

## Install and start

```sh
adb install -r -t runner/build/outputs/apk/debug/runner-debug.apk
adb install -r -t runner/build/outputs/apk/androidTest/debug/runner-debug-androidTest.apk
adb forward tcp:9008 tcp:9008
adb shell am instrument -w -e class dev.mycroblog.drive.runner.Server#serve -e port 9008 \
  dev.mycroblog.drive.runner.test/androidx.test.runner.AndroidJUnitRunner
```

`am instrument` returns when the runner ends: on `{"op":"quit"}`, or when it is killed. The port is the
instrumentation argument `port` (default 9008).
