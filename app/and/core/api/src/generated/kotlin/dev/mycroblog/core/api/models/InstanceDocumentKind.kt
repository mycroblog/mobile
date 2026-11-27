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
 * The kind of an instance document: the terms of service, the privacy policy, and the
 * commerce disclosures of the supporter plans. Each is the instance text of the same name
 * (`terms`, `privacy_policy`, `commerce_disclosure`).
 */
@Serializable(with = InstanceDocumentKind.Serializer::class)
public enum class InstanceDocumentKind(
  public val `value`: String,
) {
  @SerialName("terms")
  TERMS("terms"),
  @SerialName("privacy_policy")
  PRIVACY_POLICY("privacy_policy"),
  @SerialName("commerce_disclosure")
  COMMERCE_DISCLOSURE("commerce_disclosure"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceDocumentKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.InstanceDocumentKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceDocumentKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceDocumentKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceDocumentKind> =
        entries.associateBy(InstanceDocumentKind::value)

    public fun fromValue(`value`: String): InstanceDocumentKind = mapping[value] ?: UNRECOGNIZED
  }
}
