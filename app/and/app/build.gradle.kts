// The app: one activity (ADR-0010 §1), the root navigation, and the features put together.
plugins {
    alias(libs.plugins.mycroblog.android.application)
    alias(libs.plugins.mycroblog.android.compose)
    alias(libs.plugins.mycroblog.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.mycroblog.app"
    defaultConfig {
        applicationId = "dev.mycroblog.app"
        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    listOf("timeline", "notifications", "search", "messages", "profile", "bookmarks", "settings").forEach {
        implementation(project(":feature:$it:impl"))
    }

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.work.runtime)
    implementation(libs.kotlinx.serialization.core)
    ksp(libs.androidx.hilt.compiler)
}
