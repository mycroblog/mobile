package dev.mycroblog.core.data

import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.core.model.TimelineFailure
import dev.mycroblog.core.model.TimelineLoad
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import java.io.IOException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * [TimelineSync] keeps a public timeline in the store by the rule of ADR-0008 §3: a refresh reads from the newest page
 * until it meets the stored order, and replaces the stored order when 5 pages do not meet it; older pages follow the
 * stored order; the store is pruned to 1000 entries. It turns the failures of my into [TimelineFailure]s.
 */
class TimelineSyncTest {
    @get:Rule
    val folder = TemporaryFolder()

    private val my = FakeMy()
    private val database = inMemoryStore()
    private lateinit var preferences: ReaderPreferences
    private lateinit var launch: Launch
    private val address = ServerAddress.parse("example.social")!!
    private val context = ReaderContext(address.origin)
    private val local = PublicTimeline.LOCAL

    private fun <T> test(block: suspend () -> T): T = runBlocking {
        preferences = ReaderPreferences(folder.newFolder())
        launch = Launch(my.engine, database, preferences)
        launch.readers.choose(address)
        my.timelineRequests.clear()
        block()
    }

    @After
    fun close() {
        runBlocking { if (::preferences.isInitialized) preferences.close() }
        database.close()
    }

    private suspend fun stored(): List<String> = launch.timelines.posts(context, local).first().map { it.id }

    private fun ids(numbers: List<Int>): List<String> = numbers.map(FakeMy::id)

    private fun server(): List<String> = ids(my.timelines.getValue("local"))

    private suspend fun refresh(): TimelineLoad = launch.sync.refresh(context, local)

    private suspend fun loadOlder(): TimelineLoad = launch.sync.loadOlder(context, local)

    @Test
    fun aFirstRefreshStoresTheNewestPostsInTheServersOrder() = test {
        my.post("local", 20)
        assertEquals(TimelineLoad.Loaded, refresh())
        val stored = stored()
        assertTrue("the first refresh stored no post", stored.isNotEmpty())
        assertEquals("the stored order is not the newest posts of the server", server().take(stored.size), stored)
        assertEquals(null, my.timelineRequests.first().second)
    }

    @Test
    fun aRefreshThatMeetsTheStoredOrderWithinItsPagesKeepsIt() = test {
        my.post("local", 20)
        refresh()
        val before = stored()
        val added = ids(my.post("local", 4))
        my.timelineRequests.clear()

        assertEquals(TimelineLoad.Loaded, refresh())

        assertEquals("the refresh did not put the new posts above the stored ones", added + before, stored())
        assertEquals(
            "4 new posts in pages of 3 meet the stored order in the second page",
            2,
            my.timelineRequests.size,
        )
    }

    @Test
    fun aRefreshThatNeverMeetsTheStoredOrderReplacesIt() = test {
        my.post("local", 20)
        refresh()
        val before = stored()
        my.post("local", 30)
        my.timelineRequests.clear()

        assertEquals(TimelineLoad.Loaded, refresh())

        assertEquals("the refresh did not read 5 pages", 5, my.timelineRequests.size)
        val stored = stored()
        assertEquals("the refresh did not replace the stored order with the 5 newest pages", server().take(15), stored)
        assertTrue("posts of the replaced order are left in the timeline", stored.none { it in before })
    }

    @Test
    fun anOlderPageFollowsTheStoredOrder() = test {
        my.post("local", 20)
        refresh()
        val before = stored()

        assertEquals(TimelineLoad.Loaded, loadOlder())

        val stored = stored()
        assertEquals(
            "an older page did not follow the stored order",
            server().take(before.size + my.pageSize),
            stored,
        )
        val oldest = my.timelines.getValue("local")[before.size - 1]
        assertEquals(
            "the older page was not asked for by the cursor of the stored order",
            FakeMy.cursorAfter(oldest),
            my.timelineRequests.last().second,
        )
    }

    @Test
    fun anOlderPageAfterARefreshThatMetFollowsTheKeptOrder() = test {
        my.post("local", 20)
        refresh()
        val before = stored()
        val added = ids(my.post("local", 4))
        refresh()

        assertEquals(TimelineLoad.Loaded, loadOlder())

        assertEquals(server().take(added.size + before.size + my.pageSize), stored())
    }

    @Test
    fun anOlderPageAfterAReplaceFollowsTheNewestPages() = test {
        my.post("local", 20)
        refresh()
        my.post("local", 30)
        refresh()

        assertEquals(TimelineLoad.Loaded, loadOlder())

        assertEquals(server().take(15 + my.pageSize), stored())
    }

