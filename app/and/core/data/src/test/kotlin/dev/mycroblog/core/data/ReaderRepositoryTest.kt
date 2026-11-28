package dev.mycroblog.core.data

import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ServerAddress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * [ReaderRepository] remembers the server a reader chose across launches, and offers the public timelines its
 * instance allows a reader who has not signed in (shared/fixtures/public-timelines/cases.json).
 */
class ReaderRepositoryTest {
    @get:Rule
    val folder = TemporaryFolder()

    private val my = FakeMy()
    private val open = mutableListOf<ReaderPreferences>()
    private val address = ServerAddress.parse("example.social")!!

    private fun launch(directory: java.io.File): ReaderRepository {
        val preferences = ReaderPreferences(directory)
        open += preferences
        return ReaderRepository(my.engine, preferences.store)
    }

    /** Ends every open launch the way the app's process ends, so that the next one reads the same file. */
    private suspend fun end() {
        open.forEach { it.close() }
        open.clear()
    }

    @After
    fun closeAll() = runBlocking { end() }

    @Test
    fun noServerIsChosenAtFirst() = runBlocking {
        assertNull(launch(folder.newFolder()).reader.first())
    }

    @Test
    fun theChosenServerIsRememberedAcrossLaunches() = runBlocking {
        val directory = folder.newFolder()
        launch(directory).choose(address)
        end()

        val reader = launch(directory).reader.first()
        assertEquals(address.origin, reader?.server?.origin)
        assertEquals(address.origin, reader?.context?.origin)
        assertEquals(listOf(PublicTimeline.LOCAL, PublicTimeline.FEDERATED), reader?.timelines)
    }

    @Test
    fun theOriginIsRememberedWhenTheInstanceDoesNotAnswer() = runBlocking {
        val directory = folder.newFolder()
        my.instanceAnswers = false
        launch(directory).choose(address)
        end()

        assertEquals(address.origin, launch(directory).reader.first()?.server?.origin)
    }

    @Test
    fun choosingAnotherServerReplacesIt() = runBlocking {
        val directory = folder.newFolder()
        val readers = launch(directory)
        readers.choose(address)
        val other = ServerAddress.parse("other.example")!!
        readers.choose(other)
        end()

        assertEquals(other.origin, launch(directory).reader.first()?.server?.origin)
    }

    @Test
    fun theOfferedTimelinesFollowEveryCaseOfTheSharedFixture() = runBlocking {
        val cases = Json.parseToJsonElement(Fixtures.read("public-timelines/cases.json")).jsonObject
            .getValue("cases").jsonArray.map { it.jsonObject }
        val failures = cases.mapNotNull { case ->
            val timelines = case.getValue("timelines").jsonObject
            my.audiences["local"] = timelines.getValue("local").jsonPrimitive.content
            my.audiences["federated"] = timelines.getValue("federated").jsonPrimitive.content
            my.singleUser = case.getValue("single_user").jsonPrimitive.boolean
            val readers = launch(folder.newFolder())
            readers.choose(address)
            val offered = readers.reader.first()?.timelines
            end()
            val expected = case.getValue("offered").jsonArray.map { name ->
                PublicTimeline.entries.single { it.apiName == name.jsonPrimitive.content }
            }
            if (offered == expected) null else "$case: expected $expected, but offered $offered"
        }
        assertEquals(
            "the cases of public-timelines/cases.json offered otherwise:\n" + failures.joinToString("\n"),
            0,
            failures.size,
        )
    }

    @Test
    fun refreshingTheInstanceFollowsWhatItNowAllows() = runBlocking {
        val readers = launch(folder.newFolder())
        readers.choose(address)
        my.audiences["federated"] = "signed_in"

        readers.refreshInstance()

        assertEquals(listOf(PublicTimeline.LOCAL), readers.reader.first()?.timelines)
    }

    @Test
    fun aHiddenTimelineStaysHiddenAcrossLaunches() = runBlocking {
        val directory = folder.newFolder()
        val readers = launch(directory)
        readers.choose(address)
        readers.hide(readers.reader.first()!!.context, PublicTimeline.FEDERATED)
        assertEquals(listOf(PublicTimeline.LOCAL), readers.reader.first()?.timelines)
        end()

        assertEquals(listOf(PublicTimeline.LOCAL), launch(directory).reader.first()?.timelines)
    }
}
