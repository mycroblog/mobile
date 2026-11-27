// The Android app of mycroblog (ADR-0002 §1, ADR-0010). The build logic shared by the modules is the included build
// build-logic, as convention plugins; the versions are in gradle/libs.versions.toml.
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mycroblog"

include(":app")

// The common modules (ADR-0010 §6). They depend on no feature.
include(":core:model", ":core:api", ":core:data", ":core:designsystem")

// Each feature is a key module (api: only its destination keys) and an implementation module (impl), ADR-0010 §6.
listOf("timeline", "notifications", "search", "messages", "profile", "bookmarks", "settings").forEach {
    include(":feature:$it:api", ":feature:$it:impl")
}
