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

@Serializable(with = ThemeErrorCode.Serializer::class)
public enum class ThemeErrorCode(
  public val `value`: String,
) {
  @SerialName("unknown_token")
  UNKNOWN_TOKEN("unknown_token"),
  @SerialName("duplicate_token")
  DUPLICATE_TOKEN("duplicate_token"),
  @SerialName("invalid_value")
  INVALID_VALUE("invalid_value"),
  @SerialName("out_of_range")
  OUT_OF_RANGE("out_of_range"),
  @SerialName("low_contrast")
  LOW_CONTRAST("low_contrast"),
  @SerialName("too_many_overrides")
  TOO_MANY_OVERRIDES("too_many_overrides"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ThemeErrorCode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ThemeErrorCode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ThemeErrorCode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ThemeErrorCode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ThemeErrorCode> = entries.associateBy(ThemeErrorCode::value)

    public fun fromValue(`value`: String): ThemeErrorCode = mapping[value] ?: UNRECOGNIZED
  }
}
