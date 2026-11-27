// The client API of the server (ADR-0003 §2). src/generated/kotlin holds the Ktor client and the kotlinx.serialization
// models that Fabrikt generates from shared/api/openapi.yaml (task api:generate writes it; never edit it by hand), and
// src/main/kotlin the thin hand-written parts beside them. The data layer depends on this module with
// `implementation`, so the generated types reach no module past it (ADR-0010 §6).
plugins {
    alias(libs.plugins.mycroblog.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets.named("main") {
        kotlin.srcDir("src/generated/kotlin")
    }
}

dependencies {
    // The generated clients take an HttpClient and return models that use these types in their signatures.
    api(libs.ktor.client.core)
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)

    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}
