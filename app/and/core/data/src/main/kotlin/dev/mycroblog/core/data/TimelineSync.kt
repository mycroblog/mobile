package dev.mycroblog.core.data

import dev.mycroblog.core.api.RetryAfterRecorder
import dev.mycroblog.core.api.client.NetworkError
import dev.mycroblog.core.api.client.NetworkResult
import dev.mycroblog.core.api.client.TimelinesClient
import dev.mycroblog.core.api.models.Status
import dev.mycroblog.core.api.models.StatusPage
import dev.mycroblog.core.api.problem
import dev.mycroblog.core.api.queryValue
import dev.mycroblog.core.data.store.AccountEntity
import dev.mycroblog.core.data.store.PostEntity
import dev.mycroblog.core.data.store.StoreDatabase
import dev.mycroblog.core.model.Account
import dev.mycroblog.core.model.PostRecord
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.TimelineFailure
import dev.mycroblog.core.model.TimelineLoad
import io.ktor.client.engine.HttpClientEngine
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * The one component that writes the timelines of a reader context into the store (ADR-0008 §1). Screens read what it
 * wrote through [TimelineRepository].
 */
@Singleton
class TimelineSync @Inject constructor(
    private val engine: HttpClientEngine,
    private val database: StoreDatabase,
    private val readers: ReaderRepository,
) {
    private val dao = database.timelines()
    private val writing = Mutex()

    /**
     * Reads the newest page and follows its cursor until it meets the stored order, up to [MAX_PAGES] pages; the new
     * posts go on top of the stored ones. If it does not meet them, the stored order is replaced (ADR-0008 §3). With
     * nothing stored, it reads the newest page only.
     */
    suspend fun refresh(context: ReaderContext, timeline: PublicTimeline): TimelineLoad = writing.withLock {
        guarded { refreshLocked(context, timeline) }
    }

    private suspend fun refreshLocked(context: ReaderContext, timeline: PublicTimeline): TimelineLoad {
        val stored = dao.ids(context.origin, timeline.apiName)
        // With nothing stored there is nothing to meet: the newest page is the timeline, and older ones load as the
        // reader scrolls, so that opening a timeline costs one request of the server's rate limit.
        if (stored.isEmpty()) return loadNewestPage(context, timeline)
        val storedIds = stored.toHashSet()
        val statuses = mutableListOf<Status>()
        var cursor: String? = null
        var pages = 0
        var met = -1
        var end = false
        while (pages < MAX_PAGES) {
            val page = when (val result = fetch(context, timeline, cursor)) {
                is Fetched.Page -> result.page
                is Fetched.Failure -> return fail(context, timeline, result.failure)
            }
            pages++
            val meets = page.items.indexOfFirst { it.id.toString() in storedIds }
            if (meets >= 0) {
                statuses += page.items.subList(0, meets)
                met = stored.indexOf(page.items[meets].id.toString())
                break
            }
            statuses += page.items
            cursor = page.nextCursor
            if (cursor == null) {
                end = true
                break
            }
        }
        val newIds = statuses.map { it.id.toString() }
        val (ids, next) = when {
            met >= 0 -> (newIds + stored.subList(met, stored.size)).distinct() to
                dao.timeline(context.origin, timeline.apiName)?.nextCursor

            end -> newIds.distinct() to null

            else -> newIds.distinct() to cursor
        }
        write(context, timeline, statuses, ids, next)
        return TimelineLoad.Loaded
    }

    private suspend fun loadNewestPage(context: ReaderContext, timeline: PublicTimeline): TimelineLoad {
        val page = when (val result = fetch(context, timeline, cursor = null)) {
            is Fetched.Page -> result.page
            is Fetched.Failure -> return fail(context, timeline, result.failure)
        }
        write(context, timeline, page.items, page.items.map { it.id.toString() }.distinct(), page.nextCursor)
        return TimelineLoad.Loaded
    }

    /** Appends the next older page to the stored order; at the end of the timeline it does nothing. */
    suspend fun loadOlder(context: ReaderContext, timeline: PublicTimeline): TimelineLoad = writing.withLock {
        guarded { loadOlderLocked(context, timeline) }
    }

    private suspend fun loadOlderLocked(context: ReaderContext, timeline: PublicTimeline): TimelineLoad {
        val state = dao.timeline(context.origin, timeline.apiName)
        if (state == null || !state.loaded) return refreshLocked(context, timeline)
        val cursor = state.nextCursor ?: return TimelineLoad.Loaded
        val page = when (val result = fetch(context, timeline, cursor)) {
            is Fetched.Page -> result.page
            is Fetched.Failure -> return fail(context, timeline, result.failure)
        }
        val stored = dao.ids(context.origin, timeline.apiName)
        val ids = (stored + page.items.map { it.id.toString() }).distinct()
        write(context, timeline, page.items, ids, page.nextCursor)
        return TimelineLoad.Loaded
    }

    /**
     * Cuts every timeline to its newest [MAX_ENTRIES] posts and drops the records none holds, as at launch. A store
     * that fails is left as it is, to be pruned at the next launch.
     */
    suspend fun prune() {
        writing.withLock { guarded { dao.prune(MAX_ENTRIES).let { TimelineLoad.Loaded } } }
    }

    /**
     * The outcome of [block], or [TimelineFailure.Unexpected] when the store throws: a failure of the store never
     * escapes to a screen. Cancellation still propagates.
     */
    private inline fun guarded(block: () -> TimelineLoad): TimelineLoad = try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        TimelineLoad.Failed(TimelineFailure.Unexpected)
    }

    private suspend fun fail(context: ReaderContext, timeline: PublicTimeline, failure: TimelineFailure): TimelineLoad {
        if (failure == TimelineFailure.NotOffered) readers.hide(context, timeline)
        return TimelineLoad.Failed(failure)
    }

    private suspend fun write(
        context: ReaderContext,
        timeline: PublicTimeline,
        statuses: List<Status>,
        ids: List<String>,
        nextCursor: String?,
    ) {
        val (posts, accounts) = withContext(Dispatchers.Default) {
            val records = statuses.map { it.toRecords() }
            val posts = records.flatMap { it.posts }.map { it.toEntity(context) }
            val accounts = records.flatMap { it.accounts }.distinctBy { it.id }.map { it.toEntity(context) }
            posts to accounts
        }
        dao.write(posts, accounts, context.origin, timeline.apiName, ids, nextCursor)
    }

    private suspend fun fetch(context: ReaderContext, timeline: PublicTimeline, cursor: String?): Fetched {
        val retryAfter = RetryAfterRecorder()
        val result = apiClient(engine, context.origin, retryAfter).use { client ->
            val api = TimelinesClient(client)
            val encoded = cursor?.let(::queryValue)
            when (timeline) {
                PublicTimeline.LOCAL -> api.getLocalTimeline(cursor = encoded)
                PublicTimeline.FEDERATED -> api.getFederatedTimeline(cursor = encoded)
            }
        }
        return when (result) {
            is NetworkResult.Success -> Fetched.Page(result.data)
            is NetworkResult.Failure -> Fetched.Failure(result.error.toFailure(retryAfter.value))
        }
    }

    private sealed interface Fetched {
        class Page(val page: StatusPage) : Fetched

        class Failure(val failure: TimelineFailure) : Fetched
    }

    companion object {
        /** The most pages a refresh follows to meet the stored order (ADR-0008 §3). */
        const val MAX_PAGES = 5

        /** The most posts the store keeps of a timeline after a launch (ADR-0008 §3). */
        const val MAX_ENTRIES = 1000
    }
}

