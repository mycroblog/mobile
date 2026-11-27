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
 * The state of a job. `queued`: it has not run yet and waits for its `run_at` or a worker.
 * `running`: a worker holds it. `retrying`: it ran and waits to run again. `dead`: it
 * failed for good (its handler gave up, or it used up its attempts).
 */
@Serializable(with = AdminJobState.Serializer::class)
public enum class AdminJobState(
  public val `value`: String,
) {
  @SerialName("queued")
  QUEUED("queued"),
  @SerialName("running")
  RUNNING("running"),
  @SerialName("retrying")
  RETRYING("retrying"),
  @SerialName("dead")
  DEAD("dead"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminJobState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminJobState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminJobState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminJobState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminJobState> = entries.associateBy(AdminJobState::value)

    public fun fromValue(`value`: String): AdminJobState = mapping[value] ?: UNRECOGNIZED
  }
}
