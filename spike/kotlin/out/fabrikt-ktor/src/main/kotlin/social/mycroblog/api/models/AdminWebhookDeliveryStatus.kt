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
 * `pending`: not sent yet. `succeeded`: an attempt got a 2xx answer. `failing`: every
 * attempt so far failed; it is retried over about 27 hours, 8 attempts in all, and can be
 * sent again by hand after that.
 */
@Serializable(with = AdminWebhookDeliveryStatus.Serializer::class)
public enum class AdminWebhookDeliveryStatus(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("succeeded")
  SUCCEEDED("succeeded"),
  @SerialName("failing")
  FAILING("failing"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminWebhookDeliveryStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminWebhookDeliveryStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminWebhookDeliveryStatus) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminWebhookDeliveryStatus {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminWebhookDeliveryStatus> =
        entries.associateBy(AdminWebhookDeliveryStatus::value)

    public fun fromValue(`value`: String): AdminWebhookDeliveryStatus = mapping[value] ?: UNRECOGNIZED
  }
}
