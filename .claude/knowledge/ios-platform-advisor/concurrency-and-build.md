# Concurrency and build

- View is MainActor; some closures (visualEffect, onGeometryChange) may run off the main actor [https://developer.apple.com/videos/play/wwdc2025/266/, checked 2026-11-27]
- With Xcode 27, @State initializes an Observable class once per view lifetime, back-deployed; earlier builds may run the initializer repeatedly [https://developer.apple.com/videos/play/wwdc2026/269/, checked 2026-11-27]
- iOS 27 SDK requires the UIScene life cycle [https://developer.apple.com/videos/play/wwdc2026/278/, checked 2026-11-27]
- Xcode 26 explicitly built modules: reports of "Unable to find module dependency" across local packages (Apple forums) [https://developer.apple.com/forums/thread/806875, checked 2026-11-27]
- A dependency resolving a swift-syntax version other than Xcode's prebuilt one rebuilds swift-syntax from source (minutes per build) -> check Package.resolved [https://developer.apple.com/forums/thread/836382, checked 2026-11-27]
