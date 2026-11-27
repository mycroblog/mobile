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
 * The coded reason of a verdict.
 */
@Serializable(with = HookReason.Serializer::class)
public enum class HookReason(
  public val `value`: String,
) {
  @SerialName("spam")
  SPAM("spam"),
  @SerialName("harassment")
  HARASSMENT("harassment"),
  @SerialName("sexual")
  SEXUAL("sexual"),
  @SerialName("violence")
  VIOLENCE("violence"),
  @SerialName("illegal")
  ILLEGAL("illegal"),
  @SerialName("malware")
  MALWARE("malware"),
  @SerialName("other")
  OTHER("other"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.HookReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookReason> = entries.associateBy(HookReason::value)

    public fun fromValue(`value`: String): HookReason = mapping[value] ?: UNRECOGNIZED
  }
}
