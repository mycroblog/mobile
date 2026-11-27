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
 * The state of a custom emoji request: `pending` until an administrator decides it, then
 * `approved` or `rejected`; `withdrawn` by the requester before that; or `lapsed`, a request
 * for a new image of an emoji that was deleted.
 */
@Serializable(with = CustomEmojiRequestState.Serializer::class)
public enum class CustomEmojiRequestState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("approved")
  APPROVED("approved"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("withdrawn")
  WITHDRAWN("withdrawn"),
  @SerialName("lapsed")
  LAPSED("lapsed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CustomEmojiRequestState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.CustomEmojiRequestState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CustomEmojiRequestState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CustomEmojiRequestState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CustomEmojiRequestState> =
        entries.associateBy(CustomEmojiRequestState::value)

    public fun fromValue(`value`: String): CustomEmojiRequestState = mapping[value] ?: UNRECOGNIZED
  }
}
