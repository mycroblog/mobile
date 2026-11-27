package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A local custom emoji that the picker offers. Picking it inserts
 * `:shortcode:` into the text.
 */
@Serializable
public data class PickerCustomEmoji(
  /**
   * The shortcode, without the colons around it.
   */
  @SerialName("shortcode")
  public val shortcode: String,
  /**
   * The URL of the image, animated when the emoji is.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * The URL of a still image of the emoji.
   */
  @Contextual
  @SerialName("static_url")
  public val staticUrl: URI,
  /**
   * The category that the picker groups the emoji in; absent for none.
   */
  @SerialName("category")
  public val category: String? = null,
  /**
   * Other names that a search of the picker finds the emoji by.
   */
  @SerialName("aliases")
  public val aliases: List<String>,
)
