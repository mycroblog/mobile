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
 * Why a custom emoji is refused. `invalid_shortcode`: not 2 to 30 letters, digits, and
 * underscores. `shortcode_taken`: a local emoji has the shortcode, or a pending request reserves it. `invalid_alias`: an
 * alias breaks the rules of a shortcode, or there are more than 10. `invalid_category`:
 * the category is longer than 64 characters or not text. `media_not_found`: the media
 * file is not an upload of the user. `media_not_ready`: it has not finished processing.
 * `unsupported_format`: it is not a PNG, a GIF, or a WebP. `too_large`: it is larger
 * than `custom_emoji_max_bytes`. `too_big`: it is larger than 1,024 pixels on a side.
 * `remote_emoji`: the operation is for local emoji only. `local_emoji`: the operation is
 * for remote emoji only. `fetch_failed`: the image of the remote emoji could not be
 * fetched (its server did not return it, or the instance rejects the media of its domain).
 * `limit_reached`: the approved and pending requests to create an emoji that the user submitted
 * this month (UTC) are as many as their monthly limit. `request_pending`: the emoji already has a pending request for a new image.
 * `invalid_reason`: the reason of a rejection is longer than 500 characters or not text.
 */
@Serializable(with = CustomEmojiProblemReason.Serializer::class)
public enum class CustomEmojiProblemReason(
  public val `value`: String,
) {
  @SerialName("invalid_shortcode")
  INVALID_SHORTCODE("invalid_shortcode"),
  @SerialName("shortcode_taken")
  SHORTCODE_TAKEN("shortcode_taken"),
  @SerialName("invalid_alias")
  INVALID_ALIAS("invalid_alias"),
  @SerialName("invalid_category")
  INVALID_CATEGORY("invalid_category"),
  @SerialName("media_not_found")
  MEDIA_NOT_FOUND("media_not_found"),
  @SerialName("media_not_ready")
  MEDIA_NOT_READY("media_not_ready"),
  @SerialName("unsupported_format")
  UNSUPPORTED_FORMAT("unsupported_format"),
  @SerialName("too_large")
  TOO_LARGE("too_large"),
  @SerialName("too_big")
  TOO_BIG("too_big"),
  @SerialName("remote_emoji")
  REMOTE_EMOJI("remote_emoji"),
  @SerialName("local_emoji")
  LOCAL_EMOJI("local_emoji"),
  @SerialName("fetch_failed")
  FETCH_FAILED("fetch_failed"),
  @SerialName("limit_reached")
  LIMIT_REACHED("limit_reached"),
  @SerialName("request_pending")
  REQUEST_PENDING("request_pending"),
  @SerialName("invalid_reason")
  INVALID_REASON("invalid_reason"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CustomEmojiProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.CustomEmojiProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CustomEmojiProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CustomEmojiProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CustomEmojiProblemReason> =
        entries.associateBy(CustomEmojiProblemReason::value)

    public fun fromValue(`value`: String): CustomEmojiProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
