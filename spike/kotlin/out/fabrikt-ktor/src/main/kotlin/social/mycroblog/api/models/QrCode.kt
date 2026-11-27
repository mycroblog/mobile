package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A QR code as an SVG path: draw `path` filled in a `size` × `size` view box (the quiet
 * zone of four modules included) on a light background.
 */
@Serializable
public data class QrCode(
  /**
   * The width and height of the view box, in modules.
   */
  @SerialName("size")
  public val size: Int,
  /**
   * The path data (`d`) of the dark modules, one unit per module.
   */
  @SerialName("path")
  public val path: String,
)
