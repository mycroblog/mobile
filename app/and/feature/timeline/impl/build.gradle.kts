// The screens of the timeline feature (ADR-0010 §6).
plugins {
    alias(libs.plugins.mycroblog.feature.impl)
}

dependencies {
    implementation(project(":core:data"))
    // Home opens the server screen of the settings feature, by its key.
    implementation(project(":feature:settings:api"))
}
