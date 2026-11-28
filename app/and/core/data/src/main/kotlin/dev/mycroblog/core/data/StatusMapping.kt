package dev.mycroblog.core.data

import dev.mycroblog.core.api.models.Account as ApiAccount
import dev.mycroblog.core.api.models.CustomEmoji as ApiCustomEmoji
import dev.mycroblog.core.api.models.MediaAttachment
import dev.mycroblog.core.api.models.MediaAttachmentType
import dev.mycroblog.core.api.models.Status
import dev.mycroblog.core.model.Account
import dev.mycroblog.core.model.CustomEmoji
import dev.mycroblog.core.model.Media
import dev.mycroblog.core.model.MediaType
import dev.mycroblog.core.model.PostContentParser
import dev.mycroblog.core.model.PostCounts
import dev.mycroblog.core.model.PostRecord
import dev.mycroblog.core.model.TimelinePost

/** The records the store keeps of a status: its own and, for a boost, the boosted one's, with their accounts. */
internal class StatusRecords(val posts: List<PostRecord>, val accounts: List<Account>)

/** The records of this status, its content parsed (once, here, off the main thread by the caller). */
internal fun Status.toRecords(): StatusRecords {
    val boosted = boost
    val posts = listOfNotNull(toPostRecord(), boosted?.toPostRecord())
    val accounts = listOfNotNull(account.toAccount(), boosted?.account?.toAccount()).distinctBy { it.id }
    return StatusRecords(posts, accounts)
}

/** The status as a timeline shows it, from its records as the store would keep them. */
internal fun Status.toTimelinePost(): TimelinePost {
    val records = toRecords()
    val accounts = records.accounts.associateBy { it.id }
    val entry = records.posts.first()
    val boosted = records.posts.getOrNull(1)
    return checkNotNull(timelinePost(entry, accounts.getValue(entry.accountId), boosted, accounts::get))
}

/**
 * The timeline post of the stored [entry] by [entryAuthor]: for a boost, the [boosted] post by its author (as
 * [authorOf] gives it), with [entryAuthor] as the booster. Null when the boosted post is missing.
 */
internal fun timelinePost(
    entry: PostRecord,
    entryAuthor: Account,
    boosted: PostRecord?,
    authorOf: (String) -> Account?,
): TimelinePost? {
    if (entry.boostId == null) return TimelinePost(entry.id, boostedBy = null, author = entryAuthor, post = entry)
    if (boosted == null) return null
    val author = authorOf(boosted.accountId) ?: return null
    return TimelinePost(entry.id, boostedBy = entryAuthor, author = author, post = boosted)
}

internal fun ApiAccount.toAccount(): Account {
    val emojis = emojis.map { it.toModel() }
    val name = displayName.ifEmpty { username }.ifEmpty { domain }
    return Account(
        id = id.toString(),
        displayName = PostContentParser.parsePlainRuns(name, emojis),
        handle = acct.takeIf { it.isNotEmpty() }?.let { "@$it" },
        avatar = avatar?.small?.url?.toString(),
        emojis = emojis,
        url = url.toString(),
    )
}

private fun Status.toPostRecord(): PostRecord {
    val emojis = emojis.map { it.toModel() }
    return PostRecord(
        id = id.toString(),
        accountId = account.id.toString(),
        boostId = boost?.id?.toString(),
        createdAt = createdAt.toEpochMilliseconds(),
        url = url?.toString(),
        contentWarning = spoilerText.takeIf { it.isNotEmpty() }?.let { PostContentParser.parsePlainRuns(it, emojis) },
        content = PostContentParser.parseHtml(content, emojis),
        sensitive = sensitive,
        media = mediaAttachments.map { it.toModel() },
        omittedMediaCount = omittedMediaCount ?: 0,
        edited = editedAt != null,
        counts = PostCounts(replies = repliesCount, boosts = boostsCount, favourites = favouritesCount),
        emojis = emojis,
    )
}

private fun MediaAttachment.toModel(): Media {
    val width = meta.width
    val height = meta.height
    return Media(
        type = when (type) {
            MediaAttachmentType.IMAGE -> MediaType.IMAGE
            MediaAttachmentType.GIFV -> MediaType.GIFV
            MediaAttachmentType.VIDEO -> MediaType.VIDEO
            MediaAttachmentType.AUDIO -> MediaType.AUDIO
            MediaAttachmentType.UNKNOWN, MediaAttachmentType.UNRECOGNIZED -> MediaType.UNKNOWN
        },
        description = description?.takeIf { it.isNotBlank() },
        preview = previewUrl?.toString(),
        blurhash = blurhash,
        aspectRatio = if (width != null && height != null && height > 0) width / height.toFloat() else null,
    )
}

private fun ApiCustomEmoji.toModel() = CustomEmoji(shortcode, url.toString(), staticUrl.toString())
