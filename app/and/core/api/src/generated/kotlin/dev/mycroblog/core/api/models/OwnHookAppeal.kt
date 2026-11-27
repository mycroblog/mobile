package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user's appeal of a post that the automated review refused.
 */
@Serializable
public data class OwnHookAppeal(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The decision appealed.
   */
  @SerialName("hook_decision_id")
  public val hookDecisionId: String,
  /**
   * Where an appeal of a refused post stands: `pending` until a moderator decides it,
   * `upheld` when the refusal stays, `overturned` when the moderator canceled it. After
   * `overturned` the user's posts are no longer reviewed, and the user posts again: the refused
   * post is not restored.
   */
  @SerialName("state")
  public val state: HookAppealState,
  @SerialName("submitted_at")
  public val submittedAt: Instant,
  /**
   * When a moderator upheld or overturned it; absent while it is `pending`.
   */
  @SerialName("decided_at")
  public val decidedAt: Instant? = null,
)
