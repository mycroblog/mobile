package social.mycroblog.api.models

import java.net.URI
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A hashtag and whether the user follows it. The posts of the hashtags the user follows appear
 * on the home timeline.
 */
@Serializable
public data class Hashtag(
  /**
   * The hashtag without `#`, as first written on the server, or as the request wrote it when
   * no post has used it and nobody follows it.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The hashtag's page.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * Whether the user follows the hashtag; false for a visitor who is not logged in.
   */
  @SerialName("following")
  public val following: Boolean,
)
