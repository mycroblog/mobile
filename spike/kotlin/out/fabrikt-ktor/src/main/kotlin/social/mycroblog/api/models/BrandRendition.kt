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
 * A PNG rendition of a brand image, drawn from its SVG for the places that take no SVG:
 * `favicon-32` and `favicon-48` (32 and 48 pixels), `apple-touch-180` (180 pixels, opaque, the
 * iOS home screen), `pwa-192` and `pwa-512` (the app icons), and `maskable-512` (512 pixels,
 * opaque, the mark within the middle 80%) of the icon; `logo-mail` of the logo and
 * `logo-dark-mail` of the dark logo (96 pixels high and as wide as the aspect ratio makes it, up
 * to 600 pixels, which then sets the height; a pixel off is accepted), shown in emails; and
 * `preview-1200x630` of the logo (1200 × 630 pixels, opaque), the link preview image when the
 * instance has no `preview_image`.
 */
@Serializable(with = BrandRendition.Serializer::class)
public enum class BrandRendition(
  public val `value`: String,
) {
  @SerialName("favicon-32")
  FAVICON_32("favicon-32"),
  @SerialName("favicon-48")
  FAVICON_48("favicon-48"),
  @SerialName("apple-touch-180")
  APPLE_TOUCH_180("apple-touch-180"),
  @SerialName("pwa-192")
  PWA_192("pwa-192"),
  @SerialName("pwa-512")
  PWA_512("pwa-512"),
  @SerialName("maskable-512")
  MASKABLE_512("maskable-512"),
  @SerialName("logo-mail")
  LOGO_MAIL("logo-mail"),
  @SerialName("logo-dark-mail")
  LOGO_DARK_MAIL("logo-dark-mail"),
  @SerialName("preview-1200x630")
  PREVIEW_1200X630("preview-1200x630"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BrandRendition> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.BrandRendition", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BrandRendition) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BrandRendition {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BrandRendition> = entries.associateBy(BrandRendition::value)

    public fun fromValue(`value`: String): BrandRendition = mapping[value] ?: UNRECOGNIZED
  }
}
