# Architecture and navigation

- Strongly recommended: data layer with repositories, UDF, screen-level ViewModels, collectAsStateWithLifecycle, single activity, Compose; do not send one-off events from ViewModel to UI; "Use Navigation 3" [https://developer.android.com/topic/architecture/recommendations, checked 2026-11-27]
- Navigation 3: 1.0.0 stable 2025-11-19; 1.2.0 (2026-09-23) adds deep-link matching, synthetic back stacks and NavigationBackHandler [https://developer.android.com/jetpack/androidx/releases/navigation3, checked 2026-11-27]
- Navigation 2.x is in maintenance mode, critical fixes only [https://developer.android.com/jetpack/androidx/releases/navigation, checked 2026-11-27]
- "You own your back stack": rememberNavBackStack survives config change and process death when keys are @Serializable NavKey [https://developer.android.com/guide/navigation/navigation-3/save-state, checked 2026-11-27]
- Deep links: the app matches the intent in onCreate/onNewIntent and builds the back stack itself [https://developer.android.com/guide/navigation/navigation-3/deep-links, checked 2026-11-27]
- Scenes render several entries by window size; Material 3 adaptive-navigation3 provides ListDetailSceneStrategy [https://developer.android.com/guide/navigation/navigation-3/custom-layouts, checked 2026-11-27]
- Width classes compact, medium, expanded, large, extra-large, plus height classes -> map each [https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes, checked 2026-11-27]
- Targeting API 36: predictive back on by default, onBackPressed not called; edge-to-edge opt-out removed; on sw600dp+ orientation and resizability limits ignored [https://developer.android.com/about/versions/16/behavior-changes-16, checked 2026-11-27]
- ViewModel survives config change, not process death; saved state survives both and should hold only small data such as IDs [https://developer.android.com/topic/libraries/architecture/saving-states, checked 2026-11-27]
