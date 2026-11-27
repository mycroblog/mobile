import dev.mycroblog.buildlogic.libs
import dev.mycroblog.buildlogic.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A feature's key module (ADR-0010 §6): only its destination keys, as serializable Navigation 3 keys, so that the
 * back stack is saved as keys (ADR-0010 §2). Other features navigate to the feature through it.
 */
class FeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("mycroblog.android.library")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
        dependencies {
            add("api", libs.library("androidx-navigation3-runtime"))
            add("api", libs.library("kotlinx-serialization-core"))
        }
    }
}
