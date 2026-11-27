package dev.mycroblog.core.api.models

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
 * How the totals place a payment. `amortized` (the default) spreads each
 * payment of a schedule longer than a month (every 2 or more months, every year, every 5 or
 * more weeks) by day over the days up to the next payment, with the cumulative rounding;
 * the other payments stay on their dates. `cash` places every payment on its date. Imported
 * usage charges spread by day over their billing period under both.
 */
@Serializable(with = CostBasis.Serializer::class)
public enum class CostBasis(
  public val `value`: String,
) {
  @SerialName("amortized")
  AMORTIZED("amortized"),
  @SerialName("cash")
  CASH("cash"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CostBasis> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.CostBasis", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CostBasis) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CostBasis {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CostBasis> = entries.associateBy(CostBasis::value)

    public fun fromValue(`value`: String): CostBasis = mapping[value] ?: UNRECOGNIZED
  }
}
