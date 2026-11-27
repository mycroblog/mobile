package dev.mycroblog.core.api

import dev.mycroblog.core.api.client.NetworkError
import dev.mycroblog.core.api.models.Problem
import kotlinx.serialization.SerializationException

/**
 * The RFC 9457 problem that the server sent as the body of this error response, or null when the body is missing or
 * is not a problem (a proxy's error page, say). The generated clients keep the body as text.
 */
public fun NetworkError.Http.problem(): Problem? {
    val text = body ?: return null
    return try {
        ApiJson.decodeFromString(Problem.serializer(), text)
    } catch (_: SerializationException) {
        null
    } catch (_: IllegalArgumentException) {
        null
    }
}
