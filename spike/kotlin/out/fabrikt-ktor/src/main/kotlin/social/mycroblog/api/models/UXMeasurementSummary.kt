package social.mycroblog.api.models

import kotlin.Double
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The samples of one metric and name over the window.
 */
@Serializable
public data class UXMeasurementSummary(
  /**
   * What a UX measurement of the web client measures, and so what its name names. The values are
   * milliseconds, except `call_packet_loss`, a percentage from 0 to 100.
   *
   * - `lcp`, `inp`, `long_frame`: the largest contentful paint, the interaction to next paint, and
   *   a long animation frame of a route, named by the route's ID (a pattern such as
   *   `/_app/statuses/$id`, never the URL).
   * - `transition`: a navigation, named by its interaction kind (`push`, `pop`, `modal`,
   *   `replace`, `none`).
   * - `press`: the time from a press to its response, named by the action ID (`post.reply`).
   * - `call_rtt`, `call_jitter`, `call_packet_loss`: the quality of a voice call, named by the
   *   call transport (`p2p` or `sfu`).
   */
  @SerialName("metric")
  public val metric: UXMetric,
  /**
   * The route ID, interaction kind, action ID, or call transport; `other` for the names past
   * the first 200 of the metric that a process of the server kept.
   */
  @SerialName("name")
  public val name: String,
  @SerialName("samples")
  public val samples: Long,
  /**
   * The upper bound of the bucket that holds the 75th percentile: at least three quarters of
   * the samples are at most this value. Absent when the 75th percentile is in the last bucket.
   */
  @SerialName("p75")
  public val p75: Double? = null,
  /**
   * The samples by bucket, from the lowest bound up.
   */
  @SerialName("buckets")
  public val buckets: List<UXMeasurementBucket>,
)
