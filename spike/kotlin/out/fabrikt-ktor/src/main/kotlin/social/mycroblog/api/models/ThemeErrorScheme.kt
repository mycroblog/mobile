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

@Serializable(with = ThemeErrorScheme.Serializer::class)
public enum class ThemeErrorScheme(
  public val `value`: String,
) {
  @SerialName("light")
  LIGHT("light"),
  @SerialName("dark")
  DARK("dark"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ThemeErrorScheme> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ThemeErrorScheme", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ThemeErrorScheme) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ThemeErrorScheme {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ThemeErrorScheme> =
        entries.associateBy(ThemeErrorScheme::value)

    public fun fromValue(`value`: String): ThemeErrorScheme = mapping[value] ?: UNRECOGNIZED
  }
}
