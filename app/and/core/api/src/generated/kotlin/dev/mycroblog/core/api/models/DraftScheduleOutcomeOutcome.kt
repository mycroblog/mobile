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
 * `canceled`: the user canceled the schedule or scheduled another time. `failed`: the post
 * could not be published at its time, for `reason`.
 */
@Serializable(with = DraftScheduleOutcomeOutcome.Serializer::class)
public enum class DraftScheduleOutcomeOutcome(
  public val `value`: String,
) {
  @SerialName("canceled")
  CANCELED("canceled"),
  @SerialName("failed")
  FAILED("failed"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<DraftScheduleOutcomeOutcome> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.DraftScheduleOutcomeOutcome", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: DraftScheduleOutcomeOutcome) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DraftScheduleOutcomeOutcome {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, DraftScheduleOutcomeOutcome> =
        entries.associateBy(DraftScheduleOutcomeOutcome::value)

    public fun fromValue(`value`: String): DraftScheduleOutcomeOutcome = mapping[value] ?: UNRECOGNIZED
  }
}
