package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The title and summary of one revision of an article.
 */
@Serializable
public data class StatusEditArticle(
  /**
   * The title of the revision.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The summary of the revision.
   */
  @SerialName("summary")
  public val summary: String,
)
