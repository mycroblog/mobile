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
 * Whether a participant speaks or listens.
 */
@Serializable(with = VoiceRole.Serializer::class)
public enum class VoiceRole(
  public val `value`: String,
) {
  @SerialName("speaker")
  SPEAKER("speaker"),
  @SerialName("listener")
  LISTENER("listener"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceRole> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceRole", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceRole) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceRole {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceRole> = entries.associateBy(VoiceRole::value)

    public fun fromValue(`value`: String): VoiceRole = mapping[value] ?: UNRECOGNIZED
  }
}
