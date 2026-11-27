package social.mycroblog.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An invitation of a user to an event.
 */
@Serializable
public data class EventInvitationCreate(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID,
)
