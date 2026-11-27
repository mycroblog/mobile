package social.mycroblog.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The limits of the body of an article (the instance settings `max_article_bytes` and
 * `max_article_images`). An article over one is a 422; `previewArticle` reports both.
 */
@Serializable
public data class InstanceArticleLimits(
  /**
   * The most UTF-8 bytes of the HTML that the body renders to.
   */
  @SerialName("max_bytes")
  public val maxBytes: Int,
  /**
   * The most images of the user's own uploads in the body, besides the cover.
   */
  @SerialName("max_images")
  public val maxImages: Int,
)
