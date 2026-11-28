package dev.mycroblog.core.data

import dev.mycroblog.core.api.RetryAfterRecorder
import dev.mycroblog.core.api.installApi
import dev.mycroblog.core.api.recordRetryAfter
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout

/**
 * A client of the shared [engine] for the server at [origin], for the generated clients; closing it leaves the engine
 * open. With [retryAfter], it records the `Retry-After` of a 429.
 */
internal fun apiClient(engine: HttpClientEngine, origin: String, retryAfter: RetryAfterRecorder? = null) =
    HttpClient(engine) {
        installApi(origin)
        install(HttpTimeout) {
            connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
            requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
        }
        retryAfter?.let { recordRetryAfter(it) }
    }

private const val CONNECT_TIMEOUT_MILLIS = 10_000L
private const val REQUEST_TIMEOUT_MILLIS = 15_000L
