// Compiles Fabrikt's output (KTOR client, KOTLINX_SERIALIZATION models) in place.
kotlin {
    sourceSets["main"].kotlin.srcDir("../../out/fabrikt-ktor/src/main/kotlin")
}
