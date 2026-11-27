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
 * `succeeded`: the server accepted the activity. `retryable_failure`: it failed in a way
 * that is tried again. `permanent_failure`: the server refused it, and it is not sent
 * again. `skipped`: nothing was sent, for the reason in `error`.
 */
@Serializable(with = AdminDeliveryAttemptOutcome.Serializer::class)
public enum class AdminDeliveryAttemptOutcome(
  public val `value`: String,
) {
  @SerialName("succeeded")
  SUCCEEDED("succeeded"),
  @SerialName("retryable_failure")
  RETRYABLE_FAILURE("retryable_failure"),
  @SerialName("permanent_failure")
  PERMANENT_FAILURE("permanent_failure"),
  @SerialName("skipped")
  SKIPPED("skipped"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminDeliveryAttemptOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminDeliveryAttemptOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminDeliveryAttemptOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminDeliveryAttemptOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminDeliveryAttemptOutcome> =
        entries.associateBy(AdminDeliveryAttemptOutcome::value)

    public fun fromValue(`value`: String): AdminDeliveryAttemptOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
