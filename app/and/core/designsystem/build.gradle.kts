// The design system: the Material 3 theme and the components every screen shares (ADR-0002 §6), such as the one
// rendering of a post (#41).
plugins {
    alias(libs.plugins.mycroblog.android.library)
    alias(libs.plugins.mycroblog.android.compose)
}

dependencies {
    api(project(":core:model"))
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
}
