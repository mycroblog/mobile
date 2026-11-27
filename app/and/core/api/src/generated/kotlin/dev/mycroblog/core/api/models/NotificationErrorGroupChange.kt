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

@Serializable(with = NotificationErrorGroupChange.Serializer::class)
public enum class NotificationErrorGroupChange(
  public val `value`: String,
) {
  @SerialName("created")
  CREATED("created"),
  @SerialName("regressed")
  REGRESSED("regressed"),
  @SerialName("others")
  OTHERS("others"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<NotificationErrorGroupChange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.NotificationErrorGroupChange", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: NotificationErrorGroupChange) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): NotificationErrorGroupChange {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, NotificationErrorGroupChange> =
        entries.associateBy(NotificationErrorGroupChange::value)

    public fun fromValue(`value`: String): NotificationErrorGroupChange = mapping[value] ?: UNRECOGNIZED
  }
}
