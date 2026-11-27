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

/**
 * Who may open voice chat rooms: `everyone` (every local user who is not limited) or
 * `permitted` (only the users whose roles have `start_voice_rooms`). `permitted` while the
 * voice chat is off.
 */
@Serializable(with = InstanceVoiceRoomStarters.Serializer::class)
public enum class InstanceVoiceRoomStarters(
  public val `value`: String,
) {
  @SerialName("everyone")
  EVERYONE("everyone"),
  @SerialName("permitted")
  PERMITTED("permitted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceVoiceRoomStarters> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceVoiceRoomStarters", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceVoiceRoomStarters) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceVoiceRoomStarters {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceVoiceRoomStarters> =
        entries.associateBy(InstanceVoiceRoomStarters::value)

    public fun fromValue(`value`: String): InstanceVoiceRoomStarters = mapping[value] ?: UNRECOGNIZED
  }
}
