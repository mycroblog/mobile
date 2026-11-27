package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One revision of a status.
 */
@Serializable
public data class StatusEdit(
  /**
   * The text of the revision as sanitized HTML; for an article, its body.
   */
  @SerialName("content")
  public val content: String,
  /**
   * The title and summary of the revision of an article; omitted for a post.
   */
  @SerialName("article")
  public val article: StatusEditArticle? = null,
  /**
   * The content warning of the revision; empty when there is none.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String,
  /**
   * Whether the media of the revision are sensitive.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean,
  /**
   * When the revision was made; the first one is when the status was posted.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * The media the revision attaches, in order.
   */
  @SerialName("media_attachments")
  public val mediaAttachments: List<MediaAttachment>,
  /**
   * The custom emoji of the revision's text and content warning, as `Status.emojis`.
   */
  @SerialName("emojis")
  public val emojis: List<CustomEmoji>,
)
