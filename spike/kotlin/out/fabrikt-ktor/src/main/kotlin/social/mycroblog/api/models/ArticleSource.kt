package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the author wrote of an article besides its Markdown body, for editing it.
 */
@Serializable
public data class ArticleSource(
  /**
   * The title.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The summary the author wrote; empty when the server made it from the body.
   */
  @SerialName("summary")
  public val summary: String,
  /**
   * The cover image; omitted when there is none.
   */
  @SerialName("cover")
  public val cover: StatusMediaItem? = null,
  /**
   * The hashtags, without `#`.
   */
  @SerialName("tags")
  public val tags: List<String>,
)
