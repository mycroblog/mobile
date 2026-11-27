# kotlin-test-developer: decisions

- Shoot a screen with an indeterminate progress indicator through `createComposeRule()` with
  `mainClock.autoAdvance = false`, then `onRoot().captureRoboImage()`: `captureRoboImage { }` redraws it forever and the
  test hangs - the animation never idles under Robolectric (#11, 2026-11-27).
- An Android library has no `JvmTestSuite`: its integration tests sit in `src/integrationTest/kotlin`, added to the
  `test` source set, excluded from the unit-test tasks and run by an `integrationTest` task on the debug unit-test
  classpath, as `:core:data` does (#11, 2026-11-27).
- In a Gradle build script, look up an AGP task (`testDebugUnitTest`) inside the configuration block of the task that
  needs it, not at the top level: AGP registers it after the script runs (#11, 2026-11-27).
