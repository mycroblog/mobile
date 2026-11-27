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
 * The processing state: `processing` until the server has re-encoded the file,
 * then `ready`, or `failed` when the content could not be processed.
 */
@Serializable(with = MediaAttachmentState.Serializer::class)
public enum class MediaAttachmentState(
  public val `value`: String,
) {
  @SerialName("processing")
  PROCESSING("processing"),
  @SerialName("ready")
  READY("ready"),
  @SerialName("failed")
  FAILED("failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<MediaAttachmentState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.MediaAttachmentState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: MediaAttachmentState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): MediaAttachmentState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, MediaAttachmentState> =
        entries.associateBy(MediaAttachmentState::value)

    public fun fromValue(`value`: String): MediaAttachmentState = mapping[value] ?: UNRECOGNIZED
  }
}
