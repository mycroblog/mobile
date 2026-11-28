package dev.mycroblog.core.data.store

import androidx.room3.ColumnInfo
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.RoomDatabase
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * The store (ADR-0008): one record per post and per account, keyed by (context, id) (§2), the order of each timeline,
 * and the screen state of each timeline kept apart from the records. A record's [PostEntity.data] is the JSON of its
 * domain record. Everything here can be fetched again; the database is a cache that may be thrown away.
 */
@Database(
    entities = [
        PostEntity::class,
        AccountEntity::class,
        TimelineEntryEntity::class,
        TimelineEntity::class,
        PostStateEntity::class,
    ],
    version = 2,
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun timelines(): TimelineDao
}

/** A post of a context: `PostRecord` as JSON, with the ids the timeline query joins on. */
@Entity(tableName = "posts", primaryKeys = ["context", "id"])
data class PostEntity(
    val context: String,
    val id: String,
    @ColumnInfo(name = "account_id") val accountId: String,
    @ColumnInfo(name = "boost_id") val boostId: String?,
    val data: String,
)

/** An account of a context: `Account` as JSON. */
@Entity(tableName = "accounts", primaryKeys = ["context", "id"])
data class AccountEntity(val context: String, val id: String, val data: String)

/** The post at [position] (0 the newest) of a timeline of a context. */
@Entity(
    tableName = "timeline_entries",
    primaryKeys = ["context", "timeline", "position"],
    indices = [Index("context", "post_id")],
)
data class TimelineEntryEntity(
    val context: String,
    val timeline: String,
    val position: Int,
    @ColumnInfo(name = "post_id") val postId: String,
)

/**
 * The state of a timeline of a context: the cursor of its next older page (null at its end or before the first
 * load), and the reading position as an anchor post and the offset from it (ADR-0008 §3).
 */
@Entity(tableName = "timelines", primaryKeys = ["context", "timeline"])
data class TimelineEntity(
    val context: String,
    val timeline: String,
    @ColumnInfo(name = "loaded") val loaded: Boolean,
    @ColumnInfo(name = "next_cursor") val nextCursor: String?,
    @ColumnInfo(name = "anchor_id") val anchorId: String?,
    @ColumnInfo(name = "anchor_offset") val anchorOffset: Int,
)

/**
 * What the reader opened of a post of a context: its content warning ([contentOpen]) and its sensitive media
 * ([mediaShown]). Screen state, kept apart from the post's record (ADR-0008 §2), so that fetching the post again keeps
 * it.
 */
@Entity(tableName = "post_states", primaryKeys = ["context", "post_id"])
data class PostStateEntity(
    val context: String,
    @ColumnInfo(name = "post_id") val postId: String,
    @ColumnInfo(name = "content_open") val contentOpen: Boolean,
    @ColumnInfo(name = "media_shown") val mediaShown: Boolean,
)

/** A row of a timeline: its entry's post and author, and for a boost the boosted post and its author. */
data class TimelineRow(
    val post: String,
    val account: String,
    val boost: String?,
    @ColumnInfo(name = "boost_account") val boostAccount: String?,
)

@Dao
abstract class TimelineDao {
    /** The rows of a timeline, newest first; a row whose records are missing is left out. */
    @Query(
        """
        SELECT p.data AS post, a.data AS account, b.data AS boost, ba.data AS boost_account
        FROM timeline_entries e
        JOIN posts p ON p.context = e.context AND p.id = e.post_id
        JOIN accounts a ON a.context = p.context AND a.id = p.account_id
        LEFT JOIN posts b ON b.context = p.context AND b.id = p.boost_id
        LEFT JOIN accounts ba ON ba.context = b.context AND ba.id = b.account_id
        WHERE e.context = :context AND e.timeline = :timeline
        ORDER BY e.position
        """,
    )
    abstract fun rows(context: String, timeline: String): Flow<List<TimelineRow>>

