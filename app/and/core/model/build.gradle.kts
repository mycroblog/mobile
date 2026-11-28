// The domain types: plain Kotlin, used by every other module, depending on none.
plugins {
    alias(libs.plugins.mycroblog.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

// The records of the store (ADR-0008 §2) are kept as JSON in it.
dependencies {
    api(libs.kotlinx.serialization.core)
}

// The unit tests (ADR-0006 §1) read the shared fixtures (ADR-0002 §2) from shared/fixtures of the repository, never a
// copy. The directory is an input of the test task, so that a changed fixture runs the tests again.
val fixtures: Directory = layout.settingsDirectory.dir("../../shared/fixtures")

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnit(libs.versions.junit)
            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
            targets.configureEach {
                testTask.configure {
                    inputs.dir(fixtures).withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
                    systemProperty("mycroblog.fixtures", fixtures.asFile.absolutePath)
                }
            }
        }
    }
}
