import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import dev.mycroblog.buildlogic.libs
import dev.mycroblog.buildlogic.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

/** Jetpack Compose with Material 3 in an Android module, its versions from the Compose BOM. */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        extensions.findByType<ApplicationExtension>()?.buildFeatures?.compose = true
        extensions.findByType<LibraryExtension>()?.buildFeatures?.compose = true
        dependencies {
            val bom = platform(libs.library("androidx-compose-bom"))
            add("implementation", bom)
            add("implementation", libs.library("androidx-compose-material3"))
            add("implementation", libs.library("androidx-compose-ui-tooling-preview"))
            add("debugImplementation", libs.library("androidx-compose-ui-tooling"))
        }
    }
}
