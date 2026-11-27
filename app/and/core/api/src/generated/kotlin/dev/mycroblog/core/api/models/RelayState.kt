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
 * Where the membership of a relay stands. `pending`: the `Follow` is sent and not
 * answered. `accepted`: public posts go to the relay and, with `receive`, come from it.
 * `rejected`: the relay rejected the `Follow` or ended the membership. `left`: the
 * instance sent the `Undo` of its `Follow`.
 */
@Serializable(with = RelayState.Serializer::class)
public enum class RelayState(
  public val `value`: String,
) {
  @SerialName("pending")
  PENDING("pending"),
  @SerialName("accepted")
  ACCEPTED("accepted"),
  @SerialName("rejected")
  REJECTED("rejected"),
  @SerialName("left")
  LEFT("left"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<RelayState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.RelayState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: RelayState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RelayState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, RelayState> = entries.associateBy(RelayState::value)

    public fun fromValue(`value`: String): RelayState = mapping[value] ?: UNRECOGNIZED
  }
}
