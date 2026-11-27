import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import social.mycroblog.api.client.NetworkError
import social.mycroblog.api.client.NetworkResult
import social.mycroblog.api.client.TimelinesClient
import social.mycroblog.api.models.Problem
import social.mycroblog.api.models.StatusPage
import social.mycroblog.api.models.VoiceSFUCandidate
import social.mycroblog.api.models.VoiceSFUError
import social.mycroblog.api.models.VoiceSFUNodeMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class FabriktRuntimeTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun oneOfWithDiscriminatorDecodesToTheSubtype() {
        val m = json.decodeFromString<VoiceSFUNodeMessage>("""{"v":1,"type":"candidate","candidate":"x"}""")
        assertEquals(VoiceSFUCandidate(v = 1, candidate = "x"), m)
        val e = json.decodeFromString<VoiceSFUNodeMessage>(
            """{"v":1,"type":"error","code":"room_unavailable","message":"m"}""",
        )
        assertIs<VoiceSFUError>(e)
        // Encoding writes the discriminator back.
        assertEquals("""{"type":"candidate","v":1,"candidate":"x"}""", json.encodeToString<VoiceSFUNodeMessage>(m))
    }

    @Test
    fun unknownDiscriminatorFails() {
        // The spec says a client ignores a `type` it does not know; the generated code throws.
        assertFailsWith<SerializationException> {
            json.decodeFromString<VoiceSFUNodeMessage>("""{"v":1,"type":"future","x":1}""")
        }
    }

    @Test
    fun homeTimelineAndProblem() = runBlocking {
        var seenUrl = ""
        val engine = MockEngine { req ->
            seenUrl = req.url.toString()
            if (req.url.parameters["cursor"] == "bad") {
                respond(
                    """{"type":"/api/v1/problems/invalid-token","title":"t","status":401}""",
                    HttpStatusCode.Unauthorized,
                    headersOf(HttpHeaders.ContentType, "application/problem+json"),
                )
            } else {
                respond(
                    """{"items":[],"next_cursor":"a+b/c="}""",
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }
        }
        val http = HttpClient(engine) { install(ContentNegotiation) { json(json) } }
        val client = TimelinesClient(http)
        val ok = client.getHomeTimeline(cursor = "a+b/c=")
        assertEquals(NetworkResult.Success(StatusPage(nextCursor = "a+b/c=", items = emptyList())), ok)
        // The query value is not percent-encoded by the generated code.
        println("home timeline request URL: $seenUrl")

        val failed = client.getHomeTimeline(cursor = "bad")
        assertIs<NetworkResult.Failure>(failed)
        val http401 = assertIs<NetworkError.Http>(failed.error)
        // The Problem is left as a string body; the caller decodes it.
        val problem = json.decodeFromString<Problem>(http401.body!!)
        assertEquals("/api/v1/problems/invalid-token", problem.type)
    }
}
