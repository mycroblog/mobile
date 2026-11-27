package dev.mycroblog.core.data

import dev.mycroblog.core.model.InstanceLookup
import dev.mycroblog.core.model.InstanceLookupFailure
import dev.mycroblog.core.model.ServerAddress
import io.ktor.client.engine.okhttp.OkHttp
import java.net.ServerSocket
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

/** [InstanceRepository.lookUp] with the engine of the app, against the local my (task my:up) and a closed port. */
class InstanceRepositoryIntegrationTest {
    private val repository = InstanceRepository(OkHttp.create())

    private fun address(text: String): ServerAddress {
        val address = ServerAddress.parse(text)
        assertNotNull("\"$text\" is not a valid server address", address)
        return address!!
    }

    @Test
    fun theLocalMyIsFound() = runBlocking {
        when (val lookup = repository.lookUp(address(LocalMy.url))) {
            is InstanceLookup.Found -> assertTrue("the instance has an empty name", lookup.instance.name.isNotEmpty())
            is InstanceLookup.Failed -> fail("looking up the local my at ${LocalMy.url} failed: ${lookup.failure}")
        }
    }

    @Test
    fun aClosedPortIsNoAnswer() = runBlocking {
        // A port that was free a moment ago, so that nothing listens on it.
        val port = ServerSocket(0).use { it.localPort }
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NoAnswer),
            repository.lookUp(address("http://localhost:$port")),
        )
    }
}
