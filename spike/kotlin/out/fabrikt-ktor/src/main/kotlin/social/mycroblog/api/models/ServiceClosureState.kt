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
 * Where the server is in its closure:
 *
 * - `none`: no closure is announced.
 * - `announced`: the closure is announced; the server runs as before until it closes.
 * - `closed`: the server is closed and delivers the deletion of its local accounts to the
 *   other servers. The users can still sign in and export their data until `export_until`.
 * - `delivery_completed`: the server is closed and those deliveries have ended.
 */
@Serializable(with = ServiceClosureState.Serializer::class)
public enum class ServiceClosureState(
  public val `value`: String,
) {
  @SerialName("none")
  NONE("none"),
  @SerialName("announced")
  ANNOUNCED("announced"),
  @SerialName("closed")
  CLOSED("closed"),
  @SerialName("delivery_completed")
  DELIVERY_COMPLETED("delivery_completed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ServiceClosureState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ServiceClosureState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ServiceClosureState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ServiceClosureState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ServiceClosureState> =
        entries.associateBy(ServiceClosureState::value)

    public fun fromValue(`value`: String): ServiceClosureState = mapping[value] ?: UNRECOGNIZED
  }
}
