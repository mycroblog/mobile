package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An invitation to a voice chat room. The invited user sees the room until the invitation is
 * revoked; an invitation as a speaker also lets the user speak without asking.
 */
@Serializable
public data class VoiceRoomInvitation(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
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
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("invited_by_id")
  public val invitedById: UUID,
  @SerialName("invited_at")
  public val invitedAt: Instant,
)
