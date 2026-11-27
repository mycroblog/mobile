package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of a `reaction` event of a voice chat room stream.
 */
@Serializable
public data class VoiceStreamReaction(
  /**
   * The account ID of the participant who reacted.
   */
  @SerialName("participant")
  public val participant: String,
  /**
   * A Unicode emoji, or the `:shortcode:` of a custom emoji of this server.
   */
  @SerialName("emoji")
  public val emoji: String,
)
