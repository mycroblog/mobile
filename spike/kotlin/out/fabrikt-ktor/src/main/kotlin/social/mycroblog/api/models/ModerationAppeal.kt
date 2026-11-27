package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's appeal of an action on their account. It never names the
 * moderator who decided it.
 */
@Serializable
public data class ModerationAppeal(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
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
   * When the moderators decided the appeal; omitted while pending.
   */
  @SerialName("decided_at")
  public val decidedAt: Instant? = null,
  /**
   * The moderators' explanation of the decision, as plain text; omitted when there is none.
   */
  @SerialName("decision_reason")
  public val decisionReason: String? = null,
)
