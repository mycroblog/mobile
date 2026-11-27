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
 * Who sees an event: `public` every local user, `followers` the users who follow the organizer,
 * `following` the users the organizer follows, `mutuals` the users who follow the organizer and
 * whom the organizer follows, and `invited` the invited users only. The organizer, the
 * co-organizers, the invited users, and the users who answered always see it, unless a block
 * separates them from the organizer or a co-organizer.
 */
@Serializable(with = EventVisibility.Serializer::class)
public enum class EventVisibility(
  public val `value`: String,
) {
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("followers")
  FOLLOWERS("followers"),
  @SerialName("following")
  FOLLOWING("following"),
  @SerialName("mutuals")
  MUTUALS("mutuals"),
  @SerialName("invited")
  INVITED("invited"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EventVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.EventVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EventVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EventVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EventVisibility> = entries.associateBy(EventVisibility::value)

    public fun fromValue(`value`: String): EventVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
