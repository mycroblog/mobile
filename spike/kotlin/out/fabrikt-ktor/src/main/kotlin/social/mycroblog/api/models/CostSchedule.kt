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
 * The unit of a cost item's payment schedule: `once` (one payment on the start
 * date), or every `interval` `weeks`, `months`, or `years`, counted from the start date. A
 * monthly payment on a day that a month does not have falls on the month's last day.
 */
@Serializable(with = CostSchedule.Serializer::class)
public enum class CostSchedule(
  public val `value`: String,
) {
  @SerialName("once")
  ONCE("once"),
  @SerialName("weeks")
  WEEKS("weeks"),
  @SerialName("months")
  MONTHS("months"),
  @SerialName("years")
  YEARS("years"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CostSchedule> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.CostSchedule", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CostSchedule) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CostSchedule {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CostSchedule> = entries.associateBy(CostSchedule::value)

    public fun fromValue(`value`: String): CostSchedule = mapping[value] ?: UNRECOGNIZED
  }
}
