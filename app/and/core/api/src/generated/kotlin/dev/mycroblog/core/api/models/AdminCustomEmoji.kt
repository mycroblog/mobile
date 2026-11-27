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
 * A custom emoji as the administrators manage it: a local one, or a remote
 * one recorded from the posts and profiles of another server.
 */
@Serializable
public data class AdminCustomEmoji(
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
   * Whether the emoji is of this instance.
   */
  @SerialName("local")
  public val local: Boolean,
  /**
   * The domain of a remote emoji; absent for a local one.
   */
  @SerialName("domain")
  public val domain: String? = null,
  /**
   * The ActivityPub `id` of the emoji: `https://{host}/ap/emojis/{id}` for a local one,
   * and the recorded one for a remote one; absent when a remote emoji has none.
   */
  @Contextual
  @SerialName("uri")
  public val uri: URI? = null,
  /**
   * The URL of the image, animated when the emoji is; absent when the image cannot be
   * shown (such as a remote one from a domain whose media the instance rejects).
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
   * Whether the picker offers the local emoji; always `true` for a remote one.
   */
  @SerialName("visible_in_picker")
  public val visibleInPicker: Boolean,
  /**
   * The category of a local emoji in the picker; absent for none.
   */
  @SerialName("category")
  public val category: String? = null,
  /**
   * The other names of a local emoji that the picker finds it by.
   */
  @SerialName("aliases")
  public val aliases: List<String>,
  /**
   * An account, as clients show it.
   */
  @SerialName("created_by")
  public val createdBy: Account? = null,
  /**
   * Whether a user made the emoji whose account no longer exists.
   */
  @SerialName("created_by_deleted")
  public val createdByDeleted: Boolean,
  /**
   * When the emoji was registered or first seen.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When its current image was set, its ActivityPub `updated`.
   */
  @SerialName("updated_at")
  public val updatedAt: Instant,
)
