package social.mycroblog.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One channel of one row of the notification settings.
 */
@Serializable
public data class NotificationSettingCell(
  /**
   * Whether the user gets the row's notifications through the channel.
   */
  @SerialName("enabled")
  public val enabled: Boolean,
  /**
   * Whether the channel is always on and cannot be changed: clients show it on and
   * disabled.
   */
  @SerialName("fixed")
  public val fixed: Boolean,
)
