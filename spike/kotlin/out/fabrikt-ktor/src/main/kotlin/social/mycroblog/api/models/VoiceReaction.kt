package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A reaction in a voice chat room.
 */
@Serializable
public data class VoiceReaction(
  /**
   * A Unicode emoji, or the `:shortcode:` of an enabled custom emoji of this server.
   */
  @SerialName("emoji")
  public val emoji: String,
)
