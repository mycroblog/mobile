// The client API of the server (ADR-0003 §2). src/generated/kotlin holds the Ktor client and the kotlinx.serialization
// models that Fabrikt generates from shared/api/openapi.yaml (task api:generate writes it; never edit it by hand), and
// src/main/kotlin the thin hand-written parts beside them. The data layer depends on this module with
// `implementation`, so the generated types reach no module past it (ADR-0010 §6).
plugins {
    alias(libs.plugins.mycroblog.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets.named("main") {
        kotlin.srcDir("src/generated/kotlin")
    }
}

dependencies {
    // The generated clients take an HttpClient and return models that use these types in their signatures.
    api(libs.ktor.client.core)
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)

    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}

// The unit tests (ADR-0006 §1) read the shared fixtures (ADR-0002 §2) from shared/fixtures of the repository, never a
// copy. The directory is an input of the test task, so that a changed fixture runs the tests again.
val fixtures: Directory = layout.settingsDirectory.dir("../../shared/fixtures")

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnit(libs.versions.junit)
            dependencies {
                // The tests of the encoded values read the request a client sends from an engine that answers it.
                implementation(libs.ktor.client.mock)
            }
            targets.configureEach {
                testTask.configure {
                    inputs.dir(fixtures).withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
                    systemProperty("mycroblog.fixtures", fixtures.asFile.absolutePath)
                }
            }
        }

        // The integration tests talk to the local my at MY_URL, which task android:test takes from task my:up. They run
        // each time they are asked to, since the server is not an input Gradle can see.
        register<JvmTestSuite>("integrationTest") {
            useJUnit(libs.versions.junit)
            dependencies {
                implementation(project())
                implementation(libs.ktor.client.cio)
            }
            targets.configureEach {
                testTask.configure {
                    systemProperty("mycroblog.my.url", providers.environmentVariable("MY_URL").getOrElse(""))
                    outputs.upToDateWhen { false }
                }
            }
        }
    }
}
