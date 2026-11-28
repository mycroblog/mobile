package dev.mycroblog.core.data

import dev.mycroblog.core.data.store.StoreDatabase
import dev.mycroblog.core.data.store.TimelineRow
import dev.mycroblog.core.model.Account
import dev.mycroblog.core.model.PostRecord
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.TimelinePosition
import dev.mycroblog.core.model.TimelinePost
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/** What a timeline's screen reads from the store (ADR-0008 §1); [TimelineSync] writes it. */
@Singleton
class TimelineRepository @Inject constructor(database: StoreDatabase) {
    private val dao = database.timelines()
    private val posts = DecodedCache(PostRecord.serializer())
    private val accounts = DecodedCache(Account.serializer())

    /** The posts of [timeline] in [context], newest first, as the store holds them now and whenever it changes. */
    fun posts(context: ReaderContext, timeline: PublicTimeline): Flow<List<TimelinePost>> =
        dao.rows(context.origin, timeline.apiName)
            .map { rows -> rows.mapNotNull { it.toPost() } }
            .flowOn(Dispatchers.Default)

    /** Whether [timeline] in [context] was loaded, and whether it has older posts to load. */
    fun state(context: ReaderContext, timeline: PublicTimeline): Flow<StoredTimeline> =
        dao.observeTimeline(context.origin, timeline.apiName)
            .map { StoredTimeline(loaded = it?.loaded == true, hasOlder = it?.nextCursor != null) }
            .distinctUntilChanged()

    /** The saved reading position of [timeline] in [context], or null when none was saved. */
    suspend fun position(context: ReaderContext, timeline: PublicTimeline): TimelinePosition? {
        val state = dao.timeline(context.origin, timeline.apiName) ?: return null
        return state.anchorId?.let { TimelinePosition(it, state.anchorOffset) }
    }

    /** Saves the reading position of [timeline] in [context], once it was loaded; null forgets it. */
    suspend fun savePosition(context: ReaderContext, timeline: PublicTimeline, position: TimelinePosition?) {
        dao.updatePosition(context.origin, timeline.apiName, position?.anchorId, position?.offset ?: 0)
    }

    private fun TimelineRow.toPost(): TimelinePost? {
        val entry = posts.decode(post) ?: return null
        val author = accounts.decode(account) ?: return null
        val boosted = boost?.let(posts::decode)
        val boostAuthor = boostAccount?.let(accounts::decode)
        return timelinePost(entry, author, boosted) { boostAuthor?.takeIf { a -> a.id == it } }
    }
}

/** What the store holds of a timeline besides its posts. */
data class StoredTimeline(val loaded: Boolean, val hasOlder: Boolean)

/** The JSON of the records in the store. */
internal fun storeJson(): Json = Json { ignoreUnknownKeys = true }

/**
 * The records decoded from their JSON, kept by the JSON, so that a timeline that the store emits again decodes only
 * the records that changed.
 */
private class DecodedCache<T : Any>(private val serializer: KSerializer<T>) {
    private val json = storeJson()
    private val decoded = object : LinkedHashMap<String, T>(MAX, LOAD_FACTOR, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, T>?) = size > MAX
    }

    fun decode(data: String): T? = synchronized(decoded) { decoded[data] } ?: try {
        json.decodeFromString(serializer, data).also { synchronized(decoded) { decoded[data] = it } }
    } catch (_: SerializationException) {
        null
    } catch (_: IllegalArgumentException) {
        null
    }

    private companion object {
        const val MAX = 2000
        const val LOAD_FACTOR = 0.75f
    }
}
