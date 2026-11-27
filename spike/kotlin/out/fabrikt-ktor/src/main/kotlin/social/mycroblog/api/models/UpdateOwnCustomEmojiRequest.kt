package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The settings of a custom emoji that the user made to change; absent ones stay.
 */
@Serializable
public data class UpdateOwnCustomEmojiRequest(
  /**
   * Whether the picker offers the emoji.
   */
  @SerialName("visible_in_picker")
  public val visibleInPicker: Boolean? = null,
  /**
   * The category in the picker, up to 64 characters; empty removes it.
   */
  @SerialName("category")
  public val category: String? = null,
  /**
   * The new aliases, each following the rules of a shortcode; at most 10.
   */
  @SerialName("aliases")
  public val aliases: List<String>? = null,
)
