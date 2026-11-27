package social.mycroblog.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The new place of a rule.
 */
@Serializable
public data class MoveInstanceRuleRequest(
  /**
   * The place among the rules in effect, from 1.
   */
  @SerialName("position")
  public val position: Int,
)
