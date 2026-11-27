package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The new content of a status.
 */
@Serializable
public data class EditStatusRequest(
  /**
   * The plain text; the server links mentions, hashtags, and URLs.
   */
  @SerialName("text")
  public val text: String,
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
   * The media to attach, in order.
   */
  @SerialName("media")
  public val media: List<StatusMediaItem>? = null,
  /**
   * Takes the status's quote away for good. A status keeps the status it
   * quotes otherwise: an edit cannot change it.
   */
  @SerialName("remove_quote")
  public val removeQuote: Boolean? = null,
)
