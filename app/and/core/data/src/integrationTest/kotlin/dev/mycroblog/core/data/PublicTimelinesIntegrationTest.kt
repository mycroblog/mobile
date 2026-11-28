package dev.mycroblog.core.data

import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.core.model.TimelineLoad
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * A reader who has not signed in reads the public timelines of the local my (task my:up) with the engine of the app:
 * the destinations its instance offers, their posts in my's order, older pages, and a refresh.
 */
class PublicTimelinesIntegrationTest {
    @get:Rule
    val folder = TemporaryFolder()

    private val engine = OkHttp.create()
    private val database = inMemoryStore()
    private lateinit var preferences: ReaderPreferences
    private lateinit var launch: Launch

    @After
    fun close() {
        runBlocking { if (::preferences.isInitialized) preferences.close() }
        database.close()
        engine.close()
    }

    private suspend fun read(path: String): JsonObject = HttpClient(OkHttp).use { client ->
        Json.parseToJsonElement(client.get("${LocalMy.url}$path").bodyAsText()).jsonObject
    }

    private suspend fun chooseTheLocalMy(): ServerAddress {
        val address = ServerAddress.parse(LocalMy.url)
        assertNotNull("\"${LocalMy.url}\" is not a valid server address", address)
        preferences = ReaderPreferences(folder.newFolder())
        launch = Launch(engine, database, preferences)
        launch.readers.choose(address!!)
        return address
    }

    /** What the local my's instance offers a reader who has not signed in, read from its answer as it is. */
    private suspend fun offeredByTheInstance(): List<PublicTimeline> {
        val instance = read("/api/v1/instance")
        if (instance.getValue("single_user").jsonPrimitive.boolean) return emptyList()
        val timelines = instance.getValue("timelines").jsonObject
        return PublicTimeline.entries.filter { timelines[it.apiName]?.jsonPrimitive?.content == "everyone" }
    }

    @Test
    fun theDestinationsAreTheTimelinesTheInstanceOffers() = runBlocking {
        chooseTheLocalMy()
        assertEquals(offeredByTheInstance(), launch.readers.reader.first()?.timelines)
    }

    @Test
    fun anOfferedTimelineListsThePostsOfTheLocalMyInItsOrder() = runBlocking {
        chooseTheLocalMy()
        val context = launch.readers.reader.first()!!.context
        val offered = offeredByTheInstance()
        assertTrue("the local my offers no public timeline to a reader who has not signed in", offered.isNotEmpty())
        for (timeline in offered) {
            assertEquals("refreshing $timeline", TimelineLoad.Loaded, launch.sync.refresh(context, timeline))
            val stored = launch.timelines.posts(context, timeline).first().map { it.id }
            val newest = read("/api/v1/timelines/${timeline.apiName}").getValue("items").jsonArray
                .map { it.jsonObject.getValue("id").jsonPrimitive.content }
            assertTrue("the $timeline timeline of the local my has no post", newest.isNotEmpty())
            assertEquals(
                "the stored $timeline timeline does not begin with the newest posts of the local my",
                newest.take(stored.size),
                stored.take(newest.size),
            )
            assertEquals("the stored $timeline timeline repeats a post", stored.size, stored.toSet().size)
        }
    }

    @Test
    fun olderPagesAndARefreshKeepTheOrderOfTheLocalMy() = runBlocking {
        chooseTheLocalMy()
        val context = launch.readers.reader.first()!!.context
        for (timeline in offeredByTheInstance()) {
            launch.sync.refresh(context, timeline)
            val first = launch.timelines.posts(context, timeline).first().map { it.id }
            while (launch.timelines.state(context, timeline).first().hasOlder) {
                val before = launch.timelines.posts(context, timeline).first().size
                val load = launch.sync.loadOlder(context, timeline)
                assertEquals("an older page of $timeline", TimelineLoad.Loaded, load)
                val after = launch.timelines.posts(context, timeline).first().size
                assertNotEquals("an older page of $timeline added no post", before, after)
            }
            val all = launch.timelines.posts(context, timeline).first().map { it.id }
            assertEquals("the older pages of $timeline changed the newest ones", first, all.take(first.size))
            assertEquals("the stored $timeline timeline repeats a post", all.size, all.toSet().size)

            assertEquals("refreshing $timeline again", TimelineLoad.Loaded, launch.sync.refresh(context, timeline))
            assertEquals(
                "a refresh with no new post changed the stored $timeline timeline",
                all,
                launch.timelines.posts(context, timeline).first().map { it.id },
            )
        }
    }

    @Test
    fun aTimelineTheInstanceDoesNotOfferIsNotLoaded() = runBlocking {
        chooseTheLocalMy()
        val context = launch.readers.reader.first()!!.context
        for (timeline in PublicTimeline.entries - offeredByTheInstance().toSet()) {
            val load = launch.sync.refresh(context, timeline)
            assertTrue("$timeline, which the local my does not offer, loaded", load is TimelineLoad.Failed)
            assertEquals(emptyList<String>(), launch.timelines.posts(context, timeline).first())
        }
    }
}
