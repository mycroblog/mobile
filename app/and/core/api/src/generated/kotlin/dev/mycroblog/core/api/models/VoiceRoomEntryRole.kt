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
 * Whether the account hosts the room or speaks in it.
 */
@Serializable(with = VoiceRoomEntryRole.Serializer::class)
public enum class VoiceRoomEntryRole(
  public val `value`: String,
) {
  @SerialName("host")
  HOST("host"),
  @SerialName("speaker")
  SPEAKER("speaker"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceRoomEntryRole> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceRoomEntryRole", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceRoomEntryRole) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceRoomEntryRole {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceRoomEntryRole> =
        entries.associateBy(VoiceRoomEntryRole::value)

    public fun fromValue(`value`: String): VoiceRoomEntryRole = mapping[value] ?: UNRECOGNIZED
  }
}
