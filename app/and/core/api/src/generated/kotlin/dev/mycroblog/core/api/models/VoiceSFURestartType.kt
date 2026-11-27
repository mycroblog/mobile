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

@Serializable(with = VoiceSFURestartType.Serializer::class)
public enum class VoiceSFURestartType(
  public val `value`: String,
) {
  @SerialName("restart")
  RESTART("restart"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceSFURestartType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceSFURestartType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceSFURestartType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceSFURestartType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceSFURestartType> =
        entries.associateBy(VoiceSFURestartType::value)

    public fun fromValue(`value`: String): VoiceSFURestartType = mapping[value] ?: UNRECOGNIZED
  }
}
