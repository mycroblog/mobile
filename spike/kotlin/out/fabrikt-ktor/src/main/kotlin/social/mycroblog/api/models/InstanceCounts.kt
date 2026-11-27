package social.mycroblog.api.models

import kotlin.Int
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How many members the instance has, how many public posts they made lately, and since when it
 * runs, as counted at `counted_at`. Omitted while the instance setting `public_counts` is off.
 */
@Serializable
public data class InstanceCounts(
  /**
   * The local accounts that are not deleted, the instance's own actor aside.
   */
  @SerialName("members")
  public val members: Int,
  /**
   * The public posts of local accounts that are not deleted, in each of the 7 periods of 24
   * hours before `counted_at`, oldest first: the last item counts the posts of the 24 hours
   * up to `counted_at`.
   */
  @SerialName("daily_posts")
  public val dailyPosts: List<Int>,
  /**
   * When the instance was set up.
   */
  @SerialName("started_at")
  public val startedAt: Instant,
  /**
   * When the counts were taken.
   */
  @SerialName("counted_at")
  public val countedAt: Instant,
)
