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
 * Whether the emoji is shown; a disabled one stays `:shortcode:` text.
 */
@Serializable(with = CustomEmojiState.Serializer::class)
public enum class CustomEmojiState(
  public val `value`: String,
) {
  @SerialName("enabled")
  ENABLED("enabled"),
  @SerialName("disabled")
  DISABLED("disabled"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CustomEmojiState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.CustomEmojiState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CustomEmojiState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CustomEmojiState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CustomEmojiState> =
        entries.associateBy(CustomEmojiState::value)

    public fun fromValue(`value`: String): CustomEmojiState = mapping[value] ?: UNRECOGNIZED
  }
}
