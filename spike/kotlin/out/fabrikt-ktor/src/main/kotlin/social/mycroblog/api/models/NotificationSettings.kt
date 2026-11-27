package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's notification settings: the rows the user gets, in the order to
 * show them. The moderator rows are only those whose permission the user's roles grant, and
 * `supporter` is there only while the supporter program is enabled (`Instance.supporters_enabled`).
 */
@Serializable
public data class NotificationSettings(
  @SerialName("rows")
  public val rows: List<NotificationSettingRow>,
)
