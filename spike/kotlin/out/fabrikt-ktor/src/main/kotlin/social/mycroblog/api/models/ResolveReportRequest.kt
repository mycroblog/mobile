package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How a report is resolved.
 */
@Serializable
public data class ResolveReportRequest(
  /**
   * How the report was resolved, present only while it is resolved. `actioned`: a moderator
   * took an action on the target. `dismissed`: there was nothing to act on.
   */
  @SerialName("outcome")
  public val outcome: AdminReportOutcome,
)
