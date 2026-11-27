package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What a draft automatic deletion policy would delete now: how many of the user's posts, up
 * to a cap, and the oldest of them.
 */
@Serializable
public data class AutoDeletePreview(
  /**
   * How many posts the policy would delete, at most 1000.
   */
  @SerialName("count")
  public val count: Int,
  /**
   * Whether the policy would delete more posts than `count`.
   */
  @SerialName("more")
  public val more: Boolean,
  /**
   * Up to 3 of those posts, oldest first.
   */
  @SerialName("examples")
  public val examples: List<Status>,
)
