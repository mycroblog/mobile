package dev.mycroblog.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(name: String): String = findVersion(name).get().requiredVersion

internal fun VersionCatalog.library(name: String) = findLibrary(name).get()

/** What every module shares: a group unique to its path, the JDK toolchain, and the module boundaries. */
internal fun Project.configureModule() {
    // Projects of the same name (every feature has an `api` and an `impl`) need distinct coordinates.
    group = "dev.mycroblog" + path.substringBeforeLast(':').replace(':', '.')
    extensions.getByType<KotlinBaseExtension>().jvmToolchain(libs.version("jdk").toInt())
    enforceModuleBoundaries()
    // A failed test prints its name and message even when Gradle runs quietly, as the checks run it; the stack trace
    // is in the report that Gradle names.
    tasks.withType<Test>().configureEach {
        testLogging.quiet {
            events(TestLogEvent.FAILED)
            exceptionFormat = TestExceptionFormat.FULL
            showStackTraces = false
        }
    }
}

/** The namespace of a module's Android code: `:feature:timeline:impl` is dev.mycroblog.feature.timeline.impl. */
internal val Project.androidNamespace: String
    get() = "dev.mycroblog" + path.replace(':', '.')

private val Project.javaVersion: JavaVersion
    get() = JavaVersion.toVersion(libs.version("jdk"))

internal fun Project.configureApplication(extension: ApplicationExtension) = with(extension) {
    compileSdk {
        version = release(libs.version("compileSdk").toInt()) {
            minorApiLevel = libs.version("compileSdkMinor").toInt()
        }
    }
    buildToolsVersion = libs.version("buildTools")
    defaultConfig {
        minSdk = libs.version("minSdk").toInt()
        targetSdk = libs.version("targetSdk").toInt()
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    lint { configureLint(this) }
}

internal fun Project.configureLibrary(extension: LibraryExtension) = with(extension) {
    namespace = androidNamespace
    compileSdk {
        version = release(libs.version("compileSdk").toInt()) {
            minorApiLevel = libs.version("compileSdkMinor").toInt()
        }
    }
    buildToolsVersion = libs.version("buildTools")
    defaultConfig {
        minSdk = libs.version("minSdk").toInt()
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    lint { configureLint(this) }
}

/** Android Lint on a module with no Android code, with the options of the Android modules. */
internal fun Project.configureJvmLint() {
    pluginManager.apply("com.android.lint")
    extensions.configure<Lint> { configureLint(this) }
}

/**
 * Android Lint, which task android:check runs in every module (`./gradlew lint`): any warning fails the check, and
 * lint prints each finding with its issue id. Generated sources are not checked (lint's default).
 */
internal fun configureLint(lint: Lint) = with(lint) {
    abortOnError = true
    warningsAsErrors = true
    textReport = true
    // These report that a newer version was released, so they would fail a commit that did not change: versions
    // are raised on purpose, each in a pull request of its own.
    disable += setOf("GradleDependency", "AndroidGradlePluginVersion", "NewerVersionAvailable", "OldTargetApi")
}
