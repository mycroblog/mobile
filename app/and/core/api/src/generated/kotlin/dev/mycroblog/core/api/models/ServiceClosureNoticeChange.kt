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

@Serializable(with = ServiceClosureNoticeChange.Serializer::class)
public enum class ServiceClosureNoticeChange(
  public val `value`: String,
) {
  @SerialName("announced")
  ANNOUNCED("announced"),
  @SerialName("rescheduled")
  RESCHEDULED("rescheduled"),
  @SerialName("withdrawn")
  WITHDRAWN("withdrawn"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ServiceClosureNoticeChange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ServiceClosureNoticeChange", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ServiceClosureNoticeChange) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ServiceClosureNoticeChange {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ServiceClosureNoticeChange> =
        entries.associateBy(ServiceClosureNoticeChange::value)

    public fun fromValue(`value`: String): ServiceClosureNoticeChange = mapping[value] ?: UNRECOGNIZED
  }
}
