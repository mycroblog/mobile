package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to a media file; a property left out keeps its value.
 */
@Serializable
public data class MediaUpdate(
  /**
   * The alternative text; an empty string removes it. Text with U+0000 is a 422.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * The focal point that previews keep in view when they crop the image, from -1.0 (left,
   * bottom) to 1.0 (right, top) on each axis; (0, 0) is the center.
   */
  @SerialName("focus")
  public val focus: MediaFocus? = null,
)
