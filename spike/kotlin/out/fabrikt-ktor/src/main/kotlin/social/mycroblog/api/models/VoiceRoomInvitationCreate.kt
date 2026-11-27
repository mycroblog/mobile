package social.mycroblog.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user to invite to a voice chat room, and as what: a listener (the default), or a speaker,
 * who may speak without asking.
 */
@Serializable
public data class VoiceRoomInvitationCreate(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
  /**
   * Whether a participant speaks or listens.
   */
  @SerialName("role")
  public val role: VoiceRole? = null,
)
