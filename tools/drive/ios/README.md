# The iOS runner of the app-driving tool

`DriveRunner` is a UI test that never ends: `RunnerTests/testServe` listens on TCP `127.0.0.1:$DRIVE_PORT`
(22087 when unset), reads one JSON request per line and writes one JSON reply per line, until it gets
`{"op":"quit"}`. It drives the app whose bundle id each request names, on a simulator only. `DriveHost` is the empty
app that XCUITest needs to start the runner. The client is `scripts/drive`.

## Build

```sh
xcodebuild build-for-testing \
  -project tools/drive/ios/DriveRunner.xcodeproj -scheme DriveRunner \
  -destination 'generic/platform=iOS Simulator' \
  -derivedDataPath tools/drive/ios/build
```

The build needs no signing. It writes
`tools/drive/ios/build/Build/Products/DriveRunner_iphonesimulator<SDK version>-arm64-x86_64.xctestrun`.

## Start

```sh
TEST_RUNNER_DRIVE_PORT=22087 xcodebuild test-without-building \
  -xctestrun tools/drive/ios/build/Build/Products/DriveRunner_iphonesimulator*.xctestrun \
  -destination id=<simulator UDID> \
  -only-testing:DriveRunner/RunnerTests/testServe
```

xcodebuild passes `TEST_RUNNER_DRIVE_PORT` to the test as `DRIVE_PORT`. The runner logs
`DRIVE_RUNNER ready on 127.0.0.1:<port>` once it listens. One simulator takes one runner at a time.
