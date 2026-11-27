package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder

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
@Serializable(with = UXMetric.Serializer::class)
public enum class UXMetric(
  public val `value`: String,
) {
  @SerialName("lcp")
  LCP("lcp"),
  @SerialName("inp")
  INP("inp"),
  @SerialName("long_frame")
  LONG_FRAME("long_frame"),
  @SerialName("transition")
  TRANSITION("transition"),
  @SerialName("press")
  PRESS("press"),
  @SerialName("call_rtt")
  CALL_RTT("call_rtt"),
  @SerialName("call_jitter")
  CALL_JITTER("call_jitter"),
  @SerialName("call_packet_loss")
  CALL_PACKET_LOSS("call_packet_loss"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<UXMetric> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.UXMetric", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: UXMetric) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): UXMetric {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, UXMetric> = entries.associateBy(UXMetric::value)

    public fun fromValue(`value`: String): UXMetric = mapping[value] ?: UNRECOGNIZED
  }
}
