package social.mycroblog.api.models

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What an `admin_error_group` tells of: a new error group (`created`, with `group_id`), a
 * resolved group that happened again on another version (`regressed`, with `group_id`), or the
 * new and regressed groups of an hour beyond the 10 that the server notifies of one by one
 * (`others`, with how many so far in `count`).
 */
@Serializable
public data class NotificationErrorGroup(
  @SerialName("change")
  public val change: NotificationErrorGroupChange,
  /**
   * The error group of a `created` or a `regressed`.
   */
  @SerialName("group_id")
  public val groupId: String? = null,
  /**
   * How many groups an `others` stands for.
   */
  @SerialName("count")
  public val count: Long? = null,
)
