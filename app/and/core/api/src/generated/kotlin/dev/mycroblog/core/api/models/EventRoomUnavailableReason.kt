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
 * Why the voice chat room of an event could not open at its start: `disabled` the voice chat is
 * off on this server, `not_eligible` the organizer may not host voice chat rooms, `too_many_rooms`
 * as many rooms were open as the server allows, and `hosting_elsewhere` the organizer was hosting
 * another room. The event itself goes on.
 */
@Serializable(with = EventRoomUnavailableReason.Serializer::class)
public enum class EventRoomUnavailableReason(
  public val `value`: String,
) {
  @SerialName("disabled")
  DISABLED("disabled"),
  @SerialName("not_eligible")
  NOT_ELIGIBLE("not_eligible"),
  @SerialName("too_many_rooms")
  TOO_MANY_ROOMS("too_many_rooms"),
  @SerialName("hosting_elsewhere")
  HOSTING_ELSEWHERE("hosting_elsewhere"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventRoomUnavailableReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.EventRoomUnavailableReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventRoomUnavailableReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventRoomUnavailableReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventRoomUnavailableReason> =
        entries.associateBy(EventRoomUnavailableReason::value)

    public fun fromValue(`value`: String): EventRoomUnavailableReason = mapping[value] ?: UNRECOGNIZED
  }
}
