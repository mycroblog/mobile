// The screens of the settings feature (ADR-0010 §6).
plugins {
    alias(libs.plugins.mycroblog.feature.impl)
    alias(libs.plugins.roborazzi)
}

// The ViewModel's tests answer with the shared fixtures (ADR-0002 §2) of shared/fixtures of the repository, never a
// copy. The directory is an input of the test task, so that a changed fixture runs the tests again.
val fixtures: Directory = layout.settingsDirectory.dir("../../shared/fixtures")

android {
    // The screenshot tests render with Robolectric, which needs the merged resources.
    testOptions.unitTests.isIncludeAndroidResources = true
    testOptions.unitTests.all { test ->
        test.inputs.dir(fixtures).withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
        test.systemProperty("mycroblog.fixtures", fixtures.asFile.absolutePath)
    }
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
    implementation(project(":core:data"))
    // The server screen asks for a permission at run time (LocalNetworkPermission.kt).
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    // The ViewModel's tests give its repository an engine that answers as each case needs.
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
