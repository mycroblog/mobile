package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The local custom emoji that a copy of a remote one becomes.
 */
@Serializable
public data class CopyCustomEmojiRequest(
  /**
   * The shortcode of the local emoji: 2 to 30 letters (A to Z, a to z), digits, and
   * underscores; case matters. Absent, the remote emoji's.
   */
  @SerialName("shortcode")
  public val shortcode: String? = null,
)
