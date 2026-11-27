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
 * What a custom emoji request asks for: `create` a new local custom emoji,
 * or `image` a new image of a local emoji that the requester made.
 */
@Serializable(with = CustomEmojiRequestKind.Serializer::class)
public enum class CustomEmojiRequestKind(
  public val `value`: String,
) {
  @SerialName("create")
  CREATE("create"),
  @SerialName("image")
  IMAGE("image"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CustomEmojiRequestKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.CustomEmojiRequestKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CustomEmojiRequestKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CustomEmojiRequestKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CustomEmojiRequestKind> =
        entries.associateBy(CustomEmojiRequestKind::value)

    public fun fromValue(`value`: String): CustomEmojiRequestKind = mapping[value] ?: UNRECOGNIZED
  }
}
