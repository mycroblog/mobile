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

@Serializable(with = VoiceSFUMigrateType.Serializer::class)
public enum class VoiceSFUMigrateType(
  public val `value`: String,
) {
  @SerialName("migrate")
  MIGRATE("migrate"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceSFUMigrateType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceSFUMigrateType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceSFUMigrateType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceSFUMigrateType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceSFUMigrateType> =
        entries.associateBy(VoiceSFUMigrateType::value)

    public fun fromValue(`value`: String): VoiceSFUMigrateType = mapping[value] ?: UNRECOGNIZED
  }
}