    @Query("SELECT post_id FROM timeline_entries WHERE context = :context AND timeline = :timeline ORDER BY position")
    abstract suspend fun ids(context: String, timeline: String): List<String>

    @Query("SELECT * FROM timelines WHERE context = :context AND timeline = :timeline")
    abstract suspend fun timeline(context: String, timeline: String): TimelineEntity?

    @Query("SELECT * FROM timelines WHERE context = :context AND timeline = :timeline")
    abstract fun observeTimeline(context: String, timeline: String): Flow<TimelineEntity?>

    // A record replaces the one of its key whole (ADR-0008 §2). INSERT OR REPLACE, rather than @Upsert, which tells a
    // conflict from another failure by the message of the driver's exception, and that message can be missing.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertAccounts(accounts: List<AccountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertTimeline(timeline: TimelineEntity)

    @Query("SELECT * FROM post_states WHERE context = :context")
    abstract fun postStates(context: String): Flow<List<PostStateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertPostState(state: PostStateEntity)

    @Insert
    abstract suspend fun insertEntries(entries: List<TimelineEntryEntity>)

    @Query("DELETE FROM timeline_entries WHERE context = :context AND timeline = :timeline")
    abstract suspend fun deleteEntries(context: String, timeline: String)

    @Query(
        "UPDATE timelines SET anchor_id = :anchorId, anchor_offset = :offset " +
            "WHERE context = :context AND timeline = :timeline",
    )
    abstract suspend fun updatePosition(context: String, timeline: String, anchorId: String?, offset: Int)

    /**
     * Writes a page's records and the timeline's new order and state at once, so that a screen never sees the order
     * without its posts.
     */
    @Transaction
    open suspend fun write(
        posts: List<PostEntity>,
        accounts: List<AccountEntity>,
        context: String,
        timeline: String,
        ids: List<String>,
        nextCursor: String?,
    ) {
        upsertAccounts(accounts)
        upsertPosts(posts)
        deleteEntries(context, timeline)
        insertEntries(ids.mapIndexed { index, id -> TimelineEntryEntity(context, timeline, index, id) })
        val current = timeline(context, timeline)
        upsertTimeline(
            TimelineEntity(
                context = context,
                timeline = timeline,
                loaded = true,
                nextCursor = nextCursor,
                anchorId = current?.anchorId,
                anchorOffset = current?.anchorOffset ?: 0,
            ),
        )
    }

    /** Cuts every timeline to its newest [max] entries, then drops the records no timeline holds (ADR-0008 §3). */
    @Transaction
    open suspend fun prune(max: Int) {
        deleteEntriesFrom(max)
        deleteUnheldPosts()
        deleteUnheldAccounts()
        deleteUnheldPostStates()
    }

    @Query(
        """
        DELETE FROM post_states WHERE NOT EXISTS (
            SELECT 1 FROM posts p WHERE p.context = post_states.context AND p.id = post_states.post_id
        )
        """,
    )
    abstract suspend fun deleteUnheldPostStates()

    @Query("DELETE FROM timeline_entries WHERE position >= :max")
    abstract suspend fun deleteEntriesFrom(max: Int)

    @Query(
        """
        DELETE FROM posts WHERE NOT EXISTS (
            SELECT 1 FROM timeline_entries e WHERE e.context = posts.context AND e.post_id = posts.id
        ) AND NOT EXISTS (
            SELECT 1 FROM timeline_entries e JOIN posts w ON w.context = e.context AND w.id = e.post_id
            WHERE w.context = posts.context AND w.boost_id = posts.id
        )
        """,
    )
    abstract suspend fun deleteUnheldPosts()

    @Query(
        """
        DELETE FROM accounts WHERE NOT EXISTS (
            SELECT 1 FROM posts p WHERE p.context = accounts.context AND p.account_id = accounts.id
        )
        """,
    )
    abstract suspend fun deleteUnheldAccounts()
}
