package dev.mycroblog.core.api.models

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

@Serializable(with = BrandErrorCode.Serializer::class)
public enum class BrandErrorCode(
  public val `value`: String,
) {
  @SerialName("malformed")
  MALFORMED("malformed"),
  @SerialName("doctype")
  DOCTYPE("doctype"),
  @SerialName("not_svg")
  NOT_SVG("not_svg"),
  @SerialName("no_viewbox")
  NO_VIEWBOX("no_viewbox"),
  @SerialName("too_large")
  TOO_LARGE("too_large"),
  @SerialName("too_many_elements")
  TOO_MANY_ELEMENTS("too_many_elements"),
  @SerialName("too_deep")
  TOO_DEEP("too_deep"),
  @SerialName("too_many_uses")
  TOO_MANY_USES("too_many_uses"),
  @SerialName("aspect_ratio")
  ASPECT_RATIO("aspect_ratio"),
  @SerialName("text")
  TEXT("text"),
  @SerialName("unsafe_css")
  UNSAFE_CSS("unsafe_css"),
  @SerialName("missing_rendition")
  MISSING_RENDITION("missing_rendition"),
  @SerialName("unexpected_rendition")
  UNEXPECTED_RENDITION("unexpected_rendition"),
  @SerialName("invalid_png")
  INVALID_PNG("invalid_png"),
  @SerialName("dimensions")
  DIMENSIONS("dimensions"),
  @SerialName("not_opaque")
  NOT_OPAQUE("not_opaque"),
  @SerialName("invalid_color")
  INVALID_COLOR("invalid_color"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BrandErrorCode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.BrandErrorCode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BrandErrorCode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BrandErrorCode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BrandErrorCode> = entries.associateBy(BrandErrorCode::value)

    public fun fromValue(`value`: String): BrandErrorCode = mapping[value] ?: UNRECOGNIZED
  }
}
