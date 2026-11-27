package social.mycroblog.api.models

import java.util.UUID
import kotlin.Long
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How far the authenticated user has read the notifications, across all clients.
 */
@Serializable
public data class NotificationMarker(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("last_read_id")
  public val lastReadId: UUID? = null,
  /**
   * The number of listed notifications that are unread, as `Notification.read` tells.
   */
  @SerialName("unread_count")
  public val unreadCount: Long,
)
