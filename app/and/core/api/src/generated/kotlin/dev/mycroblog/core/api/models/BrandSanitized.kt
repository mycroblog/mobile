package dev.mycroblog.core.api.models

import java.math.BigDecimal
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An SVG as the server would save it: the sanitized document, the size of its viewBox, the
 * renditions to draw from it with their sizes, and what was removed.
 */
@Serializable
public data class BrandSanitized(
  /**
   * The sanitized SVG document.
   */
  @SerialName("svg")
  public val svg: String,
  @Contextual
  @SerialName("view_box_width")
  public val viewBoxWidth: BigDecimal,
  @Contextual
  @SerialName("view_box_height")
  public val viewBoxHeight: BigDecimal,
  @SerialName("renditions")
  public val renditions: List<BrandRenditionSize>,
  @SerialName("dropped")
  public val dropped: List<BrandDropped>,
)
