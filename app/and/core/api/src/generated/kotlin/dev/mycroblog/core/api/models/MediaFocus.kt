package dev.mycroblog.core.api.models

import kotlin.Double
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The focal point that previews keep in view when they crop the image, from -1.0 (left,
 * bottom) to 1.0 (right, top) on each axis; (0, 0) is the center.
 */
@Serializable
public data class MediaFocus(
  @SerialName("x")
  public val x: Double,
  @SerialName("y")
  public val y: Double,
)