private fun NetworkError.toFailure(retryAfter: String?): TimelineFailure = when (this) {
    is NetworkError.Http -> when (statusCode) {
        HTTP_NOT_FOUND -> TimelineFailure.NotOffered

        HTTP_TOO_MANY_REQUESTS -> TimelineFailure.RateLimited(
            retryAfter?.let { retryAtMillis(it, System.currentTimeMillis()) },
        )

        else -> problem()?.let { TimelineFailure.Problem(it.title) } ?: TimelineFailure.Unexpected
    }

    is NetworkError.Serialization -> TimelineFailure.Unexpected

    is NetworkError.Network, is NetworkError.Unknown -> TimelineFailure.NoAnswer
}

private const val HTTP_NOT_FOUND = 404
private const val HTTP_TOO_MANY_REQUESTS = 429

/**
 * When a `Retry-After` of [value] says to try again, in milliseconds since the epoch, from [nowMillis]: a number of
 * seconds or an HTTP date (RFC 9110 §10.2.3); null when it is neither.
 */
internal fun retryAtMillis(value: String, nowMillis: Long): Long? {
    val trimmed = value.trim()
    trimmed.toLongOrNull()?.let { seconds -> return if (seconds < 0) null else nowMillis + seconds * MILLIS }
    return try {
        ZonedDateTime.parse(trimmed, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli()
    } catch (_: DateTimeParseException) {
        null
    }
}

private const val MILLIS = 1000L

private val StoreJson = storeJson()

private fun PostRecord.toEntity(context: ReaderContext) = PostEntity(
    context = context.origin,
    id = id,
    accountId = accountId,
    boostId = boostId,
    data = StoreJson.encodeToString(PostRecord.serializer(), this),
)

private fun Account.toEntity(context: ReaderContext) =
    AccountEntity(context.origin, id, StoreJson.encodeToString(Account.serializer(), this))
