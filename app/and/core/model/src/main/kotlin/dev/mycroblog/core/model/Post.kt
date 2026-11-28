package dev.mycroblog.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account as the store keeps it (one record per (context, id), ADR-0008 §2) and as a post shows it: its display
 * name as runs, its handle, and its avatar.
 */
@Serializable
data class Account(
    val id: String,
    /** The display name, or the username when it is empty, or the domain when that is empty too. */
    val displayName: List<ContentRun>,
    /** `@` and `acct` (`@carol@remote.example` for a remote account), or null when `acct` is empty. */
    val handle: String?,
    /** The small avatar, or null when the account has none. */
    val avatar: String?,
    /** The custom emoji of the display name. */
    val emojis: List<CustomEmoji>,
    /** The account's page on its server. */
    val url: String?,
)

/** A kind of media attachment. A kind this app does not know is [UNKNOWN]. */
@Serializable
enum class MediaType {
    @SerialName("image")
    IMAGE,

    @SerialName("gifv")
    GIFV,

    @SerialName("video")
    VIDEO,

    @SerialName("audio")
    AUDIO,

    @SerialName("unknown")
    UNKNOWN,
}

/** A media attachment as a post shows it: its preview image, its BlurHash, and its description. */
@Serializable
data class Media(
    val type: MediaType,
    /** The description, the accessible label; null when the author gave none. */
    val description: String?,
    /** The server's preview image (`preview_url`), or null when it has none. */
    val preview: String?,
    val blurhash: String?,
    /** The width over the height of the original, or null when the server does not know it. */
    val aspectRatio: Float? = null,
)

@Serializable
data class PostCounts(val replies: Int, val boosts: Int, val favourites: Int)

/**
 * A post as the store keeps it, one record per (context, id) (ADR-0008 §2), its content parsed once when it was
 * stored. A boost is a record of its own whose [boostId] names the boosted post.
 */
@Serializable
data class PostRecord(
    val id: String,
    val accountId: String,
    val boostId: String?,
    /** When it was posted, in milliseconds since the epoch. */
    val createdAt: Long,
    /** The post's page on its server. */
    val url: String?,
    /** The content warning as runs, or null when it has none. */
    val contentWarning: List<ContentRun>?,
    val content: List<ContentBlock>,
    val sensitive: Boolean,
    val media: List<Media>,
    val omittedMediaCount: Int,
    val edited: Boolean,
    val counts: PostCounts,
    /** The custom emoji of the content and the content warning. */
    val emojis: List<CustomEmoji>,
    /** Whether the post has a part this app does not show yet: a poll, a quote, an article or an event board. */
    val hasUnshownContent: Boolean = false,
)

/**
 * A post of a timeline as it is shown (`shared/fixtures/timeline-page/expected.json`): for a boost, the boosted
 * [post] by its [author], with the booster as [boostedBy]. [id] is the timeline's own entry, the boost's for a boost.
 */
data class TimelinePost(val id: String, val boostedBy: Account?, val author: Account, val post: PostRecord) {
    val contentWarning: List<ContentRun>? get() = post.contentWarning
    val content: List<ContentBlock> get() = post.content

    /** A content warning hides the content until the reader opens it. */
    val contentHidden: Boolean get() = post.contentWarning != null

    /** A content warning or `sensitive` hides the media until the reader shows them. */
    val mediaHidden: Boolean get() = contentHidden || post.sensitive
    val media: List<Media> get() = post.media
    val omittedMediaCount: Int get() = post.omittedMediaCount
    val edited: Boolean get() = post.edited
    val counts: PostCounts get() = post.counts
    val createdAt: Long get() = post.createdAt

    /** Whether the post has a part this app does not show yet, which the reader can open on the server ([url]). */
    val hasUnshownContent: Boolean get() = post.hasUnshownContent

    /** The post's page on its server. */
    val url: String? get() = post.url
}
