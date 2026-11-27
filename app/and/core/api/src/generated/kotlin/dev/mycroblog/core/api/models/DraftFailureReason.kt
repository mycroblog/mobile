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
 * Why a scheduled post was not published at its time; the draft stays, to fix and schedule
 * again. `restricted`: the account may not post (a moderation action, a suspension, or an
 * account that is not active). `reply_unavailable`: the post it replies to is gone, hidden from
 * the user, or does not take the reply. `quote_unavailable`: the post it quotes is gone, hidden
 * from the user, or may not be quoted. `media_unavailable`: an attached file is gone or failed
 * processing. `invalid`: the post does not pass the checks of a post, such as its length.
 * `rejected`: the server's automated review refused the post.
 */
@Serializable(with = DraftFailureReason.Serializer::class)
public enum class DraftFailureReason(
  public val `value`: String,
) {
  @SerialName("restricted")
  RESTRICTED("restricted"),
  @SerialName("reply_unavailable")
  REPLY_UNAVAILABLE("reply_unavailable"),
  @SerialName("quote_unavailable")
  QUOTE_UNAVAILABLE("quote_unavailable"),
  @SerialName("media_unavailable")
  MEDIA_UNAVAILABLE("media_unavailable"),
  @SerialName("invalid")
  INVALID("invalid"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DraftFailureReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DraftFailureReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DraftFailureReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DraftFailureReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DraftFailureReason> =
        entries.associateBy(DraftFailureReason::value)

    public fun fromValue(`value`: String): DraftFailureReason = mapping[value] ?: UNRECOGNIZED
  }
}
