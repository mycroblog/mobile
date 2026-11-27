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
 * The state of a quote as the viewer sees it, with the values of Mastodon's
 * `Quote.state` and `legacy`. The quoted status is shown for `accepted` and `legacy`, and
 * behind the reason for `muted_account` and `blocked_domain`:
 *
 * - `pending`: the quoted author has not approved the quote yet, or the quoted status is not
 *   fetched yet.
 * - `accepted`: the quoted author approved the quote.
 * - `rejected`: the quoted author refused the quote.
 * - `revoked`: the quoted author withdrew the approval.
 * - `legacy`: a quote from software without approvals (Misskey, Akkoma), shown as it is.
 * - `deleted`: the quoted status is deleted.
 * - `unauthorized`: the viewer may not see the quoted status.
 * - `blocked_account`: the viewer blocks the quoted author, or is blocked by them.
 * - `blocked_domain`: the viewer mutes the domain of the quoted author.
 * - `muted_account`: the viewer mutes the quoted author.
 */
@Serializable(with = QuoteState.Serializer::class)
public enum class QuoteState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("accepted")
  ACCEPTED("accepted"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("revoked")
  REVOKED("revoked"),
  @SerialName("legacy")
  LEGACY("legacy"),
  @SerialName("deleted")
  DELETED("deleted"),
  @SerialName("unauthorized")
  UNAUTHORIZED("unauthorized"),
  @SerialName("blocked_account")
  BLOCKED_ACCOUNT("blocked_account"),
  @SerialName("blocked_domain")
  BLOCKED_DOMAIN("blocked_domain"),
  @SerialName("muted_account")
  MUTED_ACCOUNT("muted_account"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<QuoteState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.QuoteState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: QuoteState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): QuoteState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, QuoteState> = entries.associateBy(QuoteState::value)

    public fun fromValue(`value`: String): QuoteState = mapping[value] ?: UNRECOGNIZED
  }
}
