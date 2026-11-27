// The client API of the server: the Ktor client and the kotlinx.serialization models that Fabrikt generates from the
// server's OpenAPI specification (ADR-0003 §2) go under src/main/kotlin, with the thin hand-written parts beside them.
plugins {
    alias(libs.plugins.mycroblog.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}
