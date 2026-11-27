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

@Serializable(with = ErrorGroupEventActor.Serializer::class)
public enum class ErrorGroupEventActor(
  public val `value`: String,
) {
  @SerialName("user")
  USER("user"),
  @SerialName("cli")
  CLI("cli"),
  @SerialName("system")
  SYSTEM("system"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ErrorGroupEventActor> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ErrorGroupEventActor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ErrorGroupEventActor) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ErrorGroupEventActor {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ErrorGroupEventActor> =
        entries.associateBy(ErrorGroupEventActor::value)

    public fun fromValue(`value`: String): ErrorGroupEventActor = mapping[value] ?: UNRECOGNIZED
  }
}
