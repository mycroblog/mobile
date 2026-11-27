package social.mycroblog.api.models

import kotlin.Double
import kotlin.Long
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The samples whose value is above the bound of the bucket before and at most `le`. The last
 * bucket has no `le`: it holds the samples above every bound.
 */
@Serializable
public data class UXMeasurementBucket(
  /**
   * The upper bound of the bucket, in the unit of the metric.
   */
  @SerialName("le")
  public val le: Double? = null,
  @SerialName("samples")
  public val samples: Long,
)
