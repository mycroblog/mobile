package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Every instance setting, in a fixed order.
 */
@Serializable
public data class AdminSettingList(
  @SerialName("items")
  public val items: List<AdminSetting>,
)
