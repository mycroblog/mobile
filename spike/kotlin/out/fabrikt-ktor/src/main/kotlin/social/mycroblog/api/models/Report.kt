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
 * A report that the user filed. It never names the reporter, and tells only whether the
 * moderators resolved it, never how.
 */
@Serializable
public data class Report(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Why the account is reported. `spam`: spam or unwanted promotion. `legal`: content that
   * is illegal. `violation`: it breaks the instance rules that `rule_ids` cite. `other`:
   * anything else.
   */
  @SerialName("category")
  public val category: ReportCategory,
  /**
   * The comment as plain text.
   */
  @SerialName("comment")
  public val comment: String,
  /**
   * Whether the report is to be forwarded to the target's server.
   */
  @SerialName("forwarded")
  public val forwarded: Boolean,
  @SerialName("filed_at")
  public val filedAt: Instant,
  /**
   * The account reported, as the user would see it through `getAccount`; omitted when that
   * answers 404, because the account is suspended, deactivated, deleted, or blocks the user.
   */
  @SerialName("target_account")
  public val targetAccount: Account? = null,
  @SerialName("status_ids")
  public val statusIds: List<@Contextual UUID>,
  @SerialName("rule_ids")
  public val ruleIds: List<@Contextual UUID>,
  /**
   * The voice chat room reported; omitted for none.
   */
  @SerialName("voice_room_id")
  public val voiceRoomId: String? = null,
  /**
   * The event reported; omitted for none.
   */
  @SerialName("event_id")
  public val eventId: String? = null,
  /**
   * `open`: the report awaits the moderators. `resolved`: the moderators handled it.
   */
  @SerialName("state")
  public val state: ReportState,
)
