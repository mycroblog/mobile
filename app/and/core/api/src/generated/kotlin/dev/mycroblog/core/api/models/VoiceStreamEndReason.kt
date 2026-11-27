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

@Serializable(with = VoiceStreamEndReason.Serializer::class)
public enum class VoiceStreamEndReason(
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
  @SerialName("replaced")
  REPLACED("replaced"),
  @SerialName("host")
  HOST("host"),
  @SerialName("empty")
  EMPTY("empty"),
  @SerialName("host_disconnected")
  HOST_DISCONNECTED("host_disconnected"),
  @SerialName("host_ineligible")
  HOST_INELIGIBLE("host_ineligible"),
  @SerialName("moderator")
  MODERATOR("moderator"),
  @SerialName("time_limit")
  TIME_LIMIT("time_limit"),
  @SerialName("disabled")
  DISABLED("disabled"),
  @SerialName("host_absent")
  HOST_ABSENT("host_absent"),
  @SerialName("unattended")
  UNATTENDED("unattended"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<VoiceStreamEndReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceStreamEndReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceStreamEndReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceStreamEndReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceStreamEndReason> =
        entries.associateBy(VoiceStreamEndReason::value)

    public fun fromValue(`value`: String): VoiceStreamEndReason = mapping[value] ?: UNRECOGNIZED
  }
}
