package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account removed from a voice chat room, who may not join it again.
 */
@Serializable
public data class AdminVoiceRoomBan(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("banned_by_account_id")
  public val bannedByAccountId: UUID,
  @SerialName("banned_at")
  public val bannedAt: Instant,
)