    @Test
    fun theOldestPageLeavesNoOlderOne() = test {
        my.post("local", 2)
        refresh()
        assertEquals(server(), stored())
        val state = launch.timelines.state(context, local).first()
        assertFalse("a timeline of one page says it has older posts", state.hasOlder)
        assertTrue("a refreshed timeline does not say it is loaded", state.loaded)
    }

    @Test
    fun aTimelineWithMorePagesHasOlderOnes() = test {
        my.post("local", 20)
        refresh()
        assertTrue(launch.timelines.state(context, local).first().hasOlder)
    }

    @Test
    fun pruningKeepsTheNewest1000Entries() = test {
        my.pageSize = 250
        my.post("local", 1300)
        refresh()
        while (stored().size < 1300 && launch.timelines.state(context, local).first().hasOlder) {
            assertEquals(TimelineLoad.Loaded, loadOlder())
        }
        assertEquals(server(), stored())

        launch.sync.prune()

        assertEquals("pruning did not keep the newest 1000 entries", server().take(1000), stored())
    }

    @Test
    fun aFailedRefreshKeepsTheStoredOrder() = test {
        my.post("local", 20)
        refresh()
        val before = stored()
        my.post("local", 4)
        my.answer = { throw IOException("reset") }

        assertEquals(TimelineLoad.Failed(TimelineFailure.NoAnswer), refresh())
        assertEquals(before, stored())
    }

    @Test
    fun aTimelineThatIsNotFoundIsNotOfferedAndItsDestinationIsHidden() = test {
        assertEquals(listOf(PublicTimeline.LOCAL, PublicTimeline.FEDERATED), launch.readers.reader.first()!!.timelines)
        my.answer = {
            respond(
                """{"type":"about:blank","title":"Not Found","status":404}""",
                HttpStatusCode.NotFound,
                headersOf(HttpHeaders.ContentType, "application/problem+json"),
            )
        }

        assertEquals(TimelineLoad.Failed(TimelineFailure.NotOffered), refresh())
        assertEquals(listOf(PublicTimeline.FEDERATED), launch.readers.reader.first()!!.timelines)
    }

    /** A 429 of the rate limit, with [retryAfter] as its Retry-After when there is one. */
    private fun rateLimited(
        retryAfter: String?,
    ): suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData = {
        val headers = listOfNotNull(
            HttpHeaders.ContentType to listOf("application/problem+json"),
            retryAfter?.let { HttpHeaders.RetryAfter to listOf(it) },
        )
        respond(
            """{"type":"about:blank","title":"Too Many Requests","status":429}""",
            HttpStatusCode.TooManyRequests,
            headersOf(*headers.toTypedArray()),
        )
    }

    @Test
    fun aRateLimitRetriesAfterItsSeconds() = test {
        my.post("local", 3)
        my.answer = rateLimited("120")
        val before = System.currentTimeMillis()
        val load = refresh()
        val after = System.currentTimeMillis()

        val failure = (load as? TimelineLoad.Failed)?.failure as? TimelineFailure.RateLimited
        assertTrue("a 429 with Retry-After: 120 is $load", failure != null)
        val at = failure!!.retryAtMillis
        assertTrue(
            "Retry-After: 120 at $before..$after is retried at $at",
            at != null && at in before + 120_000..after + 120_000,
        )
    }

    @Test
    fun aRateLimitRetriesAtItsDate() = test {
        my.post("local", 3)
        val date = ZonedDateTime.of(2036, 10, 21, 7, 28, 0, 0, ZoneOffset.UTC)
        my.answer = rateLimited(DateTimeFormatter.RFC_1123_DATE_TIME.format(date))

        assertEquals(
            TimelineLoad.Failed(TimelineFailure.RateLimited(date.toInstant().toEpochMilli())),
            refresh(),
        )
    }

    @Test
    fun aRateLimitWithoutRetryAfterHasNoTime() = test {
        my.post("local", 3)
        my.answer = rateLimited(null)

        assertEquals(TimelineLoad.Failed(TimelineFailure.RateLimited(null)), refresh())
    }

    @Test
    fun anOlderPageThatIsRateLimitedKeepsTheStoredOrder() = test {
        my.post("local", 20)
        refresh()
        val before = stored()
        my.answer = rateLimited("30")

        val load = loadOlder()

        val failure = (load as? TimelineLoad.Failed)?.failure
        assertTrue("a rate-limited older page is $load", failure is TimelineFailure.RateLimited)
        assertEquals(before, stored())
    }

    @Test
    fun aProblemIsItsTitle() = test {
        my.answer = {
            respond(
                """{"type":"about:blank","title":"Service Unavailable","status":503}""",
                HttpStatusCode.ServiceUnavailable,
                headersOf(HttpHeaders.ContentType, "application/problem+json"),
            )
        }
        assertEquals(TimelineLoad.Failed(TimelineFailure.Problem("Service Unavailable")), refresh())
    }
}
