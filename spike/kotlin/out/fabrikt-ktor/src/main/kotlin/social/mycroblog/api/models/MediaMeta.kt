package social.mycroblog.api.models

import kotlin.Double
import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the server knows about the media file.
 */
@Serializable
public data class MediaMeta(
  /**
   * The width of the original image in pixels, after its EXIF orientation; absent until the file is ready, and for a remote file whose server declared none.
   */
  @SerialName("width")
  public val width: Int? = null,
  /**
   * The height of the original image in pixels, after its EXIF orientation; absent until the file is ready, and for a remote file whose server declared none.
   */
  @SerialName("height")
  public val height: Int? = null,
  /**
   * The focal point that previews keep in view when they crop the image, from -1.0 (left,
   * bottom) to 1.0 (right, top) on each axis; (0, 0) is the center.
   */
  @SerialName("focus")
  public val focus: MediaFocus,
  /**
   * The length of a video or audio file in seconds; absent for an image and until the file is ready.
   */
  @SerialName("duration")
  public val duration: Double? = null,
)
