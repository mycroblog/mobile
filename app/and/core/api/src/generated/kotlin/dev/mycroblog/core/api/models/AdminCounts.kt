package dev.mycroblog.core.api.models

import kotlin.Long
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What awaits the staff; a count whose permission the user lacks is omitted.
 */
@Serializable
public data class AdminCounts(
  /**
   * The reports that are open. Needs `manage_reports`.
   */
  @SerialName("open_reports")
  public val openReports: Long? = null,
  /**
   * The registrations that await approval. Needs `manage_accounts`.
   */
  @SerialName("pending_registrations")
  public val pendingRegistrations: Long? = null,
  /**
   * The appeals of moderation actions that await a decision. Needs `manage_accounts`.
   */
  @SerialName("pending_appeals")
  public val pendingAppeals: Long? = null,
  /**
   * The custom emoji requests that await a decision. Needs `manage_custom_emojis`.
   */
  @SerialName("pending_custom_emoji_requests")
  public val pendingCustomEmojiRequests: Long? = null,
  /**
   * The error groups that are open: new, regressed after a resolve, or unmuted. Needs
   * `manage_errors`.
   */
  @SerialName("open_error_groups")
  public val openErrorGroups: Long? = null,
  /**
   * When the open report that has waited longest was filed; omitted when no report is open.
   * Needs `manage_reports`.
   */
  @SerialName("oldest_open_report_filed_at")
  public val oldestOpenReportFiledAt: Instant? = null,
)
