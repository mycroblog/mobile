package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The whole content of a draft; a save replaces what the draft had. A draft keeps text that a
 * post would refuse (empty, or longer than `max_post_characters`), up to 100,000 characters, and
 * media that are still being processed; publishing it checks it as a new post.
 */
@Serializable
public data class DraftRequest(
  /**
   * The plain text; omitted or empty while there is none.
   */
  @SerialName("text")
  public val text: String? = null,
  /**
   * The content warning; omitted or empty for none. It counts toward `max_post_characters`.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String? = null,
  /**
   * Whether the media are sensitive.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean? = null,
  /**
   * A BCP 47 language tag; omitted means the user's default post language.
   */
  @SerialName("language")
  public val language: String? = null,
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("visibility")
  public val visibility: Visibility,
  /**
   * The post to reply to, checked when the draft is scheduled and published.
   */
  @SerialName("in_reply_to_id")
  public val inReplyToId: String? = null,
  /**
   * The post to quote, checked when the draft is scheduled and published.
   */
  @SerialName("quoted_status_id")
  public val quotedStatusId: String? = null,
  /**
   * Who may reply to, boost, and quote the post; the kinds left out take the user's
   * `default_interaction_policy` when the draft is published.
   */
  @SerialName("interaction_policy")
  public val interactionPolicy: InteractionPolicyChange? = null,
  /**
   * The media to attach, in order: the user's own uploads, processed or not. A draft with a
   * `poll` attaches none.
   */
  @SerialName("media")
  public val media: List<JsonElement>? = null,
  /**
   * The poll of a draft. A draft keeps a poll still being written (fewer than two options, or
   * blank ones); publishing it checks the limits of `PollInput`. It ends `expires_in` seconds after
   * the draft is published.
   */
  @SerialName("poll")
  public val poll: DraftPoll? = null,
  /**
   * Makes the draft that of an article, whose `text` is the body in Markdown. A draft of an article
   * has no `spoiler_text`, `media`, `poll`, `in_reply_to_id`, or `quoted_status_id`, and is never
   * `direct` (422). It keeps an empty title; publishing it checks it as `createArticle` does.
   */
  @SerialName("article")
  public val article: DraftArticleInput? = null,
)
