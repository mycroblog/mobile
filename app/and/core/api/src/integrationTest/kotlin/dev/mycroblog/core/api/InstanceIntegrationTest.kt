package dev.mycroblog.core.api

import dev.mycroblog.core.api.client.InstanceClient
import dev.mycroblog.core.api.client.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

/** The generated client reads `GET /api/v1/instance` of the local my (task my:up). */
class InstanceIntegrationTest {
    @Test
    fun getInstance() = runBlocking {
        HttpClient(CIO) { installApi(LocalMy.url) }.use { client ->
            when (val result = InstanceClient(client).getInstance()) {
                is NetworkResult.Success -> assertTrue("the instance has an empty name", result.data.name.isNotEmpty())
                is NetworkResult.Failure -> fail("GET /api/v1/instance of ${LocalMy.url} failed: ${result.error}")
            }
        }
    }
}
