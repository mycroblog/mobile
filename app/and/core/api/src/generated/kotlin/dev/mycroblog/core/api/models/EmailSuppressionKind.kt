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

@Serializable(with = EmailSuppressionKind.Serializer::class)
public enum class EmailSuppressionKind(
  public val `value`: String,
) {
  @SerialName("hard_bounce")
  HARD_BOUNCE("hard_bounce"),
  @SerialName("complaint")
  COMPLAINT("complaint"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<EmailSuppressionKind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.EmailSuppressionKind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: EmailSuppressionKind) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): EmailSuppressionKind {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, EmailSuppressionKind> =
        entries.associateBy(EmailSuppressionKind::value)

    public fun fromValue(`value`: String): EmailSuppressionKind = mapping[value] ?: UNRECOGNIZED
  }
}
