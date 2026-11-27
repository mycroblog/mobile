plugins {
    kotlin("jvm") version "2.4.21" apply false
    kotlin("plugin.serialization") version "2.4.21" apply false
}

val ktor = "3.6.1"

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }

    dependencies {
        "implementation"("io.ktor:ktor-client-core:$ktor")
        "implementation"("io.ktor:ktor-client-content-negotiation:$ktor")
        "implementation"("io.ktor:ktor-serialization-kotlinx-json:$ktor")
        "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-json:1.12.0")
        "implementation"("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
        "testImplementation"(kotlin("test"))
        "testImplementation"("io.ktor:ktor-client-mock:$ktor")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}
