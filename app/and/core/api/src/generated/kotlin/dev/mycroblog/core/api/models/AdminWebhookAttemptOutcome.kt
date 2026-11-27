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
 * `succeeded`: a 2xx answer. `http_error`: another answer, a redirect included.
 * `timeout`: no answer within 15 seconds. `connection_error`: the connection failed.
 * `forbidden_destination`: the server may not reach the URL (a loopback or private address
 * that `webhook_allowed_destinations` does not list).
 */
@Serializable(with = AdminWebhookAttemptOutcome.Serializer::class)
public enum class AdminWebhookAttemptOutcome(
  public val `value`: String,
) {
  @SerialName("succeeded")
  SUCCEEDED("succeeded"),
  @SerialName("http_error")
  HTTP_ERROR("http_error"),
  @SerialName("timeout")
  TIMEOUT("timeout"),
  @SerialName("connection_error")
  CONNECTION_ERROR("connection_error"),
  @SerialName("forbidden_destination")
  FORBIDDEN_DESTINATION("forbidden_destination"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminWebhookAttemptOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminWebhookAttemptOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminWebhookAttemptOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminWebhookAttemptOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminWebhookAttemptOutcome> =
        entries.associateBy(AdminWebhookAttemptOutcome::value)

    public fun fromValue(`value`: String): AdminWebhookAttemptOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
