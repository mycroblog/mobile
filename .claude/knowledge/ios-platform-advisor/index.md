# ios-platform-advisor: index

How iOS, iPadOS and the iPhone Duo behave for this app. Read this file, then the files of the question.

| File | Holds |
| --- | --- |
| `duo-and-layout.md` | iPhone Duo, size classes, windows and scenes, bars, navigation containers |
| `extensions.md` | Notification Service and Share Extensions, Keychain sharing, App Intents |
| `background-and-data.md` | Background execution, background URLSession, GRDB and SwiftData across processes |
| `cross-process-locks.md` | 0xDEAD10CC, file locks and NSFileCoordinator across app and extensions, Keychain atomicity and sharing |
| `concurrency-and-build.md` | Swift concurrency defaults, SwiftUI isolation, SwiftPM and Xcode pitfalls |

## Rules for a line

- `- <what the OS does> -> <what it means for the app> [<source URL>, checked <YYYY-MM-DD>]`. No paragraphs.
- The source is Apple's documentation, HIG, a WWDC or Tech Talk session, release notes, or apple.com. A forum answer
  by an Apple engineer is marked `(Apple forums)`. Widely reported but undocumented facts end with `(undocumented)`.
- A line names the OS or SDK version it holds for when it matters. Recheck a line before relying on it for a newer
  version.
- Every file is at most 4096 bytes (`scripts/kit check`). Near it: merge lines, or split the file by topic and add
  it to the table. Add, merge, or delete lines; never rewrite a file.
