// The runner of the app-driving tool. It instruments itself, so it runs in its own process and survives the app
// under test being killed.
plugins { id("com.android.application") }

android {
    namespace = "dev.mycroblog.drive.runner"
    compileSdk { version = release(37) { minorApiLevel = 2 } }
    defaultConfig {
        applicationId = "dev.mycroblog.drive.runner"
        minSdk = 29
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    androidTestImplementation("androidx.core:core:1.19.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.4.1")
}
