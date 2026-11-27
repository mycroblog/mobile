package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What makes a status an article: its title, summary, and cover. The summary is never a content
 * warning; a `sensitive` article shows the title and the summary and folds the body and the
 * cover.
 */
@Serializable
public data class StatusArticle(
  /**
   * The title, as plain text; empty only for an article from another server that has none.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The summary, as plain text; the server makes one from the body when the author writes none.
   */
  @SerialName("summary")
  public val summary: String,
  /**
   * The cover image; omitted when there is none or it cannot be shown.
   */
  @SerialName("cover")
  public val cover: MediaAttachment? = null,
)
