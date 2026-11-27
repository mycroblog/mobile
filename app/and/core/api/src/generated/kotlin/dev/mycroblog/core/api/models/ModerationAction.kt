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
 * A moderation action on the authenticated user's own account, as the
 * user sees it. It never carries the moderators' private note, the report the action
 * follows from, or who filed the report or issued the action.
 */
@Serializable
public data class ModerationAction(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
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
   * What the action does, in the user's language.
   */
  @SerialName("kind_label")
  public val kindLabel: String,
  /**
   * The kind of reason for an action.
   */
  @SerialName("reason_category")
  public val reasonCategory: AdminAccountActionCategory,
  /**
   * The reason category, in the user's language.
   */
  @SerialName("reason_category_label")
  public val reasonCategoryLabel: String,
  /**
   * The moderators' explanation to the user, as plain text; omitted when there is none.
   */
  @SerialName("public_reason")
  public val publicReason: String? = null,
  /**
   * The posts that an action on posts acted on; empty for an action on the whole account.
   */
  @SerialName("status_ids")
  public val statusIds: List<@Contextual UUID>,
  /**
   * `active`: in effect now. `expired`: it ended at `expires_at`. `lifted`: a moderator
   * lifted it.
   */
  @SerialName("state")
  public val state: ModerationActionState,
  @SerialName("issued_at")
  public val issuedAt: Instant,
  /**
   * When the action ends by itself; omitted when it lasts until it is lifted.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * When a moderator lifted the action; omitted unless it is lifted.
   */
  @SerialName("lifted_at")
  public val liftedAt: Instant? = null,
  /**
   * Whether the user can appeal the action now (`POST /moderation/actions/{id}/appeal`):
   * it has no appeal yet, `appeal_deadline` has not passed, and for a `ban` the account
   * is not deleted.
   */
  @SerialName("appealable")
  public val appealable: Boolean,
  /**
   * The last moment the action can be appealed: the instance's appeal window from the
   * action, and for a `ban` no later than the deletion of the account.
   */
  @SerialName("appeal_deadline")
  public val appealDeadline: Instant? = null,
  /**
   * The authenticated user's appeal of an action on their account. It never names the
   * moderator who decided it.
   */
  @SerialName("appeal")
  public val appeal: ModerationAppeal? = null,
)
