package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A request of a holder of `create_custom_emojis` for a new local custom emoji, or for a new
 * image of one they made, which a holder of `manage_custom_emojis` approves or rejects. A request is not an emoji: no picker, post, reaction, or other server shows
 * its image until it is approved.
 */
@Serializable
public data class CustomEmojiRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * What a custom emoji request asks for: `create` a new local custom emoji,
   * or `image` a new image of a local emoji that the requester made.
   */
  @SerialName("kind")
  public val kind: CustomEmojiRequestKind,
  /**
   * The state of a custom emoji request: `pending` until an administrator decides it, then
   * `approved` or `rejected`; `withdrawn` by the requester before that; or `lapsed`, a request
   * for a new image of an emoji that was deleted.
   */
  @SerialName("state")
  public val state: CustomEmojiRequestState,
  /**
   * The shortcode of the new emoji, without the colons around it, or of the emoji whose image
   * the request replaces.
   */
  @SerialName("shortcode")
  public val shortcode: String,
  /**
   * The emoji of an `image` request, and the emoji that an approved `create` request made;
   * absent otherwise.
   */
  @SerialName("emoji_id")
  public val emojiId: String? = null,
  /**
   * The URL of the requested image, animated when it is; absent once it cannot be shown (the
   * upload of a request that ended goes with the unattached uploads).
   */
  @Contextual
  @SerialName("url")
  public val url: URI? = null,
  /**
   * The URL of a still image of the requested image; absent with `url`.
   */
  @Contextual
  @SerialName("static_url")
  public val staticUrl: URI? = null,
  /**
   * The reason of a rejection that the administrator gave; absent for none.
   */
  @SerialName("reason")
  public val reason: String? = null,
  /**
   * When the request was made.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the request was approved, rejected, withdrawn, or lapsed; absent while it is
   * pending.
   */
  @SerialName("decided_at")
  public val decidedAt: Instant? = null,
)
