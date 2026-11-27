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
 * The kind of a WebRTC signal.
 */
@Serializable(with = VoiceSignalKind.Serializer::class)
public enum class VoiceSignalKind(
  public val `value`: String,
) {
  @SerialName("offer")
  OFFER("offer"),
  @SerialName("answer")
  ANSWER("answer"),
  @SerialName("candidate")
  CANDIDATE("candidate"),
  @SerialName("restart")
  RESTART("restart"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceSignalKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.VoiceSignalKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceSignalKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceSignalKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceSignalKind> = entries.associateBy(VoiceSignalKind::value)

    public fun fromValue(`value`: String): VoiceSignalKind = mapping[value] ?: UNRECOGNIZED
  }
}
