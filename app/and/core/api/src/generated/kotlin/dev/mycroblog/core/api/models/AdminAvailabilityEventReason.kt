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
 * `failure_days`: deliveries failed on 7 distinct days. `delivery_succeeded`: a delivery
 * succeeded. `inbound_request`: the server sent a signed request. `probe_succeeded`: the
 * daily probe fetched its NodeInfo. `operator`: an operator marked it.
 */
@Serializable(with = AdminAvailabilityEventReason.Serializer::class)
public enum class AdminAvailabilityEventReason(
  public val `value`: String,
) {
  @SerialName("failure_days")
  FAILURE_DAYS("failure_days"),
  @SerialName("delivery_succeeded")
  DELIVERY_SUCCEEDED("delivery_succeeded"),
  @SerialName("inbound_request")
  INBOUND_REQUEST("inbound_request"),
  @SerialName("probe_succeeded")
  PROBE_SUCCEEDED("probe_succeeded"),
  @SerialName("operator")
  OPERATOR("operator"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAvailabilityEventReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminAvailabilityEventReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAvailabilityEventReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAvailabilityEventReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAvailabilityEventReason> =
        entries.associateBy(AdminAvailabilityEventReason::value)

    public fun fromValue(`value`: String): AdminAvailabilityEventReason = mapping[value] ?: UNRECOGNIZED
  }
}
