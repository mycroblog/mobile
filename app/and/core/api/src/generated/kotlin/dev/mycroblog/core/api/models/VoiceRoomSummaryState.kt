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
 * Whether the room is open or ended.
 */
@Serializable(with = VoiceRoomSummaryState.Serializer::class)
public enum class VoiceRoomSummaryState(
  public val `value`: String,
) {
  @SerialName("open")
  OPEN("open"),
  @SerialName("ended")
  ENDED("ended"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceRoomSummaryState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceRoomSummaryState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceRoomSummaryState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceRoomSummaryState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceRoomSummaryState> =
        entries.associateBy(VoiceRoomSummaryState::value)

    public fun fromValue(`value`: String): VoiceRoomSummaryState = mapping[value] ?: UNRECOGNIZED
  }
}
