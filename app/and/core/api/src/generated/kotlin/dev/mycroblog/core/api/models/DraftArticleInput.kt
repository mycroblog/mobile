package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Makes the draft that of an article, whose `text` is the body in Markdown. A draft of an article
 * has no `spoiler_text`, `media`, `poll`, `in_reply_to_id`, or `quoted_status_id`, and is never
 * `direct` (422). It keeps an empty title; publishing it checks it as `createArticle` does.
 */
@Serializable
public data class DraftArticleInput(
  /**
   * The title, as plain text; omitted or empty while there is none.
   */
  @SerialName("title")
  public val title: String? = null,
  /**
   * The summary of an article, as plain text. Omitted or blank, the server makes one from the
   * start of the body (about 200 characters, cut at the end of a sentence or word). It is a
   * summary, never a content warning.
   */
  @SerialName("summary")
  public val summary: String? = null,
  /**
   * The cover image, one of the user's own uploads, processed or not.
   */
  @SerialName("cover")
  public val cover: StatusMediaItem? = null,
  /**
   * The hashtags, with or without `#`; checked when the draft is published.
   */
  @SerialName("tags")
  public val tags: List<String>? = null,
)
