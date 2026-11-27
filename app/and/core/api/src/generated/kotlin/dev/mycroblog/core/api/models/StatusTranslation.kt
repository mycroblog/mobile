package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A post in another language. It belongs to the version of the post that was current when it was asked for.
 */
@Serializable
public data class StatusTranslation(
  /**
   * The language of this answer, as a BCP 47 tag.
   */
  @SerialName("language")
  public val language: String,
  /**
   * The language translated from, which a provider may have detected; `und` when it is not known.
   */
  @SerialName("source_language")
  public val sourceLanguage: String,
  /**
   * The text as sanitized HTML, with the mentions, hashtags, links, custom emoji, and code of the post unchanged.
   */
  @SerialName("content")
  public val content: String,
  /**
   * The content warning, as plain text; empty when the post has none.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String,
  /**
   * The alt text of the media that has any, in order.
   */
  @SerialName("media_attachments")
  public val mediaAttachments: List<StatusTranslationAttachment>,
  /**
   * The titles of the poll's options, in the order of the options; empty when the post has no poll.
   */
  @SerialName("poll_options")
  public val pollOptions: List<String>,
  /**
   * The service that translated a post, for the attribution next to the translation.
   */
  @SerialName("provider")
  public val provider: TranslationProvider? = null,
)
