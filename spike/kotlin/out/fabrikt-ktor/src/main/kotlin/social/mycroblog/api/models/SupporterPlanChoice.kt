package social.mycroblog.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A plan and how often its subscription renews.
 */
@Serializable
public data class SupporterPlanChoice(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("plan_id")
  public val planId: UUID,
  /**
   * How often a subscription renews.
   */
  @SerialName("interval")
  public val interval: SupporterInterval,
)
