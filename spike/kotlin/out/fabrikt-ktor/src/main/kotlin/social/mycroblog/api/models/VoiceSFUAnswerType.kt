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

@Serializable(with = VoiceSFUAnswerType.Serializer::class)
public enum class VoiceSFUAnswerType(
  public val `value`: String,
) {
  @SerialName("answer")
  ANSWER("answer"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceSFUAnswerType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.VoiceSFUAnswerType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceSFUAnswerType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceSFUAnswerType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceSFUAnswerType> =
        entries.associateBy(VoiceSFUAnswerType::value)

    public fun fromValue(`value`: String): VoiceSFUAnswerType = mapping[value] ?: UNRECOGNIZED
  }
}
