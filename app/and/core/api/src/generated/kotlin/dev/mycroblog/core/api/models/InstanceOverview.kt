package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The counts of the instance (`GET /instance/overview`), omitted while the instance setting
 * `public_counts` is off.
 */
@Serializable
public data class InstanceOverview(
  /**
   * How many members the instance has, how many public posts they made lately, and since when it
   * runs, as counted at `counted_at`. Omitted while the instance setting `public_counts` is off.
   */
  @SerialName("counts")
  public val counts: InstanceCounts? = null,
)
