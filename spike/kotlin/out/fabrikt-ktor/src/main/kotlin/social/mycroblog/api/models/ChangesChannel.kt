package social.mycroblog.api.models

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
 * The channel to change.
 */
@Serializable(with = ChangesChannel.Serializer::class)
public enum class ChangesChannel(
  public val `value`: String,
) {
  @SerialName("in_app")
  IN_APP("in_app"),
  @SerialName("email")
  EMAIL("email"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ChangesChannel> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ChangesChannel", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ChangesChannel) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ChangesChannel {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ChangesChannel> = entries.associateBy(ChangesChannel::value)

    public fun fromValue(`value`: String): ChangesChannel = mapping[value] ?: UNRECOGNIZED
  }
}
