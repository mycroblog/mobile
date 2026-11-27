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
 * Whether an event is `unscheduled` (it polls its date and no candidate was confirmed yet),
 * `scheduled`, or `canceled`. An event that ended is still `scheduled`: its `ends_at` is in the
 * past.
 */
@Serializable(with = EventState.Serializer::class)
public enum class EventState(
  public val `value`: String,
) {
  @SerialName("unscheduled")
  UNSCHEDULED("unscheduled"),
  @SerialName("scheduled")
  SCHEDULED("scheduled"),
  @SerialName("canceled")
  CANCELED("canceled"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.EventState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventState> = entries.associateBy(EventState::value)

    public fun fromValue(`value`: String): EventState = mapping[value] ?: UNRECOGNIZED
  }
}
