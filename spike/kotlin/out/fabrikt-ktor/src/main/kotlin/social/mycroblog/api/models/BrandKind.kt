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
 * A brand image of the instance: the square `icon` (the favicon, the app icons, and the
 * instance's avatar), the wide `logo` (a wordmark that includes the instance's name), or the
 * `logo_dark` for dark backgrounds (the `logo` is used there without it).
 */
@Serializable(with = BrandKind.Serializer::class)
public enum class BrandKind(
  public val `value`: String,
) {
  @SerialName("icon")
  ICON("icon"),
  @SerialName("logo")
  LOGO("logo"),
  @SerialName("logo_dark")
  LOGO_DARK("logo_dark"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BrandKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.BrandKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BrandKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BrandKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BrandKind> = entries.associateBy(BrandKind::value)

    public fun fromValue(`value`: String): BrandKind = mapping[value] ?: UNRECOGNIZED
  }
}
