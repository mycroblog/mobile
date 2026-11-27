package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A local user's appeal of an action on their account, as the moderators see it, with the
 * action.
 */
@Serializable
public data class AdminAppeal(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The account that appealed, which the action is on.
   */
  @SerialName("account_id")
  public val accountId: String,
  /**
   * What the user wrote, as plain text.
   */
  @SerialName("text")
  public val text: String,
  /**
   * Where an appeal stands: `pending` awaits the moderators, `upheld` kept the action, and
   * `overturned` lifted it.
   */
  @SerialName("state")
  public val state: ModerationAppealState,
  @SerialName("submitted_at")
  public val submittedAt: Instant,
  /**
   * A moderation action on an account.
   */
  @SerialName("action")
  public val action: AdminAccountAction,
  /**
   * When a moderator decided the appeal; omitted while pending.
   */
  @SerialName("decided_at")
  public val decidedAt: Instant? = null,
  /**
   * The moderators' explanation of the decision to the user; omitted when there is none.
   */
  @SerialName("decision_reason")
  public val decisionReason: String? = null,
  /**
   * The account of the moderator who decided; omitted while pending and for a decision
   * from the command line.
   */
  @SerialName("decided_by_account_id")
  public val decidedByAccountId: String? = null,
)
