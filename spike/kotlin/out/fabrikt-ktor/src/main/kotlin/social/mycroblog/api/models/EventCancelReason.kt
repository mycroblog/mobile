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
 * Why an event was canceled: by its `organizer`, by a `moderator`, or because the organizer was
 * suspended (`organizer_suspended`) or deleted (`organizer_deleted`) before it started.
 */
@Serializable(with = EventCancelReason.Serializer::class)
public enum class EventCancelReason(
  public val `value`: String,
) {
  @SerialName("organizer")
  ORGANIZER("organizer"),
  @SerialName("moderator")
  MODERATOR("moderator"),
  @SerialName("organizer_suspended")
  ORGANIZER_SUSPENDED("organizer_suspended"),
  @SerialName("organizer_deleted")
  ORGANIZER_DELETED("organizer_deleted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventCancelReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.EventCancelReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventCancelReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventCancelReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventCancelReason> =
        entries.associateBy(EventCancelReason::value)

    public fun fromValue(`value`: String): EventCancelReason = mapping[value] ?: UNRECOGNIZED
  }
}
