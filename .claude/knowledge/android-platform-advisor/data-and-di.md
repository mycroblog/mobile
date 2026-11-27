# Data and DI

- Room 3.0.0 stable 2026-07-01, package androidx.room3, KSP and Kotlin only, coroutines only, needs a driver; Room 2 in maintenance [https://developer.android.com/jetpack/androidx/releases/room3, https://android-developers.googleblog.com/2026/03/room-30-modernizing-room.html, checked 2026-11-27]
- Paging RemoteMediator loads only at the list edges; REFRESH replaces the cached query -> no notion of gaps in the middle [https://developer.android.com/topic/libraries/architecture/paging/v3-network-db, checked 2026-11-27]
- Hilt is the officially recommended DI; recommended with WorkManager and back-stack-scoped ViewModels [https://developer.android.com/topic/architecture/recommendations, https://developer.android.com/training/dependency-injection/hilt-android, checked 2026-11-27]
- Hilt custom components can be built and released at runtime under SingletonComponent, but cannot sit between the ViewModel or Worker components -> look the account component up by ID [https://dagger.dev/hilt/custom-components, checked 2026-11-27]
