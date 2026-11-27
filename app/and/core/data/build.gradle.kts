// The data layer (ADR-0008): the store, the sync, and the repositories the screens read.
plugins {
    alias(libs.plugins.mycroblog.android.library)
    alias(libs.plugins.mycroblog.hilt)
}

dependencies {
    api(project(":core:model"))
    implementation(project(":core:api"))
    // The engine of the HttpClient on Android. The repositories take the engine from Hilt (HttpEngineModule), so a
    // test can give another one.
    implementation(libs.ktor.client.okhttp)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.client.mock)
}

// The unit tests (ADR-0006 §1) read the shared fixtures (ADR-0002 §2) from shared/fixtures of the repository, never a
// copy. The directory is an input of the test task, so that a changed fixture runs the tests again.
val fixtures: Directory = layout.settingsDirectory.dir("../../shared/fixtures")

android {
    testOptions.unitTests.all { test ->
        test.inputs.dir(fixtures).withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
        test.systemProperty("mycroblog.fixtures", fixtures.asFile.absolutePath)
    }
}

// The integration tests are the classes named *IntegrationTest of src/integrationTest, built with the unit tests of
// the debug variant but run only by this task: they talk to the local my at MY_URL, which task android:test takes from
// task my:up. They run each time they are asked to, since the server is not an input Gradle can see.
android.sourceSets.getByName("test").kotlin.srcDir("src/integrationTest/kotlin")

val integrationTestClasses = "**/*IntegrationTest*.class"

tasks.withType<Test>().configureEach {
    if (name != "integrationTest") exclude(integrationTestClasses)
}

tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests against the local my at MY_URL."
    group = "verification"
    val unitTest = tasks.named<Test>("testDebugUnitTest")
    testClassesDirs = files(unitTest.map { it.testClassesDirs })
    classpath = files(unitTest.map { it.classpath })
    include(integrationTestClasses)
    systemProperty("mycroblog.my.url", providers.environmentVariable("MY_URL").getOrElse(""))
    outputs.upToDateWhen { false }
}
