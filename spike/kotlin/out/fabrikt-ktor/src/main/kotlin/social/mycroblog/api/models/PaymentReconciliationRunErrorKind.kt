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
 * Why it stopped: `request_cap` (a partial attempt), `permission` (the API key is wrong
 * or lacks permissions), `not_found`, `invalid_request`, `throttled` and `unavailable`
 * (the provider is busy or down; the next reconciliation tries again),
 * `invalid_signature`, `stale_signature`, `mode_mismatch` (the key's mode differs from
 * the account's), `invalid_response`; `none` for a success.
 */
@Serializable(with = PaymentReconciliationRunErrorKind.Serializer::class)
public enum class PaymentReconciliationRunErrorKind(
  public val `value`: String,
) {
  @SerialName("none")
  NONE("none"),
  @SerialName("request_cap")
  REQUEST_CAP("request_cap"),
  @SerialName("permission")
  PERMISSION("permission"),
  @SerialName("not_found")
  NOT_FOUND("not_found"),
  @SerialName("invalid_request")
  INVALID_REQUEST("invalid_request"),
  @SerialName("throttled")
  THROTTLED("throttled"),
  @SerialName("unavailable")
  UNAVAILABLE("unavailable"),
  @SerialName("invalid_signature")
  INVALID_SIGNATURE("invalid_signature"),
  @SerialName("stale_signature")
  STALE_SIGNATURE("stale_signature"),
  @SerialName("mode_mismatch")
  MODE_MISMATCH("mode_mismatch"),
  @SerialName("invalid_response")
  INVALID_RESPONSE("invalid_response"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<PaymentReconciliationRunErrorKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.PaymentReconciliationRunErrorKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: PaymentReconciliationRunErrorKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): PaymentReconciliationRunErrorKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, PaymentReconciliationRunErrorKind> =
        entries.associateBy(PaymentReconciliationRunErrorKind::value)

    public fun fromValue(`value`: String): PaymentReconciliationRunErrorKind = mapping[value] ?: UNRECOGNIZED
  }
}
