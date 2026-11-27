import dev.mycroblog.buildlogic.configureJvmLint
import dev.mycroblog.buildlogic.configureModule
import org.gradle.api.Plugin
import org.gradle.api.Project

/** A Kotlin module with no Android code. */
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")
        configureModule()
        configureJvmLint()
    }
}
