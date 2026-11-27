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
 * What a decision records: a verdict (`allow`, `drop`, `flag`, `mark_sensitive`,
 * `require_approval`, `reject`), or a kind of failure, which is not a verdict: `timeout`, `error`,
 * `invalid` (the answer is outside the hook's vocabulary), `breaker_open` (the server skipped
 * the plugin after repeated failures).
 */
@Serializable(with = HookOutcome.Serializer::class)
public enum class HookOutcome(
  public val `value`: String,
) {
  @SerialName("allow")
  ALLOW("allow"),
  @SerialName("drop")
  DROP("drop"),
  @SerialName("flag")
  FLAG("flag"),
  @SerialName("mark_sensitive")
  MARK_SENSITIVE("mark_sensitive"),
  @SerialName("require_approval")
  REQUIRE_APPROVAL("require_approval"),
  @SerialName("reject")
  REJECT("reject"),
  @SerialName("timeout")
  TIMEOUT("timeout"),
  @SerialName("error")
  ERROR("error"),
  @SerialName("invalid")
  INVALID("invalid"),
  @SerialName("breaker_open")
  BREAKER_OPEN("breaker_open"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<HookOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.HookOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: HookOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): HookOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, HookOutcome> = entries.associateBy(HookOutcome::value)

    public fun fromValue(`value`: String): HookOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
