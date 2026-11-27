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
 * Whether the errors of a group are panics (and uncaught errors of the web client) or errors.
 */
@Serializable(with = ErrorGroupKind.Serializer::class)
public enum class ErrorGroupKind(
  public val `value`: String,
) {
  @SerialName("panic")
  PANIC("panic"),
  @SerialName("error")
  ERROR("error"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ErrorGroupKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ErrorGroupKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ErrorGroupKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ErrorGroupKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ErrorGroupKind> = entries.associateBy(ErrorGroupKind::value)

    public fun fromValue(`value`: String): ErrorGroupKind = mapping[value] ?: UNRECOGNIZED
  }
}
