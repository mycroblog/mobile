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
 * The color of a role badge: the name of a pair of the web client's design tokens
 * for its background and its text, which keep their contrast in the light and the dark theme.
 * Server themes do not change them. The color is decoration; the badge's `label` carries its
 * meaning.
 */
@Serializable(with = BadgeColor.Serializer::class)
public enum class BadgeColor(
  public val `value`: String,
) {
  @SerialName("neutral")
  NEUTRAL("neutral"),
  @SerialName("red")
  RED("red"),
  @SerialName("orange")
  ORANGE("orange"),
  @SerialName("yellow")
  YELLOW("yellow"),
  @SerialName("green")
  GREEN("green"),
  @SerialName("teal")
  TEAL("teal"),
  @SerialName("blue")
  BLUE("blue"),
  @SerialName("purple")
  PURPLE("purple"),
  @SerialName("pink")
  PINK("pink"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BadgeColor> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.BadgeColor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BadgeColor) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BadgeColor {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BadgeColor> = entries.associateBy(BadgeColor::value)

    public fun fromValue(`value`: String): BadgeColor = mapping[value] ?: UNRECOGNIZED
  }
}
