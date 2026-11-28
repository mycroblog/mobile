package dev.mycroblog.core.model

/** The outcome of loading a page of a timeline into the store. */
sealed interface TimelineLoad {
    /** The store holds the page. */
    data object Loaded : TimelineLoad

    /** Nothing was stored, for [failure]. */
    data class Failed(val failure: TimelineFailure) : TimelineLoad
}

/** Why a page of a timeline could not be loaded, as the screen tells the reader. */
sealed interface TimelineFailure {
    /** No answer came: the host is unreachable, the connection failed, or it timed out. */
    data object NoAnswer : TimelineFailure

    /** The server answered with something this app cannot read. */
    data object Unexpected : TimelineFailure

    /** The server answered with an RFC 9457 problem, whose title says why. */
    data class Problem(val title: String) : TimelineFailure

    /**
     * The server limits the rate of requests (429): try again no sooner than [retryAtMillis] (since the epoch), or
     * when the reader asks if the server said no time.
     */
    data class RateLimited(val retryAtMillis: Long?) : TimelineFailure

    /** The server does not offer the timeline (404); its destination is hidden. */
    data object NotOffered : TimelineFailure
}

/** Where a reader was in a timeline: the post at the top of the screen, and how far past it (ADR-0008 §3). */
data class TimelinePosition(val anchorId: String, val offset: Int)
