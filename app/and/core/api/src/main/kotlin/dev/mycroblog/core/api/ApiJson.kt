package dev.mycroblog.core.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import java.net.URI
import java.util.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

/**
 * The JSON of the client API. It reads the UUIDs and URIs that the generated models mark `@Contextual` from their
 * strings, and ignores the properties a newer server adds.
 */
public val ApiJson: Json = Json {
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        contextual(UUID::class, UuidSerializer)
        contextual(URI::class, UriSerializer)
    }
}

/**
 * Sets up an [io.ktor.client.HttpClient] for the generated clients: their requests go to the server at [origin]
 * (`https://example.social`), under the path of the document's server, and their bodies are read with [ApiJson].
 */
public fun HttpClientConfig<*>.installApi(origin: String) {
    install(ContentNegotiation) { json(ApiJson) }
    defaultRequest { url(origin) }
}

internal object UuidSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("dev.mycroblog.core.api.UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
}

internal object UriSerializer : KSerializer<URI> {
    override val descriptor = PrimitiveSerialDescriptor("dev.mycroblog.core.api.URI", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: URI) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): URI = URI(decoder.decodeString())
}
