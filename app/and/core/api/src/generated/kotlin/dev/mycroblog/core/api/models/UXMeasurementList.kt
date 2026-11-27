package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The UX measurements of a window of hours, by metric and then by most samples.
 */
@Serializable
public data class UXMeasurementList(
  /**
   * The start of the first hour of the window, in UTC.
   */
  @SerialName("since")
  public val since: Instant,
  @SerialName("items")
  public val items: List<UXMeasurementSummary>,
)
