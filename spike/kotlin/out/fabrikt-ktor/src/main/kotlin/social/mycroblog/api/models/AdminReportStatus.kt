package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A post that a report points at, as a moderator reviews it. `status` is the post as it
 * was last, without the moderator's own relation to it (`favourited`, `boosted`,
 * `bookmarked`, `pinned`, and `permissions` are omitted, and so are `me` of each of its
 * `reactions` and `voted` and `own_votes` of its `poll`). What a moderator blocks, what
 * blocks them, and the restrictions on an author hide nothing of it. Its `quote` shows the
 * quoted post unless the post's visibility keeps it from an account with no relation to its
 * author: a followers-only or direct post is `unauthorized`, since the report does not point
 * at it. A deleted post keeps its `poll` and `quote`, and has no counts, no `reactions`, and
 * no `card` any more; the link stays in its `content`.
 */
@Serializable
public data class AdminReportStatus(
  /**
   * A status (post), as the viewer sees it. `favourited`, `boosted`, `bookmarked`, and
   * `muted` describe the authenticated user's relation to it and are omitted for anonymous
   * requests; `pinned` is only given to the author.
   *
   * In a timeline, a boost is a status of the booster with `boost` set to the boosted
   * status: its `id` and `uri` are those of the boost, `created_at` is when it was boosted,
   * `visibility` and `language` are those of the boosted status, its `content`,
   * `spoiler_text`, `media_attachments`, `mentions`, `tags`, `emojis`, and `reactions` are
   * empty, its `card` and `quote` are omitted, and its counts are zero.
   *
   * An article (`article` is given) is read as its title, summary, and cover everywhere, and
   * its `content` is empty except where one article is asked for: `getStatus`, `createArticle`,
   * and `editArticle`. Timelines, notifications, streaming, search, threads, and every other list
   * carry no body. An article has no content warning, media attachments, or poll.
   */
  @SerialName("status")
  public val status: Status,
  /**
   * When the post was deleted, by its author or otherwise; omitted when it is not.
   */
  @SerialName("deleted_at")
  public val deletedAt: Instant? = null,
)
