package dev.mycroblog.core.api.models

import kotlin.Long
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The occurrences of an error group in one hour.
 */
@Serializable
public data class ErrorGroupHourCount(
  /**
   * The start of the hour, in UTC.
   */
  @SerialName("hour")
  public val hour: Instant,
  @SerialName("count")
  public val count: Long,
  /**
   * The version of the server of the last occurrence of the hour.
   */
  @SerialName("last_version")
  public val lastVersion: String,
)
