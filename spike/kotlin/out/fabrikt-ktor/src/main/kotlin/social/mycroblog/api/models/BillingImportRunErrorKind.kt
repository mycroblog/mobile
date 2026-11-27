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
 * Why it failed: `permission` (the credentials may not read the billing data),
 * `not_enabled` (the billing data is not enabled), `not_found` (the dataset, table, or
 * subscription does not exist), `throttled` and `unavailable` (retried), `request_cap`
 * (the cap of the day or the month is reached; nothing was called), `bytes_cap` (the
 * query would scan too much), `invalid_response`; `none` for a success.
 */
@Serializable(with = BillingImportRunErrorKind.Serializer::class)
public enum class BillingImportRunErrorKind(
  public val `value`: String,
) {
  @SerialName("none")
  NONE("none"),
  @SerialName("permission")
  PERMISSION("permission"),
  @SerialName("not_enabled")
  NOT_ENABLED("not_enabled"),
  @SerialName("not_found")
  NOT_FOUND("not_found"),
  @SerialName("throttled")
  THROTTLED("throttled"),
  @SerialName("request_cap")
  REQUEST_CAP("request_cap"),
  @SerialName("bytes_cap")
  BYTES_CAP("bytes_cap"),
  @SerialName("invalid_response")
  INVALID_RESPONSE("invalid_response"),
  @SerialName("unavailable")
  UNAVAILABLE("unavailable"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BillingImportRunErrorKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.BillingImportRunErrorKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BillingImportRunErrorKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BillingImportRunErrorKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BillingImportRunErrorKind> =
        entries.associateBy(BillingImportRunErrorKind::value)

    public fun fromValue(`value`: String): BillingImportRunErrorKind = mapping[value] ?: UNRECOGNIZED
  }
}
