package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A brand image as the administrators see it: the image of `Instance` with the background color
 * of the icon and when it was set, and, in the answer to `setAdminBrand`, what the sanitizer
 * removed from the upload.
 */
@Serializable
public data class AdminBrandImage(
  @SerialName("revision")
  public val revision: String,
  @Contextual
  @SerialName("svg_url")
  public val svgUrl: URI,
  /**
   * The width of the SVG's viewBox, rounded.
   */
  @SerialName("width")
  public val width: Int,
  /**
   * The height of the SVG's viewBox, rounded.
   */
  @SerialName("height")
  public val height: Int,
  @SerialName("renditions")
  public val renditions: List<BrandFile>,
  /**
   * The `#rrggbb` color behind the opaque renditions of the icon; omitted for a logo.
   */
  @SerialName("background")
  public val background: String? = null,
  @SerialName("revised_at")
  public val revisedAt: Instant,
  @SerialName("dropped")
  public val dropped: List<BrandDropped>? = null,
)
