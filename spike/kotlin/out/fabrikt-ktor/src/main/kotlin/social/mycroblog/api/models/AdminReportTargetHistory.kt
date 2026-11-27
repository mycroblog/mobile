package social.mycroblog.api.models

import kotlin.Long
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the moderators had about the target of a report.
 */
@Serializable
public data class AdminReportTargetHistory(
  /**
   * The number of the target's other reports, open and resolved.
   */
  @SerialName("reports_count")
  public val reportsCount: Long,
  /**
   * The number of moderation actions on the target, ended ones included.
   */
  @SerialName("actions_count")
  public val actionsCount: Long,
)
