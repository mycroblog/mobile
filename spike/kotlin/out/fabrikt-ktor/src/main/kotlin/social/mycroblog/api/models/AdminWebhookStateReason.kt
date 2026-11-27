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
 * Who set `state` last. `admin`: a staff member, or the `my` command line. `gone`: the
 * server disabled it after the endpoint answered 410 Gone. `failing`: the server disabled
 * it after the deliveries kept failing, without a success, for 72 hours and 20 attempts.
 */
@Serializable(with = AdminWebhookStateReason.Serializer::class)
public enum class AdminWebhookStateReason(
  public val `value`: String,
) {
  @SerialName("admin")
  ADMIN("admin"),
  @SerialName("gone")
  GONE("gone"),
  @SerialName("failing")
  FAILING("failing"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminWebhookStateReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminWebhookStateReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminWebhookStateReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminWebhookStateReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminWebhookStateReason> =
        entries.associateBy(AdminWebhookStateReason::value)

    public fun fromValue(`value`: String): AdminWebhookStateReason = mapping[value] ?: UNRECOGNIZED
  }
}
