package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A participant of a voice chat room.
 */
@Serializable
public data class VoiceRoomParticipant(
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * Whether a participant speaks or listens.
   */
  @SerialName("role")
  public val role: VoiceRole,
  /**
   * Whether the participant is the host.
   */
  @SerialName("hosting")
  public val hosting: Boolean,
  /**
   * Whether the participant is a co-host. The host is never a co-host.
   */
  @SerialName("cohosting")
  public val cohosting: Boolean,
  /**
   * Whether the participant raised a hand to speak.
   */
  @SerialName("hand_raised")
  public val handRaised: Boolean,
  /**
   * Whether the user blocks the participant or the participant blocks the user.
   */
  @SerialName("blocked")
  public val blocked: Boolean,
  /**
   * The live session of the participant, in the `state` events of a room stream only; the
   * sessions connect to each other with signals.
   */
  @SerialName("session")
  public val session: String? = null,
)
