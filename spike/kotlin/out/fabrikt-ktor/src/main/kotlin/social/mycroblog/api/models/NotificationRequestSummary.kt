package social.mycroblog.api.models

import kotlin.Long
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How many notification requests wait for the authenticated user.
 */
@Serializable
public data class NotificationRequestSummary(
  /**
   * The number of requests, one per source account.
   */
  @SerialName("accounts_count")
  public val accountsCount: Long,
  /**
   * The number of notifications in them.
   */
  @SerialName("notifications_count")
  public val notificationsCount: Long,
)
