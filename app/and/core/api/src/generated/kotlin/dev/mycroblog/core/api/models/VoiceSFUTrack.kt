package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A transceiver of an `offer` that sends the audio of one speaker.
 */
@Serializable
public data class VoiceSFUTrack(
  /**
   * The `mid` of the transceiver in the offer's session description.
   */
  @SerialName("mid")
  public val mid: String,
  /**
   * The account ID of the speaker, as in the `account` of a `VoiceRoomParticipant`.
   */
  @SerialName("participant")
  public val participant: String,
)
