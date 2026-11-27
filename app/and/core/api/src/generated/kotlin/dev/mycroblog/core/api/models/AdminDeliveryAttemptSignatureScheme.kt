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
 * The signature scheme of the request that got the response; absent without a response.
 */
@Serializable(with = AdminDeliveryAttemptSignatureScheme.Serializer::class)
public enum class AdminDeliveryAttemptSignatureScheme(
  public val `value`: String,
) {
  @SerialName("rfc9421")
  RFC9421("rfc9421"),
  @SerialName("cavage")
  CAVAGE("cavage"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDeliveryAttemptSignatureScheme> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminDeliveryAttemptSignatureScheme", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDeliveryAttemptSignatureScheme) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDeliveryAttemptSignatureScheme {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDeliveryAttemptSignatureScheme> =
        entries.associateBy(AdminDeliveryAttemptSignatureScheme::value)

    public fun fromValue(`value`: String): AdminDeliveryAttemptSignatureScheme = mapping[value] ?: UNRECOGNIZED
  }
}
