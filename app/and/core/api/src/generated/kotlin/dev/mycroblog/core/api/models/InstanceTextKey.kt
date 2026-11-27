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

/**
 * The key of an instance text: the name (one line), the short and full descriptions,
 * the terms of service and the privacy policy (Markdown), the alternative texts of the
 * preview image and the thumbnail, and the commerce disclosures of the supporter plans
 * (Markdown, shown at `/about/commerce`; the server does not vouch for what they say).
 */
@Serializable(with = InstanceTextKey.Serializer::class)
public enum class InstanceTextKey(
  public val `value`: String,
) {
  @SerialName("name")
  NAME("name"),
  @SerialName("short_description")
  SHORT_DESCRIPTION("short_description"),
  @SerialName("description")
  DESCRIPTION("description"),
  @SerialName("terms")
  TERMS("terms"),
  @SerialName("privacy_policy")
  PRIVACY_POLICY("privacy_policy"),
  @SerialName("preview_image_alt")
  PREVIEW_IMAGE_ALT("preview_image_alt"),
  @SerialName("thumbnail_alt")
  THUMBNAIL_ALT("thumbnail_alt"),
  @SerialName("commerce_disclosure")
  COMMERCE_DISCLOSURE("commerce_disclosure"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceTextKey> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.InstanceTextKey", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceTextKey) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceTextKey {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceTextKey> = entries.associateBy(InstanceTextKey::value)

    public fun fromValue(`value`: String): InstanceTextKey = mapping[value] ?: UNRECOGNIZED
  }
}
