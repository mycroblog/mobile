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
 * The kind of a money movement of the payment provider: `payment` of a
 * subscription, `refund` and `chargeback` of a payment (a negative gross), the
 * `chargeback_reversal` that gives a chargeback back, and a `fee` of the provider that
 * belongs to no payment (a zero gross and a negative net).
 */
@Serializable(with = IncomeKind.Serializer::class)
public enum class IncomeKind(
  public val `value`: String,
) {
  @SerialName("payment")
  PAYMENT("payment"),
  @SerialName("refund")
  REFUND("refund"),
  @SerialName("chargeback")
  CHARGEBACK("chargeback"),
  @SerialName("chargeback_reversal")
  CHARGEBACK_REVERSAL("chargeback_reversal"),
  @SerialName("fee")
  FEE("fee"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<IncomeKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.IncomeKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: IncomeKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): IncomeKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, IncomeKind> = entries.associateBy(IncomeKind::value)

    public fun fromValue(`value`: String): IncomeKind = mapping[value] ?: UNRECOGNIZED
  }
}
