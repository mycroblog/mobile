# android-platform-advisor: index

How Android and Jetpack behave for this app. Read this file, then the files of the question.

| File | Holds |
| --- | --- |
| `architecture-and-navigation.md` | Google's architecture recommendations, Navigation 3, adaptive layouts, predictive back, state saving |
| `entry-points.md` | Shares, Direct Share, App Links, notifications and their actions |
| `background-and-push.md` | WorkManager, foreground services, user-initiated transfers, FCM |
| `auth-app-links.md` | assetlinks.json, App Links verification and testing, Auth Tab and Custom Tabs for OAuth sign-in |
| `data-and-di.md` | Room, Paging, Hilt and other DI |
| `build-and-versions.md` | AGP, Gradle, Kotlin, KSP, Hilt and Jetpack stable versions, compileSdk DSL, built-in Kotlin |
| `adaptive-and-system-ui.md` | NavigationSuiteScaffold, predictive back, edge-to-edge |
| `nav-rail-and-bar.md` | NavigationRail layout, NavigationBarItem labels and icons |
| `test-layers.md` | Robolectric SDK support, Roborazzi setup and output dirs, dynamic color under Robolectric |
| `test-instrumented-jvm.md` | androidTest with Compose and Hilt, androidx.test versions, Ktor JVM engine, Gradle Test inputs |
| `lint-and-format.md` | ktlint CLI and styles, ktlint-gradle with built-in Kotlin, Android Lint DSL, lint on JVM modules |
| `layout-by-window.md` | Window size classes, lint checks on orientation and screen size, containerSize |
| `network-and-text-input.md` | Ktor engines and timeout exceptions, network security config and cleartext, INTERNET, state-based TextField, UI state saving |
| `local-network.md` | Android 17 ACCESS_LOCAL_NETWORK: scope, addresses, failure mode |
| `ui-automation.md` | UiAutomation windows, Compose accessibility node info, shell commands from an instrumented runner |
| `text-and-media.md` | HTML to AnnotatedString, links and TalkBack, inline emoji, text premeasure |
| `lists-and-prefs.md` | LazyColumn keys and position saving, PullToRefreshBox, DataStore |
| `ime-and-focus.md` | windowSoftInputMode and IME insets, extract UI, hiding the keyboard |
| `room3-and-sqlite.md` | Room 3 plugin, builders, SQLite drivers, bundled on JVM, write transactions |
| `images-links-motion.md` | Coil 3 artifacts and GIF, Custom Tabs and package visibility, Remove animations |

## Rules for a line

- `- <what the OS or library does> -> <what it means for the app> [<source URL>, checked <YYYY-MM-DD>]`. No paragraphs.
- The source is developer.android.com, a Jetpack release note, m3.material.io, firebase.google.com or the Firebase blog, dagger.dev, the
  Android Developers Blog, or AndroidX source. Secondary sources are not lines.
- A line names the Android, target SDK or library version it holds for when it matters.
- Every file is at most 4096 bytes (`scripts/kit check`). Near it: merge lines, or split the file by topic and add
  it to the table. Add, merge, or delete lines; never rewrite a file.
