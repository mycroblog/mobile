// The design system: the Material 3 theme and the components every screen shares (ADR-0002 §6).
plugins {
    alias(libs.plugins.mycroblog.android.library)
    alias(libs.plugins.mycroblog.android.compose)
    alias(libs.plugins.roborazzi)
}

android {
    // The screenshot tests render with Robolectric, which needs the merged resources.
    testOptions.unitTests.isIncludeAndroidResources = true
}

// The screenshot tests (ADR-0006 §1): the reference images are committed under src/test/screenshots; a failed
// comparison writes the new image and the comparison under build/outputs/roborazzi.
roborazzi {
    outputDir.set(layout.projectDirectory.dir("src/test/screenshots"))
    @OptIn(com.github.takahirom.roborazzi.ExperimentalRoborazziApi::class)
    compare {
        outputDir.set(layout.buildDirectory.dir("outputs/roborazzi"))
    }
}

dependencies {
    implementation(libs.androidx.compose.material.icons.core)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
