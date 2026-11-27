package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A report of an account to the moderators.
 */
@Serializable
public data class CreateReportRequest(
  /**
   * The account reported, local or remote.
   */
  @SerialName("account_id")
  public val accountId: String,
  /**
   * Posts of the account that the user can see, up to 20.
   */
  @SerialName("status_ids")
  public val statusIds: List<@Contextual UUID>? = null,
  /**
   * Why the account is reported. `spam`: spam or unwanted promotion. `legal`: content that
   * is illegal. `violation`: it breaks the instance rules that `rule_ids` cite. `other`:
   * anything else.
   */
  @SerialName("category")
  public val category: ReportCategory,
  /**
   * Instance rules in effect that the account broke, up to 20; only with the
   * `violation` category.
   */
  @SerialName("rule_ids")
  public val ruleIds: List<@Contextual UUID>? = null,
  /**
   * A comment for the moderators as plain text.
   */
  @SerialName("comment")
  public val comment: String? = null,
  /**
   * A voice chat room open on this server that the user can see; `account_id` is then its
   * host. Such a report points at no posts and is never forwarded.
   */
  @SerialName("voice_room_id")
  public val voiceRoomId: String? = null,
  /**
   * An event on this server that the user can see; `account_id` is then its organizer. Such
   * a report points at no posts and no voice chat room, and is never forwarded. The
   * moderators see the settings of the event as the user saw them.
   */
  @SerialName("event_id")
  public val eventId: String? = null,
  /**
   * Asks to forward the report to the server of a remote account, without naming the
   * reporter. It means nothing for a local account.
   */
  @SerialName("forward")
  public val forward: Boolean = false,
)
