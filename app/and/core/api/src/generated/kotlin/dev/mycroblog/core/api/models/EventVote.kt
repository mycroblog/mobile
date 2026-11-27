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
 * An answer to a candidate date of an event's date poll.
 */
@Serializable(with = EventVote.Serializer::class)
public enum class EventVote(
  public val `value`: String,
) {
  @SerialName("yes")
  YES("yes"),
  @SerialName("maybe")
  MAYBE("maybe"),
  @SerialName("no")
  NO("no"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventVote> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.EventVote", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventVote) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventVote {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventVote> = entries.associateBy(EventVote::value)

    public fun fromValue(`value`: String): EventVote = mapping[value] ?: UNRECOGNIZED
  }
}
