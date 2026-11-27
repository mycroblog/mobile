package dev.mycroblog.core.api.models

import kotlin.Double
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One sample of how fast the web client responded.
 */
@Serializable
public data class ClientMeasurement(
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
   * The route ID, interaction kind, action ID, or call transport that `metric` names.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The value in milliseconds, or in percent for `call_packet_loss`.
   */
  @SerialName("value")
  public val `value`: Double,
)
