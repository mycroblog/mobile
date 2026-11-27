package social.mycroblog.api.models

import java.util.UUID
import kotlin.Long
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The filtered notifications of one source that wait for the authenticated user to accept or
 * dismiss them. The request is named by the ID of its source account.
 */
@Serializable
public data class NotificationRequest(
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
   * The number of notifications waiting in the request.
   */
  @SerialName("notifications_count")
  public val notificationsCount: Long,
  /**
   * A notification of something that happened to the authenticated user.
   */
  @SerialName("last_notification")
  public val lastNotification: Notification? = null,
)
