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
 * The state of an error group: `open` (new, unmuted, or regressed),
 * `resolved` (an administrator marked it fixed; an occurrence on another version regresses
 * it to `open`), or `muted` (it notifies nobody until it is unmuted).
 */
@Serializable(with = ErrorGroupState.Serializer::class)
public enum class ErrorGroupState(
  public val `value`: String,
) {
  @SerialName("open")
  OPEN("open"),
  @SerialName("resolved")
  RESOLVED("resolved"),
  @SerialName("muted")
  MUTED("muted"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ErrorGroupState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.ErrorGroupState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ErrorGroupState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ErrorGroupState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ErrorGroupState> = entries.associateBy(ErrorGroupState::value)

    public fun fromValue(`value`: String): ErrorGroupState = mapping[value] ?: UNRECOGNIZED
  }
}
