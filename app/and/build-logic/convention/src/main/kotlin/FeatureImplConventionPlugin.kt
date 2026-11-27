import dev.mycroblog.buildlogic.library
import dev.mycroblog.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * A feature's implementation module (ADR-0010 §6): its screens and their ViewModels, on its own key module and the
 * common modules. It never depends on another feature's implementation; the build fails if it does.
 */
class FeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("mycroblog.android.library")
        pluginManager.apply("mycroblog.android.compose")
        pluginManager.apply("mycroblog.hilt")
        dependencies {
            add("api", project(path.removeSuffix(":impl") + ":api"))
            add("implementation", project(":core:designsystem"))
            add("implementation", libs.library("androidx-navigation3-ui"))
            add("implementation", libs.library("androidx-lifecycle-viewmodel-navigation3"))
            add("implementation", libs.library("androidx-hilt-lifecycle-viewmodel-compose"))
        }
    }
}
