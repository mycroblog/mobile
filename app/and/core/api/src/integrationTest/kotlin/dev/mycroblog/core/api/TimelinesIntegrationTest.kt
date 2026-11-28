package dev.mycroblog.core.api

import dev.mycroblog.core.api.client.NetworkResult
import dev.mycroblog.core.api.client.TimelinesClient
import dev.mycroblog.core.api.models.StatusPage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** The generated client pages `GET /api/v1/timelines/local` of the local my (task my:up) by its encoded cursor. */
class TimelinesIntegrationTest {
    private fun NetworkResult<StatusPage>.page(what: String): StatusPage = when (this) {
        is NetworkResult.Success -> data
        is NetworkResult.Failure -> throw AssertionError("$what of ${LocalMy.url} failed: $error")
    }

    @Test
    fun theCursorOfAPageReadsTheNextOne() = runBlocking {
        HttpClient(CIO) { installApi(LocalMy.url) }.use { client ->
            val api = TimelinesClient(client)
            val all = api.getLocalTimeline(limit = 40).page("the local timeline").items.map { it.id.toString() }
            assertTrue("the local timeline of the local my has fewer than 3 posts", all.size >= 3)

            val paged = mutableListOf<String>()
            var cursor: String? = null
            do {
                val page = api.getLocalTimeline(limit = 2, cursor = cursor?.let(::queryValue)).page("a page")
                paged += page.items.map { it.id.toString() }
                cursor = page.nextCursor
            } while (cursor != null && paged.size < all.size)

            assertEquals(
                "pages of 2 by their cursors differ from one page of the local timeline",
                all,
                paged.take(all.size),
            )
        }
    }
}
