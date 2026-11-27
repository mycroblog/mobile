package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A rule of the instance. Its ID stays the same across revisions, so reports keep
 * citing it.
 */
@Serializable
public data class AdminRule(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The sort key of the rule; the rules in effect are listed by it. Retiring a rule
   * leaves a gap until the next move.
   */
  @SerialName("position")
  public val position: Int,
  /**
   * The rule, on one line, as plain text.
   */
  @SerialName("text")
  public val text: String,
  /**
   * An explanation of the rule, as plain text; omitted when there is none.
   */
  @SerialName("hint")
  public val hint: String? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the current revision was recorded.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
  /**
   * When the rule was retired; omitted for a rule in effect.
   */
  @SerialName("retired_at")
  public val retiredAt: Instant? = null,
)
