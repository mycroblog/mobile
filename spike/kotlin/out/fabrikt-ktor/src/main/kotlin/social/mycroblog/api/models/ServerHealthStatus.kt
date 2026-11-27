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
 * `ok` while the server is healthy, `unavailable` while the database is unreachable or its
 * schema is not applied, `maintenance` during the maintenance mode.
 */
@Serializable(with = ServerHealthStatus.Serializer::class)
public enum class ServerHealthStatus(
  public val `value`: String,
) {
  @SerialName("ok")
  OK("ok"),
  @SerialName("unavailable")
  UNAVAILABLE("unavailable"),
  @SerialName("maintenance")
  MAINTENANCE("maintenance"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ServerHealthStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ServerHealthStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ServerHealthStatus) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ServerHealthStatus {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ServerHealthStatus> =
        entries.associateBy(ServerHealthStatus::value)

    public fun fromValue(`value`: String): ServerHealthStatus = mapping[value] ?: UNRECOGNIZED
  }
}
