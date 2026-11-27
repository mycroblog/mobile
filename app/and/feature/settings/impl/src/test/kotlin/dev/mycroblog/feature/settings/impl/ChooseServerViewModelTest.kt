package dev.mycroblog.feature.settings.impl

import androidx.lifecycle.SavedStateHandle
import dev.mycroblog.core.data.InstanceRepository
import dev.mycroblog.core.model.Instance
import dev.mycroblog.core.model.InstanceLookupFailure
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import java.io.IOException
import java.util.Collections
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** How the server screen's state moves as the address changes and lookups run. */
@OptIn(ExperimentalCoroutinesApi::class)
class ChooseServerViewModelTest {
    /** A server that answers each request with the next of [answers], once its gate opens, and records the URLs. */
    private class Server(vararg answers: Answer) {
        private val queue = ArrayDeque(answers.toList())
        val urls: MutableList<String> = Collections.synchronizedList(mutableListOf())

        @Volatile
        var gate = CompletableDeferred(Unit)

        val engine = MockEngine { request ->
            urls += request.url.toString()
            gate.await()
            when (val answer = synchronized(queue) { queue.removeFirst() }) {
                is Answer.Instance -> respond(
                    answer.json,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )

                Answer.Unreachable -> throw IOException("unreachable")
            }
        }
    }

    private sealed interface Answer {
        data class Instance(val json: String) : Answer
        data object Unreachable : Answer
    }

    /** The instance of the shared fixture, as my answers it and as a client takes it. */
    private val instance = Answer.Instance(Fixtures.read("instance/response.json"))
    private val found = Json.parseToJsonElement(Fixtures.read("instance/expected.json")).jsonObject.let {
        Instance(it.getValue("name").jsonPrimitive.content, it.getValue("short_description").jsonPrimitive.content)
    }

    @Before
    fun setMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun resetMain() = Dispatchers.resetMain()

    private fun viewModel(server: Server) = ChooseServerViewModel(InstanceRepository(server.engine), SavedStateHandle())

    /** The lookup once it is no longer under way, which the engine's thread decides. */
    private suspend fun ChooseServerViewModel.settled(): ServerLookupState =
        uiState.first { it.lookup !is ServerLookupState.Loading }.lookup

    @Test
    fun itStartsIdleWithAnEmptyAddressThatCannotBeLookedUp() {
        val state = viewModel(Server()).uiState.value
        assertEquals("", state.address)
        assertEquals(ServerLookupState.Idle, state.lookup)
        assertFalse(state.canLookUp)
    }

    @Test
    fun anAddressCanBeLookedUp() {
        val viewModel = viewModel(Server())
        viewModel.onAddressChange("example.social")
        assertEquals("example.social", viewModel.uiState.value.address)
        assertTrue(viewModel.uiState.value.canLookUp)
    }

    @Test
    fun anEmptyAddressIsNotLookedUp() {
        val server = Server(instance)
        val viewModel = viewModel(server)
        viewModel.lookUp()
        assertEquals(ServerLookupState.Idle, viewModel.uiState.value.lookup)
        assertEquals(emptyList<String>(), server.urls)
    }

    @Test
    fun anInvalidAddressIsNotFetched() {
        val server = Server(instance)
        val viewModel = viewModel(server)
        viewModel.onAddressChange("http://example.social")
        viewModel.lookUp()
        assertEquals(ServerLookupState.InvalidAddress, viewModel.uiState.value.lookup)
        assertEquals(emptyList<String>(), server.urls)
    }

    @Test
    fun aLookupIsLoadingItsHostThenFound() = runTest {
        val server = Server(instance).apply { gate = CompletableDeferred() }
        val viewModel = viewModel(server)
        viewModel.onAddressChange(" Example.Social/about ")
        viewModel.lookUp()
        assertEquals(ServerLookupState.Loading("example.social"), viewModel.uiState.value.lookup)
        assertFalse("Look up is disabled while a lookup is under way", viewModel.uiState.value.canLookUp)

        server.gate.complete(Unit)
        assertEquals(
            ServerLookupState.Found("example.social", found),
            viewModel.settled(),
        )
        assertEquals(listOf("https://example.social/api/v1/instance"), server.urls)
        assertTrue(viewModel.uiState.value.canLookUp)
    }

    @Test
    fun anUnreachableServerFailsWithNoAnswer() = runTest {
        val viewModel = viewModel(Server(Answer.Unreachable))
        viewModel.onAddressChange("example.social")
        viewModel.lookUp()
        assertEquals(ServerLookupState.Failed("example.social", InstanceLookupFailure.NoAnswer), viewModel.settled())
    }

    @Test
    fun retryRepeatsTheSameLookup() = runTest {
        val server = Server(Answer.Unreachable, instance)
        val viewModel = viewModel(server)
        viewModel.onAddressChange("example.social:8443")
        viewModel.lookUp()
        assertEquals(ServerLookupState.Failed("example.social", InstanceLookupFailure.NoAnswer), viewModel.settled())

        server.gate = CompletableDeferred()
        viewModel.retry()
        assertEquals(ServerLookupState.Loading("example.social"), viewModel.uiState.value.lookup)
        server.gate.complete(Unit)
        assertEquals(
            ServerLookupState.Found("example.social", found),
            viewModel.settled(),
        )
        assertEquals(
            listOf("https://example.social:8443/api/v1/instance", "https://example.social:8443/api/v1/instance"),
            server.urls.toList(),
        )
    }

    @Test
    fun changingTheAddressClearsAFoundInstance() = runTest {
        val viewModel = viewModel(Server(instance))
        viewModel.onAddressChange("example.social")
        viewModel.lookUp()
        assertEquals(ServerLookupState.Found("example.social", found), viewModel.settled())

        viewModel.onAddressChange("example.social2")
        assertEquals(ServerLookupState.Idle, viewModel.uiState.value.lookup)
        assertEquals("example.social2", viewModel.uiState.value.address)
    }

    @Test
    fun changingTheAddressClearsAFailure() = runTest {
        val viewModel = viewModel(Server(Answer.Unreachable))
        viewModel.onAddressChange("example.social")
        viewModel.lookUp()
        assertEquals(ServerLookupState.Failed("example.social", InstanceLookupFailure.NoAnswer), viewModel.settled())

        viewModel.onAddressChange("example.socia")
        assertEquals(ServerLookupState.Idle, viewModel.uiState.value.lookup)
    }

    @Test
    fun changingTheAddressClearsAnInvalidAddress() {
        val viewModel = viewModel(Server())
        viewModel.onAddressChange("not a server")
        viewModel.lookUp()
        assertEquals(ServerLookupState.InvalidAddress, viewModel.uiState.value.lookup)

        viewModel.onAddressChange("example.social")
        assertEquals(ServerLookupState.Idle, viewModel.uiState.value.lookup)
    }
}
