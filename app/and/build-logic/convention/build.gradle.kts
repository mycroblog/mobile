plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.mycroblog.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.mycroblog.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = libs.plugins.mycroblog.android.compose.get().pluginId
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("featureApi") {
            id = libs.plugins.mycroblog.feature.api.get().pluginId
            implementationClass = "FeatureApiConventionPlugin"
        }
        register("featureImpl") {
            id = libs.plugins.mycroblog.feature.impl.get().pluginId
            implementationClass = "FeatureImplConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.mycroblog.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.mycroblog.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
