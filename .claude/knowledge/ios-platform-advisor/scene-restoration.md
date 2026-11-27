# Scene restoration and tab accessibility

- SceneStorage: restored value is per Scene; system gives no guarantee when or how often it persists; destroyed when the scene is explicitly destroyed (iPadOS: switcher snapshot discarded) -> a new scene session starts empty [https://developer.apple.com/documentation/swiftui/scenestorage.md, checked 2026-11-27]
- Apple engineer: after termination the system restores iPad scenes via UISceneSession.stateRestorationActivity, and UISceneActivationConditions can stop a scene from being restored (Apple forums) [https://developer.apple.com/forums/thread/702040, checked 2026-11-27]
- Unanswered forum report: iPadOS restores the number of windows, not their kind (undocumented) [https://developer.apple.com/forums/thread/728193, checked 2026-11-27]
- Scene.restorationBehavior(_:) iOS 18, .disabled opts a scene out of restore; Scene.defaultLaunchBehavior has no iOS or iPadOS availability (macOS 15, visionOS 26) -> neither carries state into a new scene [https://developer.apple.com/documentation/swiftui/scene/restorationbehavior(_:), https://developer.apple.com/documentation/swiftui/scene/defaultlaunchbehavior(_:), checked 2026-11-27]
- Tab has init(value:content:label:) taking a Label view; Apple's pages say nothing on how a tab item's spoken name is derived [https://developer.apple.com/documentation/swiftui/tab.md, checked 2026-11-27]
