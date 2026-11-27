import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import social.mycroblog.api.apis.TimelinesApi
import social.mycroblog.api.models.Problem
import social.mycroblog.api.models.StatusPage
import social.mycroblog.api.models.VoiceSFUNodeMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class OagRuntimeTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun oneOfWithDiscriminatorDoesNotDecode() {
        // The sealed class has no subclasses: the members are not declared as its subtypes.
        val r = runCatching {
            json.decodeFromString<VoiceSFUNodeMessage>("""{"v":1,"type":"candidate","candidate":"x"}""")
        }
        println("decode VoiceSFUNodeMessage: $r")
        assertFalse(r.isSuccess)
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
        // The generated ApiClient installs ContentNegotiation without a format; the caller adds JSON,
        // and application/problem+json too.
        val api = TimelinesApi("https://example.test/api/v1", engine, {
            it.install(ContentNegotiation) {
                json(json)
                json(json, ContentType.parse("application/problem+json"))
            }
        })
        val ok = api.getHomeTimeline(cursor = "a+b/c=")
        assertEquals(StatusPage(items = emptyList(), nextCursor = "a+b/c="), ok.body())
        println("home timeline request URL: $seenUrl")

        val failed = api.getHomeTimeline(cursor = "bad")
        assertEquals(401, failed.status)
        // The default (Problem) response has no type of its own; the caller picks it.
        val problem = failed.typedBody<Problem>(typeInfo<Problem>())
        assertEquals("/api/v1/problems/invalid-token", problem.type)
    }
}
