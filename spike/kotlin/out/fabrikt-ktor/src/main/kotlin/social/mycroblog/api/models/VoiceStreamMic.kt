package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of a `mic` event of a voice chat room stream.
 */
@Serializable
public data class VoiceStreamMic(
  /**
   * The account ID of the participant.
   */
  @SerialName("participant")
  public val participant: String,
  /**
   * Whether the microphone is on.
   */
  @SerialName("on")
  public val on: Boolean,
)
