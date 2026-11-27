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

@Serializable(with = RelationshipSeveranceKind.Serializer::class)
public enum class RelationshipSeveranceKind(
  public val `value`: String,
) {
  @SerialName("domain_suspension")
  DOMAIN_SUSPENSION("domain_suspension"),
  @SerialName("account_suspension")
  ACCOUNT_SUSPENSION("account_suspension"),
  @SerialName("allowlist_cutoff")
  ALLOWLIST_CUTOFF("allowlist_cutoff"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RelationshipSeveranceKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.RelationshipSeveranceKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RelationshipSeveranceKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RelationshipSeveranceKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RelationshipSeveranceKind> =
        entries.associateBy(RelationshipSeveranceKind::value)

    public fun fromValue(`value`: String): RelationshipSeveranceKind = mapping[value] ?: UNRECOGNIZED
  }
}
