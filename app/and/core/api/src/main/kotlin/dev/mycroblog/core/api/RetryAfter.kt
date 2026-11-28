package dev.mycroblog.core.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

/**
 * The `Retry-After` of the last 429 that a client answered with, which the generated clients do not keep: their
 * failure holds the status and the body only.
 */
public class RetryAfterRecorder {
    /** The header's value as the server sent it, or null when no 429 carried one. */
    @Volatile
    public var value: String? = null
        internal set
}

/** Records the `Retry-After` of every 429 answer of this client in [recorder]. */
public fun HttpClientConfig<*>.recordRetryAfter(recorder: RetryAfterRecorder) {
    install(
        createClientPlugin("RetryAfterRecorder") {
            onResponse { response ->
                if (response.status == HttpStatusCode.TooManyRequests) {
                    recorder.value = response.headers[HttpHeaders.RetryAfter]
                }
            }
        },
    )
}
