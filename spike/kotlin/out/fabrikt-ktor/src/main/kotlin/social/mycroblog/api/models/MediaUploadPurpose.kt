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
 * What the file is for, which picks the sizes of its variants: an `attachment` of a
 * post (within 640 × 360, 1920 × 1080, and 3840 × 2160 pixels of area), an `avatar`
 * (cropped to squares of 96, 192, and 400 pixels around the focal point), a `header`
 * (within 750 × 250, 1500 × 500, and 3000 × 1000), or a custom `emoji` (within 64,
 * 128, and 256 pixels).
 */
@Serializable(with = MediaUploadPurpose.Serializer::class)
public enum class MediaUploadPurpose(
  public val `value`: String,
) {
  @SerialName("attachment")
  ATTACHMENT("attachment"),
  @SerialName("avatar")
  AVATAR("avatar"),
  @SerialName("header")
  HEADER("header"),
  @SerialName("emoji")
  EMOJI("emoji"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<MediaUploadPurpose> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.MediaUploadPurpose", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: MediaUploadPurpose) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): MediaUploadPurpose {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, MediaUploadPurpose> =
        entries.associateBy(MediaUploadPurpose::value)

    public fun fromValue(`value`: String): MediaUploadPurpose = mapping[value] ?: UNRECOGNIZED
  }
}
