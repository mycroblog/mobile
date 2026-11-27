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
 * The kind of media. An accepted upload is an `image`, a `video`, or an `audio` file;
 * a file that failed processing is `unknown`. `gifv` is a remote video that plays like
 * an animated image: muted, looped, without controls.
 */
@Serializable(with = MediaAttachmentType.Serializer::class)
public enum class MediaAttachmentType(
  public val `value`: String,
) {
  @SerialName("image")
  IMAGE("image"),
  @SerialName("gifv")
  GIFV("gifv"),
  @SerialName("video")
  VIDEO("video"),
  @SerialName("audio")
  AUDIO("audio"),
  @SerialName("unknown")
  UNKNOWN("unknown"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<MediaAttachmentType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.MediaAttachmentType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: MediaAttachmentType) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): MediaAttachmentType {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, MediaAttachmentType> =
        entries.associateBy(MediaAttachmentType::value)

    public fun fromValue(`value`: String): MediaAttachmentType = mapping[value] ?: UNRECOGNIZED
  }
}
