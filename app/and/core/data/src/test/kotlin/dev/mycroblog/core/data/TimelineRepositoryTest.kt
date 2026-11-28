package dev.mycroblog.core.data

import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.core.model.TimelinePosition
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * [TimelineRepository] reads a timeline and its reading position from the store, with no network (ADR-0008 §1–§3): a
 * relaunch shows the same posts and position before my answers.
 */
class TimelineRepositoryTest {
    @get:Rule
    val folder = TemporaryFolder()

    private val my = FakeMy()
    private val database = inMemoryStore()
    private val opened = mutableListOf<ReaderPreferences>()
    private val address = ServerAddress.parse("example.social")!!
    private val context = ReaderContext(address.origin)

    @After
    fun close() {
        runBlocking { opened.forEach { it.close() } }
        database.close()
    }

    private suspend fun readLocal(): List<String> {
        val preferences = ReaderPreferences(folder.newFolder()).also { opened += it }
        val launch = Launch(my.engine, database, preferences)
        launch.readers.choose(address)
        my.post("local", 7)
        launch.sync.refresh(context, PublicTimeline.LOCAL)
        return launch.timelines.posts(context, PublicTimeline.LOCAL).first().map { it.id }
    }

    @Test
    fun theStoredTimelineIsReadWithNoNetwork() = runBlocking {
        val stored = readLocal()
        assertTrue(stored.isNotEmpty())
        val relaunched = TimelineRepository(database)

        assertEquals(stored, relaunched.posts(context, PublicTimeline.LOCAL).first().map { it.id })
        assertTrue(relaunched.state(context, PublicTimeline.LOCAL).first().loaded)
    }

    @Test
    fun theReadingPositionIsRememberedPerTimeline() = runBlocking {
        val stored = readLocal()
        val position = TimelinePosition(anchorId = stored[2], offset = -48)
        TimelineRepository(database).savePosition(context, PublicTimeline.LOCAL, position)

        val relaunched = TimelineRepository(database)

        assertEquals(position, relaunched.position(context, PublicTimeline.LOCAL))
        assertNull(relaunched.position(context, PublicTimeline.FEDERATED))
    }

    @Test
    fun theReadingPositionIsKeyedByTheServer() = runBlocking {
        val stored = readLocal()
        TimelineRepository(database).savePosition(context, PublicTimeline.LOCAL, TimelinePosition(stored[0], 0))

        assertNull(TimelineRepository(database).position(ReaderContext("https://other.example"), PublicTimeline.LOCAL))
        assertEquals(
            emptyList<String>(),
            TimelineRepository(database).posts(ReaderContext("https://other.example"), PublicTimeline.LOCAL).first(),
        )
    }

    @Test
    fun aClearedPositionIsNone() = runBlocking {
        val stored = readLocal()
        val repository = TimelineRepository(database)
        repository.savePosition(context, PublicTimeline.LOCAL, TimelinePosition(stored[1], 3))
        repository.savePosition(context, PublicTimeline.LOCAL, null)

        assertNull(repository.position(context, PublicTimeline.LOCAL))
    }
}
