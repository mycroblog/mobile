package dev.mycroblog.feature.timeline.impl

import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.core.model.TimelineFailure
import dev.mycroblog.core.model.TimelinePosition
import dev.mycroblog.core.model.TimelinePost

/** What a public timeline's screen shows, all of it read from the store and the state of its loads. */
data class PublicTimelineUiState(
    /** The server read, or null before one is chosen. */
    val server: ServerAddress? = null,
    /** The posts the store holds, newest first. */
    val posts: List<TimelinePost> = emptyList(),
    /** Whether the store has loaded the timeline at least once, so that no posts means it is empty. */
    val loaded: Boolean = false,
    /** Whether older posts can be loaded. */
    val hasOlder: Boolean = false,
    /** Whether the newest posts are being loaded, on open or by pull to refresh. */
    val refreshing: Boolean = false,
    /** Whether an older page is being loaded. */
    val loadingOlder: Boolean = false,
    /** Why the last refresh failed, until the next one starts. */
    val refreshFailure: TimelineFailure? = null,
    /** Why the last older page failed, until it is tried again. */
    val olderFailure: TimelineFailure? = null,
    /** Whether a 429 still forbids another request (its `Retry-After` has not passed). */
    val rateLimited: Boolean = false,
    /** The reading position to restore once, or null when there is none or it was restored. */
    val restore: TimelinePosition? = null,
    /** Whether the saved position was looked up (and restored, if any), so that the position may now be saved. */
    val positionReady: Boolean = false,
    /** The posts whose content warning the reader opened. */
    val openContent: Set<String> = emptySet(),
    /** The posts whose sensitive media the reader showed. */
    val shownMedia: Set<String> = emptySet(),
) {
    /** The first load is under way and nothing is stored yet. */
    val initialLoading: Boolean get() = posts.isEmpty() && (refreshing || !loaded) && refreshFailure == null

    /** The timeline was loaded and holds no posts. */
    val empty: Boolean get() = posts.isEmpty() && loaded && !refreshing && refreshFailure == null
}
