package social.mycroblog.api.models

import java.net.URI
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A rendition of a media file, without metadata. Its URL never serves other content, so
 * it may be cached for good. `content_type`, `width`, `height`, and `size` are present
 * for an upload, and absent where unknown for a remote file served through the media
 * proxy.
 */
@Serializable
public data class MediaVariant(
  /**
   * Where the rendition is served.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * The media type of the rendition.
   */
  @SerialName("content_type")
  public val contentType: String? = null,
  /**
   * The width in pixels.
   */
  @SerialName("width")
  public val width: Int? = null,
  /**
   * The height in pixels.
   */
  @SerialName("height")
  public val height: Int? = null,
  /**
   * The size of the file in bytes.
   */
  @SerialName("size")
  public val size: Long? = null,
)
