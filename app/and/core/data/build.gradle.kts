// The data layer (ADR-0008): the store, the sync, and the repositories the screens read.
plugins {
    alias(libs.plugins.mycroblog.android.library)
    alias(libs.plugins.mycroblog.hilt)
}

dependencies {
    api(project(":core:model"))
    implementation(project(":core:api"))
}
