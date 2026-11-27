package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account taking part in a voice chat room, from joining to leaving.
 */
@Serializable
public data class AdminVoiceRoomParticipation(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
  @SerialName("joined_at")
  public val joinedAt: Instant,
  /**
   * When the account left; omitted while it takes part.
   */
  @SerialName("left_at")
  public val leftAt: Instant? = null,
  /**
   * Why the account left (`left`, `moved`, `disconnected`, `removed`, `blocked`,
   * `suspended`, `room_ended`); omitted while it takes part.
   */
  @SerialName("leave_reason")
  public val leaveReason: AdminVoiceRoomParticipationLeaveReason? = null,
)
