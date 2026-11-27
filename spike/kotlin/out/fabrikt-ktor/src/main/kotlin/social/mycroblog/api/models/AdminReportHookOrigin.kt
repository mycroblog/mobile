package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The decision hook that filed the report; omitted for a report that a person or another server
 * filed. Such a report has the instance actor as its reporter, is not forwarded when it is
 * filed (a moderator may forward it later, without the plugin's note), and nobody is notified
 * when it is resolved.
 */
@Serializable
public data class AdminReportHookOrigin(
  /**
   * A decision hook, where the server asks an external review before it accepts something:
   * `inbound.status` (posts from other servers), `inbound.follow` (follows from other servers),
   * `signup.check` (sign-ups), `media.check` (uploaded media), `local.status` (local posts),
   * `inbound.actor` (profiles of accounts on other servers), `local.profile` (local profiles).
   */
  @SerialName("hook")
  public val hook: HookName,
  /**
   * The decision that filed the report, which can be gone after its retention.
   */
  @SerialName("decision_id")
  public val decisionId: String,
  /**
   * The coded reason of a verdict.
   */
  @SerialName("reason")
  public val reason: HookReason? = null,
)
