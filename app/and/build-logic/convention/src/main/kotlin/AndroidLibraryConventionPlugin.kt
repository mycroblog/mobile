import com.android.build.api.dsl.LibraryExtension
import dev.mycroblog.buildlogic.configureLibrary
import dev.mycroblog.buildlogic.configureModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/** An Android library module, its namespace from its path. */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        extensions.configure<LibraryExtension> { configureLibrary(this) }
        configureModule()
    }
}
