// The screens of the settings feature (ADR-0010 §6).
plugins {
    alias(libs.plugins.mycroblog.feature.impl)
}

// The ViewModel's tests answer with the shared fixtures (ADR-0002 §2) of shared/fixtures of the repository, never a
// copy. The directory is an input of the test task, so that a changed fixture runs the tests again.
val fixtures: Directory = layout.settingsDirectory.dir("../../shared/fixtures")

android {
    testOptions.unitTests.all { test ->
        test.inputs.dir(fixtures).withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
        test.systemProperty("mycroblog.fixtures", fixtures.asFile.absolutePath)
    }
}

dependencies {
    implementation(project(":core:data"))
    // The server screen asks for a permission at run time (LocalNetworkPermission.kt).
    implementation(libs.androidx.activity.compose)
    // The server screen lays out its form by the window's height class (ADR-0010 §4).
    implementation(libs.androidx.compose.material3.adaptive)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    // The ViewModel's tests give its repository an engine that answers as each case needs.
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.kotlinx.serialization.json)
}
