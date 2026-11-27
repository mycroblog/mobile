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
 * Why a custom emoji request cannot be decided or withdrawn. `not_pending`: it was already
 * approved, rejected, withdrawn, or lapsed. `requester_cannot_create`: the requester no longer
 * holds `create_custom_emojis` or can no longer use the server. `shortcode_taken`: a local
 * emoji has the shortcode of the request. The last two can only be rejected.
 */
@Serializable(with = CustomEmojiRequestConflictReason.Serializer::class)
public enum class CustomEmojiRequestConflictReason(
  public val `value`: String,
) {
  @SerialName("not_pending")
  NOT_PENDING("not_pending"),
  @SerialName("requester_cannot_create")
  REQUESTER_CANNOT_CREATE("requester_cannot_create"),
  @SerialName("shortcode_taken")
  SHORTCODE_TAKEN("shortcode_taken"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CustomEmojiRequestConflictReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.CustomEmojiRequestConflictReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CustomEmojiRequestConflictReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CustomEmojiRequestConflictReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CustomEmojiRequestConflictReason> =
        entries.associateBy(CustomEmojiRequestConflictReason::value)

    public fun fromValue(`value`: String): CustomEmojiRequestConflictReason = mapping[value] ?: UNRECOGNIZED
  }
}
