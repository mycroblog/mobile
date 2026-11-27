package dev.mycroblog.core.data

import dev.mycroblog.core.model.Instance
import dev.mycroblog.core.model.InstanceLookup
import dev.mycroblog.core.model.InstanceLookupFailure
import dev.mycroblog.core.model.ServerAddress
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import java.io.IOException
import java.net.ConnectException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

/** How [InstanceRepository.lookUp] reads the answer of `GET /api/v1/instance`, and each way it can fail. */
class InstanceRepositoryTest {
    private val address = ServerAddress.parse("example.social")!!
    private val response = Fixtures.read("instance/response.json")
    private val expected = Json.parseToJsonElement(Fixtures.read("instance/expected.json")).jsonObject

    private fun lookUp(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): InstanceLookup {
        var lookup: InstanceLookup? = null
        runTest { lookup = InstanceRepository(MockEngine(handler)).lookUp(address) }
        return lookup!!
    }

    private fun MockRequestHandleScope.json(
        body: String,
        status: HttpStatusCode = HttpStatusCode.OK,
        type: String = "application/json",
    ) = respond(body, status, headersOf(HttpHeaders.ContentType, type))

    @Test
    fun itAsksForTheInstanceOfTheOrigin() {
        val requests = mutableListOf<HttpRequestData>()
        lookUp { request ->
            requests += request
            json(response)
        }
        assertEquals(1, requests.size)
        assertEquals(HttpMethod.Get, requests.single().method)
        assertEquals("https://example.social/api/v1/instance", requests.single().url.toString())
    }

    @Test
    fun theSharedFixtureIsFoundAsItsExpectedNameAndShortDescription() {
        val instance = Instance(
            name = expected.getValue("name").jsonPrimitive.content,
            shortDescription = expected.getValue("short_description").jsonPrimitive.content,
        )
        assertEquals(InstanceLookup.Found(instance), lookUp { json(response) })
    }

    @Test
    fun anInstanceWithNoShortDescriptionIsFoundWithNone() {
        val withoutShortDescription = JsonObject(Json.parseToJsonElement(response).jsonObject - "short_description")
        val lookup = lookUp { json(withoutShortDescription.toString()) }
        assertEquals(
            InstanceLookup.Found(
                Instance(name = expected.getValue("name").jsonPrimitive.content, shortDescription = null),
            ),
            lookup,
        )
    }

    @Test
    fun aRefusedConnectionIsNoAnswer() {
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NoAnswer),
            lookUp {
                throw ConnectException("refused")
            },
        )
    }

    @Test
    fun aNetworkErrorIsNoAnswer() {
        assertEquals(InstanceLookup.Failed(InstanceLookupFailure.NoAnswer), lookUp { throw IOException("reset") })
    }

    @Test
    fun aTimeoutIsNoAnswer() {
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NoAnswer),
            lookUp { request -> throw HttpRequestTimeoutException(request.url.toString(), 15_000L) },
        )
    }

    @Test
    fun aPageThatIsNotFoundIsNotMycroblog() {
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NotMycroblog),
            lookUp { json("<html><body>Not Found</body></html>", HttpStatusCode.NotFound, "text/html") },
        )
    }

    @Test
    fun aWebPageInsteadOfTheInstanceIsNotMycroblog() {
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NotMycroblog),
            lookUp { json("<html><body>Welcome</body></html>", type = "text/html") },
        )
    }

    @Test
    fun jsonThatIsNotAnInstanceIsNotMycroblog() {
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.NotMycroblog),
            lookUp { json("""{"uri":"example.social","title":"Another server"}""") },
        )
    }

    @Test
    fun aProblemIsItsTitle() {
        val problem = """{"type":"about:blank","title":"Service Unavailable","status":503}"""
        assertEquals(
            InstanceLookup.Failed(InstanceLookupFailure.Problem("Service Unavailable")),
            lookUp { json(problem, HttpStatusCode.ServiceUnavailable, "application/problem+json") },
        )
    }
}
