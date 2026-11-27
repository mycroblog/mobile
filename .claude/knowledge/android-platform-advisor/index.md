# android-platform-advisor: index

How Android and Jetpack behave for this app. Read this file, then the files of the question.

| File | Holds |
| --- | --- |
| `architecture-and-navigation.md` | Google's architecture recommendations, Navigation 3, adaptive layouts, predictive back, state saving |
| `entry-points.md` | Shares, Direct Share, App Links, notifications and their actions |
| `background-and-push.md` | WorkManager, foreground services, user-initiated transfers, FCM |
| `auth-app-links.md` | assetlinks.json, App Links verification and testing, Auth Tab and Custom Tabs for OAuth sign-in |
| `data-and-di.md` | Room, Paging, Hilt and other DI |
| `ui-automation.md` | UiAutomation windows, Compose accessibility node info, shell commands from an instrumented runner |

## Rules for a line

- `- <what the OS or library does> -> <what it means for the app> [<source URL>, checked <YYYY-MM-DD>]`. No paragraphs.
- The source is developer.android.com, a Jetpack release note, m3.material.io, firebase.google.com or the Firebase blog, dagger.dev, the
  Android Developers Blog, or AndroidX source. Secondary sources are not lines.
- A line names the Android, target SDK or library version it holds for when it matters.
- Every file is at most 4096 bytes (`scripts/kit check`). Near it: merge lines, or split the file by topic and add
  it to the table. Add, merge, or delete lines; never rewrite a file.
