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
 * What the instance states to AI crawlers (the instance setting `ai_usage`).
 * `allow`: no reservation; `deny_training`: no training of AI models; `deny`: no training
 * and no use by AI search, assistants, and agents. An account can opt out of what the
 * instance allows (`ai_opt_out`), never the reverse.
 */
@Serializable(with = AIUsage.Serializer::class)
public enum class AIUsage(
  public val `value`: String,
) {
  @SerialName("allow")
  ALLOW("allow"),
  @SerialName("deny_training")
  DENY_TRAINING("deny_training"),
  @SerialName("deny")
  DENY("deny"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AIUsage> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AIUsage", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AIUsage) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AIUsage {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AIUsage> = entries.associateBy(AIUsage::value)

    public fun fromValue(`value`: String): AIUsage = mapping[value] ?: UNRECOGNIZED
  }
}
