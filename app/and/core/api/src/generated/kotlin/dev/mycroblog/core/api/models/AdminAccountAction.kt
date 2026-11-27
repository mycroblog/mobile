package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderation action on an account.
 */
@Serializable
public data class AdminAccountAction(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The account the action is on.
   */
  @SerialName("account_id")
  public val accountId: String,
  /**
   * `warn`: a notice only. `mark_statuses_sensitive` and `delete_statuses`: actions on
   * posts. `force_sensitive`: every attachment is shown as sensitive. `limit`: every post is
   * followers-only and stays off the public timelines. `restrict_posting`: no posting.
   * `restrict_interaction`: no following, favouriting, boosting, or replying.
   * `disable_login`: no logging in. `suspend`: all of these, and the profile and posts are
   * hidden. `ban`: a suspension that never ends, with the deletion of the account.
   */
  @SerialName("kind")
  public val kind: AdminAccountActionKind,
  /**
   * The kind of reason for an action.
   */
  @SerialName("reason_category")
  public val reasonCategory: AdminAccountActionCategory,
  /**
   * The reason shown to the account; omitted when there is none.
   */
  @SerialName("public_reason")
  public val publicReason: String? = null,
  /**
   * The moderators' note; omitted when there is none.
   */
  @SerialName("private_note")
  public val privateNote: String? = null,
  /**
   * The report the action follows from; omitted when there is none.
   */
  @SerialName("report_id")
  public val reportId: String? = null,
  /**
   * `moderator`: a moderator or the CLI.
   */
  @SerialName("issuer")
  public val issuer: AdminAccountActionIssuer,
  /**
   * The account of the moderator who took the action; omitted for the CLI and the
   * system.
   */
  @SerialName("issued_by_account_id")
  public val issuedByAccountId: String? = null,
  /**
   * Whether the account is told of the action.
   */
  @SerialName("notify_target")
  public val notifyTarget: Boolean,
  /**
   * `active`: in effect now. `expired`: it ended at `expires_at`. `lifted`: a moderator
   * lifted it.
   */
  @SerialName("state")
  public val state: ModerationActionState,
  @SerialName("issued_at")
  public val issuedAt: Instant,
  /**
   * When the action ends by itself; omitted for one that lasts until lifted.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * When the action was lifted; omitted until it is.
   */
  @SerialName("lifted_at")
  public val liftedAt: Instant? = null,
  /**
   * Why the action was lifted; omitted until it is or when no reason was given.
   */
  @SerialName("lift_reason")
  public val liftReason: String? = null,
  /**
   * The posts of the account that `delete_statuses` or `mark_statuses_sensitive` acts
   * on, in ID order; omitted for the other kinds.
   */
  @SerialName("status_ids")
  public val statusIds: List<@Contextual UUID>? = null,
)
