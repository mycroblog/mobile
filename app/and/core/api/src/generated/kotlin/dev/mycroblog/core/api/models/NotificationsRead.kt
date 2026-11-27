package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Long
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of the stream event `notifications_read`: the user read notifications on a device,
 * and the read marker and the unread count are now these.
 */
@Serializable
public data class NotificationsRead(
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
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID? = null,
)
