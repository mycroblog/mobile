package dev.mycroblog.core.model

/** A timeline that a server provides to everyone (ADR-0002 §6), by its name in the API. */
enum class PublicTimeline(val apiName: String) {
    LOCAL("local"),
    FEDERATED("federated"),
}

/** Who may read a timeline of a server (`InstanceAudience`). */
enum class TimelineAudience {
    OFF,
    SIGNED_IN,
    EVERYONE,

    /** A value this app does not know, which it treats as not offered. */
    UNKNOWN,
}

/**
 * The timelines offered to a reader who has not signed in (`shared/fixtures/public-timelines/cases.json`): each whose
 * audience is [TimelineAudience.EVERYONE], none on a [singleUser] server. In the order of [PublicTimeline].
 */
fun offeredPublicTimelines(
    local: TimelineAudience,
    federated: TimelineAudience,
    singleUser: Boolean,
): List<PublicTimeline> {
    if (singleUser) return emptyList()
    return buildList {
        if (local == TimelineAudience.EVERYONE) add(PublicTimeline.LOCAL)
        if (federated == TimelineAudience.EVERYONE) add(PublicTimeline.FEDERATED)
    }
}

/**
 * The context of a reader who has not signed in (ADR-0007 §3 with no account): the server at [origin]. The store keys
 * its records by it, so that the posts of two servers never mix.
 */
@JvmInline
value class ReaderContext(val origin: String)

/** The server a reader chose, and the timelines it offers them; [timelines] is empty until the instance is read. */
data class Reader(val server: ServerAddress, val timelines: List<PublicTimeline>) {
    val context: ReaderContext
        get() = ReaderContext(server.origin)
}
