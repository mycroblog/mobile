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
 * Who posts on the board of an event, among those who read it: `organizer` the organizer alone,
 * `coorganizers` the organizer and the co-organizers, and `readers` everyone who reads the board.
 * The board is read by the organizer, the co-organizers, and the users who see the event and
 * answer `going` or `maybe`.
 */
@Serializable(with = EventBoardWriters.Serializer::class)
public enum class EventBoardWriters(
  public val `value`: String,
) {
  @SerialName("organizer")
  ORGANIZER("organizer"),
  @SerialName("coorganizers")
  COORGANIZERS("coorganizers"),
  @SerialName("readers")
  READERS("readers"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventBoardWriters> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.EventBoardWriters", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventBoardWriters) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventBoardWriters {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventBoardWriters> =
        entries.associateBy(EventBoardWriters::value)

    public fun fromValue(`value`: String): EventBoardWriters = mapping[value] ?: UNRECOGNIZED
  }
}
