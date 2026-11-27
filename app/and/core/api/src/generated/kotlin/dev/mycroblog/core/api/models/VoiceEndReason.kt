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
 * Why a voice chat room ended: `host` the host ended it, `empty` nobody was left in it,
 * `host_disconnected` the host lost the connection and nobody took over, `host_ineligible` the
 * host may no longer host, `moderator` a moderator ended it, `time_limit` it reached the limit
 * of its duration, `disabled` the voice chat was turned off, `host_absent` the host of a room
 * opened for a scheduled time did not come by the end of its wait and nobody took over, and
 * `unattended` nobody came to such a room by the end of its wait.
 */
@Serializable(with = VoiceEndReason.Serializer::class)
public enum class VoiceEndReason(
  public val `value`: String,
) {
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

  public object Serializer : KSerializer<VoiceEndReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.VoiceEndReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: VoiceEndReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): VoiceEndReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, VoiceEndReason> = entries.associateBy(VoiceEndReason::value)

    public fun fromValue(`value`: String): VoiceEndReason = mapping[value] ?: UNRECOGNIZED
  }
}
