package dev.mycroblog.core.data

import dev.mycroblog.core.api.client.InstanceClient
import dev.mycroblog.core.api.client.NetworkError
import dev.mycroblog.core.api.client.NetworkResult
import dev.mycroblog.core.api.installApi
import dev.mycroblog.core.api.models.Instance as ApiInstance
import dev.mycroblog.core.api.problem
import dev.mycroblog.core.model.Instance
import dev.mycroblog.core.model.InstanceLookup
import dev.mycroblog.core.model.InstanceLookupFailure
import dev.mycroblog.core.model.ServerAddress
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import javax.inject.Inject
import javax.inject.Singleton

/** Reads the instance of any server, before the app has an account on it. Nothing is stored. */
@Singleton
class InstanceRepository @Inject constructor(private val engine: HttpClientEngine) {
    /** The instance of the server at [address] (`GET /api/v1/instance`), or why it could not be read. */
    suspend fun lookUp(address: ServerAddress): InstanceLookup {
        // A client of the shared engine for this one server; closing it leaves the engine open.
        val client = HttpClient(engine) {
            installApi(address.origin)
            install(HttpTimeout) {
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
            }
        }
        return client.use {
            when (val result = InstanceClient(it).getInstance()) {
                is NetworkResult.Success -> InstanceLookup.Found(result.data.toModel())
                is NetworkResult.Failure -> InstanceLookup.Failed(result.error.toFailure())
            }
        }
    }

    private companion object {
        const val CONNECT_TIMEOUT_MILLIS = 10_000L
        const val REQUEST_TIMEOUT_MILLIS = 15_000L
    }
}

private fun ApiInstance.toModel() = Instance(name = name, shortDescription = shortDescription?.ifBlank { null })

private fun NetworkError.toFailure(): InstanceLookupFailure = when (this) {
    // A body that is not a problem, such as a web server's error page, is not from a mycroblog server.
    is NetworkError.Http -> problem()?.let { InstanceLookupFailure.Problem(it.title) }
        ?: InstanceLookupFailure.NotMycroblog

    is NetworkError.Serialization -> InstanceLookupFailure.NotMycroblog

    is NetworkError.Network, is NetworkError.Unknown -> InstanceLookupFailure.NoAnswer
}
