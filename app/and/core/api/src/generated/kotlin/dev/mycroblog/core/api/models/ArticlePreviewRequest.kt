package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The body of an article to preview.
 */
@Serializable
public data class ArticlePreviewRequest(
  /**
   * The body in Markdown, as `createArticle` takes it.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The summary of an article, as plain text. Omitted or blank, the server makes one from the
   * start of the body (about 200 characters, cut at the end of a sentence or word). It is a
   * summary, never a content warning.
   */
  @SerialName("summary")
  public val summary: String? = null,
)
