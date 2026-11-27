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
 * Why the account left (`left`, `moved`, `disconnected`, `removed`, `blocked`,
 * `suspended`, `room_ended`); omitted while it takes part.
 */
@Serializable(with = AdminVoiceRoomParticipationLeaveReason.Serializer::class)
public enum class AdminVoiceRoomParticipationLeaveReason(
  public val `value`: String,
) {
  @SerialName("left")
  LEFT("left"),
  @SerialName("moved")
  MOVED("moved"),
  @SerialName("disconnected")
  DISCONNECTED("disconnected"),
  @SerialName("removed")
  REMOVED("removed"),
  @SerialName("blocked")
  BLOCKED("blocked"),
  @SerialName("suspended")
  SUSPENDED("suspended"),
  @SerialName("room_ended")
  ROOM_ENDED("room_ended"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminVoiceRoomParticipationLeaveReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.AdminVoiceRoomParticipationLeaveReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminVoiceRoomParticipationLeaveReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminVoiceRoomParticipationLeaveReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminVoiceRoomParticipationLeaveReason> =
        entries.associateBy(AdminVoiceRoomParticipationLeaveReason::value)

    public fun fromValue(`value`: String): AdminVoiceRoomParticipationLeaveReason = mapping[value] ?: UNRECOGNIZED
  }
}
