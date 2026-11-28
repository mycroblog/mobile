package dev.mycroblog.core.api

import dev.mycroblog.core.api.client.NetworkResult
import dev.mycroblog.core.api.client.TimelinesClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * A path value through [pathValue] and a query value through [queryValue] reach the server byte for byte (ADR-0003
 * §2): the request the generated client sends, decoded as a server decodes a path segment and a query (where `+` is a
 * space), is the UTF-8 of the value, whatever reserved characters it holds.
 */
class UrlValuesTest {
    private val values = listOf(
        "a+b",
        "a/b",
        "a=b",
        "a&b=c",
        "100%",
        "%2F",
        "a b",
        "日本語",
        "café",
        "😀",
        "b2xkZXI+/==",
        "+/=&% é日?#",
    )

    private fun send(call: suspend (TimelinesClient) -> NetworkResult<*>): HttpRequestData {
        var sent: HttpRequestData? = null
        val engine = MockEngine { request ->
            sent = request
            respond("""{"items":[]}""", headers = headersOf(HttpHeaders.ContentType, "application/json"))
        }
        runBlocking {
            HttpClient(engine) { installApi("https://example.social") }.use { client ->
                val result = call(TimelinesClient(client))
                assertTrue("the client did not read the empty page: $result", result is NetworkResult.Success)
            }
        }
        return sent ?: error("the client sent no request")
    }

    /** The bytes of a percent-encoded [text]: `%XX` is one byte, `+` a space when [plusIsSpace], any other its ASCII. */
    private fun decode(text: String, plusIsSpace: Boolean): ByteArray {
        val bytes = ByteArrayOutputStream()
        var i = 0
        while (i < text.length) {
            val c = text[i]
            when {
                c == '%' -> {
                    bytes.write(text.substring(i + 1, i + 3).toInt(16))
                    i += 3
                    continue
                }

                c == '+' && plusIsSpace -> bytes.write(' '.code)

                else -> {
                    check(c.code < 0x80) { "the request holds a character that is not ASCII: \"$text\"" }
                    bytes.write(c.code)
                }
            }
            i++
        }
        return bytes.toByteArray()
    }

    /** The raw values of [name] in the query of [request], as a server splits it. */
    private fun rawQueryValues(request: HttpRequestData, name: String): List<String> =
        request.url.encodedQuery.split('&').filter { it.isNotEmpty() }.mapNotNull { pair ->
            val key = pair.substringBefore('=')
            if (String(decode(key, plusIsSpace = true)) == name) pair.substringAfter('=', "") else null
        }

    @Test
    fun aCursorReachesTheServerByteForByte() {
        val failures = values.mapNotNull { value ->
            val request = send { it.getLocalTimeline(cursor = queryValue(value)) }
            val raw = rawQueryValues(request, "cursor")
            when {
                request.url.encodedPath != "/api/v1/timelines/local" ->
                    "\"$value\": the path became ${request.url.encodedPath}"

                raw.size != 1 -> "\"$value\": the query ${request.url.encodedQuery} has ${raw.size} cursors"

                !decode(raw.single(), plusIsSpace = true).contentEquals(value.toByteArray()) ->
                    "\"$value\": the query ${request.url.encodedQuery} decodes to " +
                        "\"${String(decode(raw.single(), plusIsSpace = true))}\""

                else -> null
            }
        }
        assertEquals(
            "the cursors that did not reach the server as they are:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun aPathValueReachesTheServerByteForByte() {
        val failures = values.mapNotNull { value ->
            val request = send { it.getHashtagTimeline(hashtag = pathValue(value)) }
            val path = request.url.encodedPath
            val prefix = "/api/v1/timelines/tag/"
            val segment = path.removePrefix(prefix)
            when {
                !path.startsWith(prefix) -> "\"$value\": the path became $path"

                '/' in segment -> "\"$value\": the path $path has more than one segment after the tag"

                request.url.encodedQuery.isNotEmpty() ->
                    "\"$value\": part of it went into the query ${request.url.encodedQuery}"

                request.url.encodedFragment.isNotEmpty() -> "\"$value\": part of it went into the fragment"

                !decode(segment, plusIsSpace = false).contentEquals(value.toByteArray()) ->
                    "\"$value\": the path $path decodes to \"${String(decode(segment, plusIsSpace = false))}\""

                else -> null
            }
        }
        assertEquals(
            "the path values that did not reach the server as they are:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun anEncodedValueIsOnlyAsciiThatNoServerReadsAsADelimiter() {
        val reserved = setOf('+', '/', '=', '&', '%', ' ', '?', '#')
        val failures = values.flatMap { value ->
            listOf("pathValue" to pathValue(value), "queryValue" to queryValue(value)).mapNotNull { (name, encoded) ->
                val bad = encoded.filterIndexed { i, c ->
                    c.code >= 0x80 || (c in reserved && !(c == '%' && encoded.isHexAt(i + 1)))
                }
                if (bad.isEmpty()) null else "$name(\"$value\") = \"$encoded\" keeps \"$bad\""
            }
        }
        assertEquals("the encoded values that keep a delimiter:\n" + failures.joinToString("\n"), 0, failures.size)
    }

    @Test
    fun anUnreservedValueStaysAsItIs() {
        val unreserved = "AZaz09-._~"
        assertArrayEquals(unreserved.toByteArray(), pathValue(unreserved).toByteArray())
        assertArrayEquals(unreserved.toByteArray(), queryValue(unreserved).toByteArray())
    }

    private fun String.isHexAt(i: Int): Boolean =
        i + 1 < length && this[i].isHexDigitChar() && this[i + 1].isHexDigitChar()

    private fun Char.isHexDigitChar(): Boolean = this in '0'..'9' || this in 'A'..'F' || this in 'a'..'f'
}
