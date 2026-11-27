import com.android.build.api.dsl.ApplicationExtension
import dev.mycroblog.buildlogic.configureApplication
import dev.mycroblog.buildlogic.configureModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/** The app module: the one Android application, which puts the features together. */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        extensions.configure<ApplicationExtension> { configureApplication(this) }
        configureModule()
    }
}
