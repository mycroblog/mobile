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

@Serializable(with = PaymentStatusWarnings.Serializer::class)
public enum class PaymentStatusWarnings(
  public val `value`: String,
) {
  @SerialName("no_webhook")
  NO_WEBHOOK("no_webhook"),
  @SerialName("reconciliation_corrected")
  RECONCILIATION_CORRECTED("reconciliation_corrected"),
  @SerialName("duplicate_charge")
  DUPLICATE_CHARGE("duplicate_charge"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<PaymentStatusWarnings> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.PaymentStatusWarnings", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: PaymentStatusWarnings) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): PaymentStatusWarnings {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, PaymentStatusWarnings> =
        entries.associateBy(PaymentStatusWarnings::value)

    public fun fromValue(`value`: String): PaymentStatusWarnings = mapping[value] ?: UNRECOGNIZED
  }
}
