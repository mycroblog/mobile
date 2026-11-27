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
 * Who sees the `physical` and `url` places of an event: `approved` only the attendees whom the
 * organizer or a co-organizer approved, and `viewers` everyone who sees the event. The organizer
 * and the co-organizers see them always, and a `voice` place is shown to everyone who sees the
 * event.
 */
@Serializable(with = EventPlaceDisclosure.Serializer::class)
public enum class EventPlaceDisclosure(
  public val `value`: String,
) {
  @SerialName("approved")
  APPROVED("approved"),
  @SerialName("viewers")
  VIEWERS("viewers"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventPlaceDisclosure> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.EventPlaceDisclosure", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventPlaceDisclosure) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventPlaceDisclosure {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventPlaceDisclosure> =
        entries.associateBy(EventPlaceDisclosure::value)

    public fun fromValue(`value`: String): EventPlaceDisclosure = mapping[value] ?: UNRECOGNIZED
  }
}
