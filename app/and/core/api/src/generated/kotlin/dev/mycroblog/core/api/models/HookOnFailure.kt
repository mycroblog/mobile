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
 * What a hook that enforces does when its plugin fails: `pass` lets the subject through, `retry`
 * fails the job so that it runs again, and `require_approval` holds the sign-up for approval.
 */
@Serializable(with = HookOnFailure.Serializer::class)
public enum class HookOnFailure(
  public val `value`: String,
) {
  @SerialName("pass")
  PASS("pass"),
  @SerialName("retry")
  RETRY("retry"),
  @SerialName("require_approval")
  REQUIRE_APPROVAL("require_approval"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookOnFailure> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.HookOnFailure", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookOnFailure) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookOnFailure {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookOnFailure> = entries.associateBy(HookOnFailure::value)

    public fun fromValue(`value`: String): HookOnFailure = mapping[value] ?: UNRECOGNIZED
  }
}
