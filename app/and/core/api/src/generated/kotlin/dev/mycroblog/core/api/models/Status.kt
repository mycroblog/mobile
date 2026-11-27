package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
@Serializable
public data class Status(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The ActivityPub ID of the status.
   */
  @Contextual
  @SerialName("uri")
  public val uri: URI,
  /**
   * The HTML page of the status, on the server it belongs to for a remote status; omitted
   * when it has none.
   */
  @Contextual
  @SerialName("url")
  public val url: URI? = null,
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * The text as sanitized HTML, with mentions, hashtags, and URLs linked. The HTML of a
   * remote status is what the server's sanitizer kept of the received document. For an
   * article, its rendered body, or empty in lists (see above); the images in the body of an
   * article from another server load through the server's media proxy.
   */
  @SerialName("content")
  public val content: String,
  /**
   * What makes a status an article: its title, summary, and cover. The summary is never a content
   * warning; a `sensitive` article shows the title and the summary and folds the body and the
   * cover.
   */
  @SerialName("article")
  public val article: StatusArticle? = null,
  /**
   * The content warning; empty when there is none.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String,
  /**
   * Whether the media are sensitive.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean,
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("visibility")
  public val visibility: Visibility,
  /**
   * The BCP 47 language tag of the text; `und` when it is unknown.
   */
  @SerialName("language")
  public val language: String,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("in_reply_to_id")
  public val inReplyToId: UUID? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("in_reply_to_account_id")
  public val inReplyToAccountId: UUID? = null,
  /**
   * When the status was posted.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
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
  @SerialName("boost")
  public val boost: Status? = null,
  /**
   * When the status was last edited; omitted when it never was.
   */
  @SerialName("edited_at")
  public val editedAt: Instant? = null,
  /**
   * The attached media, in order.
   */
  @SerialName("media_attachments")
  public val mediaAttachments: List<MediaAttachment>,
  /**
   * The number of media attachments of a remote status that the server left out: it keeps
   * the first four attachments of a remote post. Clients show that there are more and link
   * to `url` (or `uri`). Omitted when none were left out.
   */
  @SerialName("omitted_media_count")
  public val omittedMediaCount: Int? = null,
  /**
   * The accounts the text mentions.
   */
  @SerialName("mentions")
  public val mentions: List<StatusMention>,
  /**
   * The hashtags of the text.
   */
  @SerialName("tags")
  public val tags: List<StatusTag>,
  /**
   * The user's keyword filters with the action `warn` that the post matched where it is shown,
   * with the words each matched; empty when none did, and always for an anonymous request.
   * Clients fold a post with any behind the titles of its filters, with a way to show it. A
   * post that matches a filter with the action `hide` is left out instead, except the post
   * that `GET /api/v1/statuses/{id}` shows, which is folded. In a
   * timeline, a boost carries the filters that the boosted post matched. A quoted post carries
   * its own: a quote that matches folds only the quote, even for `hide`. The user's own posts
   * and boosts, and what they quote, never match.
   */
  @SerialName("filtered")
  public val filtered: List<FilterResult>,
  /**
   * On the home timeline and its stream, the names (without `#`) of the hashtags that the
   * user follows and the post carries, when the post is there only for them: its author is
   * neither the user nor an account the user follows. Omitted everywhere else.
   */
  @SerialName("followed_tags")
  public val followedTags: List<String>? = null,
  /**
   * The custom emoji of the text, the content warning, and the poll's choices. Clients show each `:shortcode:` of those texts that is in this list as its
   * image, and the rest as text. Empty for a boost, whose emoji are those of `boost`.
   */
  @SerialName("emojis")
  public val emojis: List<CustomEmoji>,
  /**
   * The number of replies.
   */
  @SerialName("replies_count")
  public val repliesCount: Int,
  /**
   * The number of boosts.
   */
  @SerialName("boosts_count")
  public val boostsCount: Int,
  /**
   * The number of favourites.
   */
  @SerialName("favourites_count")
  public val favouritesCount: Int,
  /**
   * The emoji reactions to the status, counted per emoji: the 20 emoji with the most
   * reactions, the most first. The others are seen in `listStatusReactions`. Favourites
   * are counted apart, in `favourites_count`.
   */
  @SerialName("reactions")
  public val reactions: List<StatusReaction>,
  /**
   * Whether the authenticated user favourited the status.
   */
  @SerialName("favourited")
  public val favourited: Boolean? = null,
  /**
   * Whether the authenticated user boosts the status.
   */
  @SerialName("boosted")
  public val boosted: Boolean? = null,
  /**
   * Whether the authenticated user bookmarked the status.
   */
  @SerialName("bookmarked")
  public val bookmarked: Boolean? = null,
  /**
   * Whether the authenticated user mutes the thread (conversation) of the status, which
   * keeps the user from being notified of what happens in it (`muteStatusThread`).
   */
  @SerialName("muted")
  public val muted: Boolean? = null,
  /**
   * Whether the status is pinned to the author's profile; given only to the author.
   */
  @SerialName("pinned")
  public val pinned: Boolean? = null,
  /**
   * The approval of this reply by the author of the status it replies to, while it is not
   * given; given only to the author of the reply, and omitted for a reply that needs no
   * approval or has it. A `pending` reply is shown to its author alone.
   */
  @SerialName("approval")
  public val approval: InteractionApprovalState? = null,
  /**
   * The approval of the authenticated user's boost of the status by its author, while it is
   * not given; omitted when the user did not ask for one or it is given (then `boosted` is
   * true).
   */
  @SerialName("boost_approval")
  public val boostApproval: InteractionApprovalState? = null,
  /**
   * The poll of a status; omitted when the status has none. The counts of a poll of
   * another server are those its server last reported. A poll whose author hides the votes of
   * each option until it ends leaves out `votes_count` of its options until then. `voted` and
   * `own_votes` describe the authenticated user's vote and are omitted for anonymous requests.
   */
  @SerialName("poll")
  public val poll: Poll? = null,
  /**
   * The card of the first link in a status: the title, description, and image
   * that the linked page declares (Open Graph, X Card, and JSON-LD), fetched by the server
   * after the status was posted or received. Omitted from a status with attachments or a
   * poll, from a status whose link has no card yet, from a status whose link is on a domain
   * where a rule of the crawler stops fetching, and when the instance setting
   * `link_cards` is off. A card appears after its fetch, so a status read right after
   * it was posted may have none.
   *
   * Clients show the card as one link to `url` and, under a content warning, only once the
   * warning is opened. When the status is `sensitive`, the image is hidden until the viewer
   * shows it. A rule of the crawler can leave the card without its image.
   */
  @SerialName("card")
  public val card: LinkCard? = null,
  /**
   * The status that this status quotes; omitted when it quotes none, and in a
   * `quoted_status`.
   */
  @SerialName("quote")
  public val quote: StatusQuote? = null,
  /**
   * The board of an event that the status is posted on; omitted for any other status.
   */
  @SerialName("event_board")
  public val eventBoard: StatusEventBoard? = null,
  /**
   * What the authenticated user may do with the status: reply to it, boost it, and quote it.
   * Omitted for anonymous requests. A post of the board of an event (`event_board`) is never
   * boosted or quoted, and is replied to (on its board) only by the users who may post there.
   */
  @SerialName("permissions")
  public val permissions: InteractionPermissions? = null,
  /**
   * Who may reply to, boost, and quote the status; given only to its author.
   */
  @SerialName("interaction_policy")
  public val interactionPolicy: InteractionPolicy? = null,
)
