package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A local custom emoji that the user made.
 */
@Serializable
public data class OwnCustomEmoji(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The shortcode, without the colons around it.
   */
  @SerialName("shortcode")
  public val shortcode: String,
  /**
   * The URL of the image, animated when the emoji is; absent when it cannot be shown.
   */
  @Contextual
  @SerialName("url")
  public val url: URI? = null,
  /**
   * The URL of a still image of the emoji; absent with `url`.
   */
  @Contextual
  @SerialName("static_url")
  public val staticUrl: URI? = null,
  /**
   * Whether the emoji is shown; a disabled one stays `:shortcode:` text.
   */
  @SerialName("state")
  public val state: CustomEmojiState,
  /**
   * Whether the picker offers the emoji.
   */
  @SerialName("visible_in_picker")
  public val visibleInPicker: Boolean,
  /**
   * The category of the emoji in the picker; absent for none.
   */
  @SerialName("category")
  public val category: String? = null,
  /**
   * The other names that the picker finds the emoji by.
   */
  @SerialName("aliases")
  public val aliases: List<String>,
  /**
   * When the emoji was made.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When its current image was set.
   */
  @SerialName("updated_at")
  public val updatedAt: Instant,
)
