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
 * The periods of the totals: ISO 8601 weeks (Monday to Sunday), calendar months, or calendar
 * years, cut by date.
 */
@Serializable(with = CostGranularity.Serializer::class)
public enum class CostGranularity(
  public val `value`: String,
) {
  @SerialName("week")
  WEEK("week"),
  @SerialName("month")
  MONTH("month"),
  @SerialName("year")
  YEAR("year"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CostGranularity> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.CostGranularity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CostGranularity) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CostGranularity {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CostGranularity> = entries.associateBy(CostGranularity::value)

    public fun fromValue(`value`: String): CostGranularity = mapping[value] ?: UNRECOGNIZED
  }
}
