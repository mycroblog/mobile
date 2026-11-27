package dev.mycroblog.buildlogic

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

/**
 * The rules of ADR-0010 §6 on which module may depend on which, for the module paths of settings.gradle.kts:
 * `:core:<name>`, `:feature:<name>:api`, `:feature:<name>:impl`, and `:app`.
 */
object ModuleBoundaries {
    private val feature = Regex("^:feature:([^:]+):(api|impl)$")

    /** Why [from] must not depend on [to], with what to do instead, or null when it may. */
    fun violation(from: String, to: String): String? {
        val source = feature.find(from)?.groupValues
        val target = feature.find(to)?.groupValues
        return when {
            // The Android plugins have a module depend on itself, as for its tests.
            from == to -> null

            source != null && target != null && source[2] == "impl" && target[2] == "impl" && source[1] != target[1] ->
                "$from depends on $to, but a feature's implementation must not depend on another feature's " +
                    "implementation (ADR-0010 §6). Depend on :feature:${target[1]}:api instead and navigate to its " +
                    "destination keys; the app module puts the implementations together."

            source != null && source[2] == "api" && to != ":core:model" ->
                "$from depends on $to, but a feature's api module holds only its destination keys and depends on " +
                    "nothing but :core:model (ADR-0010 §6). Put what needs $to in :feature:${source[1]}:impl."

            from.startsWith(":core:") && target != null ->
                "$from depends on $to, but a common module must not depend on a feature (ADR-0010 §6). Move what " +
                    "both need into a common module, or the code that needs the feature into the feature."

            to == ":app" ->
                "$from depends on $to, but nothing depends on the app module: move what both need into a common module."

            else -> null
        }
    }
}

/** Fails the build, with what to do instead, when this module declares a dependency that ADR-0010 §6 forbids. */
internal fun Project.enforceModuleBoundaries() {
    val from = path
    configurations.configureEach {
        dependencies.withType(ProjectDependency::class.java).configureEach {
            ModuleBoundaries.violation(from, path)?.let { throw GradleException(it) }
        }
    }
}
