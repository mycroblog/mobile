package social.mycroblog.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An invitation to an event. The invited user sees the event until the invitation is revoked,
 * whatever its visibility.
 */
@Serializable
public data class EventInvitation(
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
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("invited_by_id")
  public val invitedById: UUID,
  @SerialName("invited_at")
  public val invitedAt: Instant,
)
