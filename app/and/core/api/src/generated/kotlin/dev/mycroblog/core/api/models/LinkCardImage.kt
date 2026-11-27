package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The image of a link card, served by this server's media proxy in the small size of an
 * attachment's image. An animated image is its first frame.
 */
@Serializable
public data class LinkCardImage(
  /**
   * The image through the media proxy.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * The width of the original image in pixels.
   */
  @SerialName("width")
  public val width: Int,
  /**
   * The height of the original image in pixels.
   */
  @SerialName("height")
  public val height: Int,
  /**
   * The blurhash (https://blurha.sh) of the image, to show while it loads.
   */
  @SerialName("blurhash")
  public val blurhash: String? = null,
  /**
   * The alternative text of the image (`og:image:alt`); omitted when it has none.
   */
  @SerialName("description")
  public val description: String? = null,
)
