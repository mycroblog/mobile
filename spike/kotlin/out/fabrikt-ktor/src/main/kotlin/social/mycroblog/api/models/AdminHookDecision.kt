package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What a decision hook answered about one subject. It holds no text of a post.
 */
@Serializable
public data class AdminHookDecision(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * A decision hook, where the server asks an external review before it accepts something:
   * `inbound.status` (posts from other servers), `inbound.follow` (follows from other servers),
   * `signup.check` (sign-ups), `media.check` (uploaded media), `local.status` (local posts),
   * `inbound.actor` (profiles of accounts on other servers), `local.profile` (local profiles).
   */
  @SerialName("hook")
  public val hook: HookName,
  /**
   * What identifies the subject for the hook, such as the URI of a received activity.
   */
  @SerialName("subject_key")
  public val subjectKey: String,
  /**
   * The account the subject belongs to; absent when there is none.
   */
  @SerialName("account_id")
  public val accountId: String? = null,
  /**
   * The mode of the hook when it made the decision.
   */
  @SerialName("mode")
  public val mode: HookDecisionMode,
  /**
   * What a decision records: a verdict (`allow`, `drop`, `flag`, `mark_sensitive`,
   * `require_approval`, `reject`), or a kind of failure, which is not a verdict: `timeout`, `error`,
   * `invalid` (the answer is outside the hook's vocabulary), `breaker_open` (the server skipped
   * the plugin after repeated failures).
   */
  @SerialName("outcome")
  public val outcome: HookOutcome,
  /**
   * The coded reason of a verdict.
   */
  @SerialName("reason")
  public val reason: HookReason? = null,
  /**
   * The plugin's short plain-text note for the moderators; absent when there is none.
   */
  @SerialName("note")
  public val note: String? = null,
  /**
   * How long the call took, in milliseconds.
   */
  @SerialName("latency_ms")
  public val latencyMs: Long,
  @SerialName("decided_at")
  public val decidedAt: Instant,
  /**
   * When a moderator canceled the decision; absent while it stands.
   */
  @SerialName("overridden_at")
  public val overriddenAt: Instant? = null,
  /**
   * For a `drop` of `inbound.status` or `inbound.follow`: until about when cancelling it processes the activity again,
   * which needs the document as it was received. After that, cancelling records the cancellation
   * and brings nothing back. Absent for any other decision: cancelling a `drop` of
   * `inbound.actor` fetches the account again from its server, which stores its present profile.
   */
  @SerialName("restorable_until")
  public val restorableUntil: Instant? = null,
  /**
   * Whether a moderator can cancel it here: a verdict in force that stopped or changed
   * something. A `reject` of `local.status` goes through its appeal instead.
   */
  @SerialName("overridable")
  public val overridable: Boolean,
)
