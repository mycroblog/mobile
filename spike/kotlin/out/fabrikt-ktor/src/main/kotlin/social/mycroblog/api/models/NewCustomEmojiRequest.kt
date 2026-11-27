package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new custom emoji request: `create` takes the `shortcode` of the new emoji, and `image` the
 * `emoji_id` of a local emoji that the user made. `media_id` is an upload of the user that
 * finished processing (upload it with the `emoji` purpose), under the rules of the image of a
 * custom emoji.
 */
@Serializable
public data class NewCustomEmojiRequest(
  /**
   * What a custom emoji request asks for: `create` a new local custom emoji,
   * or `image` a new image of a local emoji that the requester made.
   */
  @SerialName("kind")
  public val kind: CustomEmojiRequestKind,
  /**
   * For `create`, 2 to 30 letters (A to Z, a to z), digits, and underscores; case matters.
   */
  @SerialName("shortcode")
  public val shortcode: String? = null,
  /**
   * For `image`, the emoji whose image the request replaces.
   */
  @SerialName("emoji_id")
  public val emojiId: String? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("media_id")
  public val mediaId: UUID,
)
