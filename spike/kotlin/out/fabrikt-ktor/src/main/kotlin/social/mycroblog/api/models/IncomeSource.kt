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
 * Where a money movement comes from: a subscription to a supporter plan (`subscription`), a tip
 * (`one_time`), or neither (`unknown`: a payment taken outside this server, or a fee of no
 * subscription). A refund, a chargeback, or a reversal has the source of its payment.
 */
@Serializable(with = IncomeSource.Serializer::class)
public enum class IncomeSource(
  public val `value`: String,
) {
  @SerialName("subscription")
  SUBSCRIPTION("subscription"),
  @SerialName("one_time")
  ONE_TIME("one_time"),
  @SerialName("unknown")
  UNKNOWN("unknown"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<IncomeSource> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.IncomeSource", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: IncomeSource) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): IncomeSource {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, IncomeSource> = entries.associateBy(IncomeSource::value)

    public fun fromValue(`value`: String): IncomeSource = mapping[value] ?: UNRECOGNIZED
  }
}
