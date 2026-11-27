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
 * Who may become a speaker without asking: `everyone`, `followers` of the current host,
 * `following` (those whom the host follows), `mutuals` with the host, or `manual` (nobody: the host and the co-hosts let them speak).
 */
@Serializable(with = VoiceRoomSpeakers.Serializer::class)
public enum class VoiceRoomSpeakers(
  public val `value`: String,
) {
  @SerialName("everyone")
  EVERYONE("everyone"),
  @SerialName("followers")
  FOLLOWERS("followers"),
  @SerialName("following")
  FOLLOWING("following"),
  @SerialName("mutuals")
  MUTUALS("mutuals"),
  @SerialName("manual")
  MANUAL("manual"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceRoomSpeakers> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.VoiceRoomSpeakers", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceRoomSpeakers) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceRoomSpeakers {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceRoomSpeakers> =
        entries.associateBy(VoiceRoomSpeakers::value)

    public fun fromValue(`value`: String): VoiceRoomSpeakers = mapping[value] ?: UNRECOGNIZED
  }
}
