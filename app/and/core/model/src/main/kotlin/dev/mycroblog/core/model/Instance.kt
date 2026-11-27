package dev.mycroblog.core.model

/** What the app shows of a server's instance (`GET /api/v1/instance`). */
data class Instance(
    /** The display name of the instance; the server gives its domain when it has none. */
    val name: String,
    /** A one-paragraph summary as plain text, or null when the instance has none. */
    val shortDescription: String?,
)

/** The outcome of looking up the instance of a server. */
sealed interface InstanceLookup {
    /** The server is a mycroblog server, and this is its instance. */
    data class Found(val instance: Instance) : InstanceLookup

    /** The instance could not be read, for [failure]. */
    data class Failed(val failure: InstanceLookupFailure) : InstanceLookup
}

/** Why the instance of a server could not be read, as the screen tells the user. */
sealed interface InstanceLookupFailure {
    /** No answer came: the host is unknown or unreachable, the connection failed, or it timed out. */
    data object NoAnswer : InstanceLookupFailure

    /** The server answered, but not as a mycroblog server does. */
    data object NotMycroblog : InstanceLookupFailure

    /** The server answered with an RFC 9457 problem, whose title says why. */
    data class Problem(val title: String) : InstanceLookupFailure
}
