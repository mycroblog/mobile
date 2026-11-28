# kotlin-test-developer: decisions

- An Android library has no `JvmTestSuite`: its integration tests sit in `src/integrationTest/kotlin`, added to the
  `test` source set, excluded from the unit-test tasks and run by an `integrationTest` task on the debug unit-test
  classpath, as `:core:data` does (#11, 2026-11-27).
- In a Gradle build script, look up an AGP task (`testDebugUnitTest`) inside the configuration block of the task that
  needs it, not at the top level: AGP registers it after the script runs (#11, 2026-11-27).
- A test relaunches the app's preferences with `ReaderPreferences` of the `:core:data` tests: `cancelAndJoin` of the
  scope its DataStore was created with, then a new DataStore on the same file, since one file takes one active
  DataStore (#42, 2026-11-28).
- There is no screenshot layer (ADR-0006 §1, removed in 6b16a06); a done-when asking for screenshot tests goes back to
  the caller as a conflict, not as a new layer (#41, #42, 2026-11-28).
- A UI flow that reaches the local my at 10.0.2.2 grants ACCESS_LOCAL_NETWORK with
  `uiAutomation.grantRuntimePermission` first: the system dialog is outside Compose; on a phone, Local opens from
  "More" (#42, 2026-11-28).
