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
 * The mode of the hook when it made the decision.
 */
@Serializable(with = HookDecisionMode.Serializer::class)
public enum class HookDecisionMode(
  public val `value`: String,
) {
  @SerialName("observe")
  OBSERVE("observe"),
  @SerialName("enforce")
  ENFORCE("enforce"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookDecisionMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.HookDecisionMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookDecisionMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookDecisionMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookDecisionMode> =
        entries.associateBy(HookDecisionMode::value)

    public fun fromValue(`value`: String): HookDecisionMode = mapping[value] ?: UNRECOGNIZED
  }
}
