package dev.mycroblog.core.data

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import java.io.ByteArrayOutputStream
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

/**
 * A server that answers as my does, for one origin: `GET /api/v1/instance` with the shared fixture of the instance and
 * the [audiences] and [singleUser] a test sets, and `GET /api/v1/timelines/{local,federated}` with pages of
 * [pageSize] posts of [timelines], newest first, whatever `limit` asks for.
 *
 * The cursor of a page is [cursorAfter] its last post, which holds every reserved character of a query (ADR-0003 §2):
 * the server reads it from the query as a server decodes it (`+` is a space), and answers 400 to a cursor it did not
 * give. [answer] takes over the answer to a timeline when it returns one.
 */
class FakeMy {
    val audiences = mutableMapOf("local" to "everyone", "federated" to "everyone")
    var singleUser = false
    var instanceAnswers = true
    val timelines = mapOf("local" to mutableListOf<Int>(), "federated" to mutableListOf())
    var pageSize = 3
    var answer: (suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData?)? = null

    /** The timeline requests, in order, as the name of the timeline and the decoded cursor or null. */
    val timelineRequests = mutableListOf<Pair<String, String?>>()

    val engine = MockEngine { request -> handle(request) }

    /** Adds [count] posts above the newest of [timeline], and returns their numbers, newest first. */
    fun post(timeline: String, count: Int): List<Int> {
        val list = timelines.getValue(timeline)
        val start = (timelines.values.flatten().maxOrNull() ?: 0) + 1
        val added = (start until start + count).reversed().toList()
        list.addAll(0, added)
        return added
    }

    private suspend fun MockRequestHandleScope.handle(request: HttpRequestData): HttpResponseData {
        val path = request.url.encodedPath
        if (path == "/api/v1/instance") {
            if (!instanceAnswers) throw java.io.IOException("the instance does not answer")
            return json(instance())
        }
        val name = path.removePrefix("/api/v1/timelines/")
        val list = timelines[name] ?: return problem(HttpStatusCode.NotFound)
        val cursor = queryValue(request, "cursor")
        timelineRequests += name to cursor
        answer?.invoke(this, request)?.let { return it }
        val start = if (cursor == null) {
            0
        } else {
            val index = list.indexOfFirst { cursorAfter(it) == cursor }
            if (index < 0) return problem(HttpStatusCode.BadRequest)
            index + 1
        }
        val items = list.drop(start).take(pageSize)
        val page = buildMap {
            put("items", JsonArray(items.map(::status)))
            if (start + items.size < list.size) put("next_cursor", JsonPrimitive(cursorAfter(items.last())))
        }
        return json(JsonObject(page).toString())
    }

    private fun instance(): String {
        val fixture = Json.parseToJsonElement(Fixtures.read("instance/response.json")).jsonObject
        val timelines = JsonObject(audiences.mapValues { JsonPrimitive(it.value) })
        return JsonObject(fixture + mapOf("timelines" to timelines, "single_user" to JsonPrimitive(singleUser)))
            .toString()
    }

    private fun MockRequestHandleScope.json(body: String) =
        respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))

    private fun MockRequestHandleScope.problem(status: HttpStatusCode) = respond(
        """{"type":"about:blank","title":"${status.description}","status":${status.value}}""",
        status,
        headersOf(HttpHeaders.ContentType, "application/problem+json"),
    )

    companion object {
        /** The id of post [n]: a UUID, as the API's ids are. */
        fun id(n: Int): String = "01890a5d-ac96-774b-bcce-%012x".format(n)

        /** The cursor of the page after post [n], with the reserved characters of a query and non-ASCII. */
        fun cursorAfter(n: Int): String = "${id(n)} +/=&%?#日é"

        private val template: JsonObject by lazy {
            Json.parseToJsonElement(Fixtures.read("timeline-page/response.json")).jsonObject
                .getValue("items").jsonArray.first().jsonObject
        }

        /** Post [n], a status shaped as the first of shared/fixtures/timeline-page/response.json. */
        fun status(n: Int): JsonObject = JsonObject(
            template + mapOf(
                "id" to JsonPrimitive(id(n)),
                "uri" to JsonPrimitive("https://my.example/statuses/${id(n)}"),
                "url" to JsonPrimitive("https://my.example/@alice/${id(n)}"),
                "content" to JsonPrimitive("<p>post $n</p>"),
                "created_at" to JsonPrimitive("2026-11-21T%02d:%02d:%02dZ".format(n / 3600 % 24, n / 60 % 60, n % 60)),
            ),
        )

        /** The value of [name] in the query of [request], decoded as a server decodes a query. */
        fun queryValue(request: HttpRequestData, name: String): String? =
            request.url.encodedQuery.split('&').filter { it.isNotEmpty() }.firstNotNullOfOrNull { pair ->
                if (decode(pair.substringBefore('=')) == name) decode(pair.substringAfter('=', "")) else null
            }

        private fun decode(text: String): String {
            val bytes = ByteArrayOutputStream()
            var i = 0
            while (i < text.length) {
                when (val c = text[i]) {
                    '%' -> {
                        bytes.write(text.substring(i + 1, i + 3).toInt(16))
                        i += 2
                    }

                    '+' -> bytes.write(' '.code)

                    else -> bytes.write(c.toString().toByteArray())
                }
                i++
            }
            return bytes.toString(Charsets.UTF_8)
        }
    }
}
