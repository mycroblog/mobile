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
 * `test` for test credentials, with which nothing is charged, and `live` for live
 * credentials.
 */
@Serializable(with = PaymentStatusMode.Serializer::class)
public enum class PaymentStatusMode(
  public val `value`: String,
) {
  @SerialName("test")
  TEST("test"),
  @SerialName("live")
  LIVE("live"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<PaymentStatusMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.PaymentStatusMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: PaymentStatusMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): PaymentStatusMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, PaymentStatusMode> =
        entries.associateBy(PaymentStatusMode::value)

    public fun fromValue(`value`: String): PaymentStatusMode = mapping[value] ?: UNRECOGNIZED
  }
}
