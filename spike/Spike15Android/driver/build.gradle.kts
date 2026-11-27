// A host for a long-lived UI Automator server. It instruments itself, so it runs in its own process and survives
// the app under test being killed.
plugins { id("com.android.application") }

android {
    namespace = "dev.mycroblog.spike15.driver"
    compileSdk { version = release(37) { minorApiLevel = 2 } }
    defaultConfig {
        applicationId = "dev.mycroblog.spike15.driver"
        minSdk = 29
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.4.1")
}
