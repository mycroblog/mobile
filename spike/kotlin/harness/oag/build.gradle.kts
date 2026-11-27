// Compiles openapi-generator's output (kotlin, jvm-ktor, kotlinx_serialization) in place.
kotlin {
    sourceSets["main"].kotlin.srcDir("../../out/oag-ktor/src/main/kotlin")
}
