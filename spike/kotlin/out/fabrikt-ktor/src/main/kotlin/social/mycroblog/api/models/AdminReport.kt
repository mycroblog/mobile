package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A report of an account to the moderators.
 */
@Serializable
public data class AdminReport(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The `id` of the `Flag` activity that another server sent; omitted for a report
   * made on this server.
   */
  @SerialName("uri")
  public val uri: String? = null,
  /**
   * Why the account is reported. `spam`: spam or unwanted promotion. `legal`: content that
   * is illegal. `violation`: it breaks the instance rules that `rule_ids` cite. `other`:
   * anything else.
   */
  @SerialName("category")
  public val category: ReportCategory,
  /**
   * The reporter's comment as plain text, up to 5,000 characters.
   */
  @SerialName("comment")
  public val comment: String,
  /**
   * Whether the report was forwarded to the target's server: when it was filed, because
   * the reporter asked, or later by a moderator.
   */
  @SerialName("forwarded")
  public val forwarded: Boolean,
  /**
   * Whether a moderator can forward the report to the target's server now: it was filed on
   * this server, its target is a remote account, it points at no voice chat room and no
   * event, and it is not forwarded yet.
   */
  @SerialName("forwardable")
  public val forwardable: Boolean,
  @SerialName("filed_at")
  public val filedAt: Instant,
  /**
   * `open` reports await the moderators; `resolved` ones are handled.
   */
  @SerialName("state")
  public val state: AdminReportState,
  /**
   * How the report was resolved, present only while it is resolved. `actioned`: a moderator
   * took an action on the target. `dismissed`: there was nothing to act on.
   */
  @SerialName("outcome")
  public val outcome: AdminReportOutcome? = null,
  /**
   * When the report entered its state (when it was filed, resolved, or reopened).
   */
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The account of the moderator who handles the report; omitted when nobody does.
   */
  @SerialName("assignee_account_id")
  public val assigneeAccountId: String? = null,
  /**
   * The account that filed the report; omitted when the reader is the account reported,
   * who never learns who reported them.
   */
  @SerialName("reporter_account_id")
  public val reporterAccountId: String? = null,
  /**
   * The reporter, often the instance actor of a remote server; omitted when the account is
   * gone, and when the reader is the account reported.
   */
  @SerialName("reporter_account")
  public val reporterAccount: Account? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("target_account_id")
  public val targetAccountId: UUID,
  /**
   * The account reported, even when it is suspended or blocks the reporter; omitted when the
   * account is gone.
   */
  @SerialName("target_account")
  public val targetAccount: Account? = null,
  /**
   * The IDs of the target's posts that the report points at.
   */
  @SerialName("status_ids")
  public val statusIds: List<@Contextual UUID>,
  /**
   * The voice chat room that the report points at, whose host is the target; omitted for
   * none.
   */
  @SerialName("voice_room_id")
  public val voiceRoomId: String? = null,
  /**
   * The event that the report points at, whose organizer is the target; omitted for none.
   */
  @SerialName("event_id")
  public val eventId: String? = null,
  /**
   * The decision hook that filed the report; omitted for a report that a person or another server
   * filed. Such a report has the instance actor as its reporter, is not forwarded when it is
   * filed (a moderator may forward it later, without the plugin's note), and nobody is notified
   * when it is resolved.
   */
  @SerialName("hook_origin")
  public val hookOrigin: AdminReportHookOrigin? = null,
  /**
   * The IDs of the instance rules that the report cites, retired ones included; only a
   * report of the `violation` category cites rules.
   */
  @SerialName("rule_ids")
  public val ruleIds: List<@Contextual UUID>,
)
