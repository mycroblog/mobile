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
 * The rule that the amounts of the tips break: `count` (1 to 6 different amounts in one
 * currency), `currency` (a currency that the tips and the payment provider take: JPY or USD),
 * or `range` (each from ¥100 to ¥100,000 or from US$1 to US$1,000, and at least the provider's
 * smallest one-time charge).
 */
@Serializable(with = TipAmountsProblemReason.Serializer::class)
public enum class TipAmountsProblemReason(
  public val `value`: String,
) {
  @SerialName("count")
  COUNT("count"),
  @SerialName("currency")
  CURRENCY("currency"),
  @SerialName("range")
  RANGE("range"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<TipAmountsProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.TipAmountsProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: TipAmountsProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): TipAmountsProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, TipAmountsProblemReason> =
        entries.associateBy(TipAmountsProblemReason::value)

    public fun fromValue(`value`: String): TipAmountsProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
