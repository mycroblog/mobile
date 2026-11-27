package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The status that a status quotes. Quotes are shown one level deep:
 * `quoted_status` has no `quote` of its own, and clients link to it instead.
 */
@Serializable
public data class StatusQuote(
  /**
   * The state of a quote as the viewer sees it, with the values of Mastodon's
   * `Quote.state` and `legacy`. The quoted status is shown for `accepted` and `legacy`, and
   * behind the reason for `muted_account` and `blocked_domain`:
   *
   * - `pending`: the quoted author has not approved the quote yet, or the quoted status is not
   *   fetched yet.
   * - `accepted`: the quoted author approved the quote.
   * - `rejected`: the quoted author refused the quote.
   * - `revoked`: the quoted author withdrew the approval.
   * - `legacy`: a quote from software without approvals (Misskey, Akkoma), shown as it is.
   * - `deleted`: the quoted status is deleted.
   * - `unauthorized`: the viewer may not see the quoted status.
   * - `blocked_account`: the viewer blocks the quoted author, or is blocked by them.
   * - `blocked_domain`: the viewer mutes the domain of the quoted author.
   * - `muted_account`: the viewer mutes the quoted author.
   */
  @SerialName("state")
  public val state: QuoteState,
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
  @SerialName("quoted_status")
  public val quotedStatus: Status? = null,
)
