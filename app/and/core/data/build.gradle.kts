// The data layer (ADR-0008): the store, the sync, and the repositories the screens read.
plugins {
    alias(libs.plugins.mycroblog.android.library)
    alias(libs.plugins.mycroblog.hilt)
}

dependencies {
    api(project(":core:model"))
    implementation(project(":core:api"))
    // The engine of the HttpClient on Android. The repositories take the engine from Hilt (HttpEngineModule), so a
    // test can give another one.
    implementation(libs.ktor.client.okhttp)
}
