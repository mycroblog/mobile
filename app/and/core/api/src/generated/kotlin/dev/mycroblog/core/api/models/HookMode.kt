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
 * How far a decision hook acts: `off` calls nothing, `observe` records the verdict and acts on
 * nothing, `enforce` acts on the verdict.
 */
@Serializable(with = HookMode.Serializer::class)
public enum class HookMode(
  public val `value`: String,
) {
  @SerialName("off")
  OFF("off"),
  @SerialName("observe")
  OBSERVE("observe"),
  @SerialName("enforce")
  ENFORCE("enforce"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookMode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.HookMode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookMode) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookMode {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookMode> = entries.associateBy(HookMode::value)

    public fun fromValue(`value`: String): HookMode = mapping[value] ?: UNRECOGNIZED
  }
}
