package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new local custom emoji.
 */
@Serializable
public data class CreateCustomEmojiRequest(
  /**
   * 2 to 30 letters (A to Z, a to z), digits, and underscores; case matters.
   */
  @SerialName("shortcode")
  public val shortcode: String,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("media_id")
  public val mediaId: UUID,
  /**
   * The category in the picker, up to 64 characters; empty for none.
   */
  @SerialName("category")
  public val category: String? = null,
  /**
   * Other names, each following the rules of a shortcode; at most 10.
   */
  @SerialName("aliases")
  public val aliases: List<String>? = null,
  /**
   * Whether the picker offers the emoji.
   */
  @SerialName("visible_in_picker")
  public val visibleInPicker: Boolean = true,
)
