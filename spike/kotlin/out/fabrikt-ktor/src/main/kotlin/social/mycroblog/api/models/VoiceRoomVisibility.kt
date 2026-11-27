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
 * Who sees a voice chat room and may join it: `local` every local user, `followers` the
 * followers of the current host, `following` those whom the host follows, `mutuals` those who
 * follow the host and whom the host follows, and `invited` only the invited users. The host, the co-hosts, the participants, and the
 * invited users always see it, and a block with the host or a co-host always hides it.
 */
@Serializable(with = VoiceRoomVisibility.Serializer::class)
public enum class VoiceRoomVisibility(
  public val `value`: String,
) {
  @SerialName("local")
  LOCAL("local"),
  @SerialName("followers")
  FOLLOWERS("followers"),
  @SerialName("following")
  FOLLOWING("following"),
  @SerialName("mutuals")
  MUTUALS("mutuals"),
  @SerialName("invited")
  INVITED("invited"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceRoomVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.VoiceRoomVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceRoomVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceRoomVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceRoomVisibility> =
        entries.associateBy(VoiceRoomVisibility::value)

    public fun fromValue(`value`: String): VoiceRoomVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
