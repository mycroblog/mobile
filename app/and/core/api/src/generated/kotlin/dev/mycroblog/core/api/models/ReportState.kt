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
 * `open`: the report awaits the moderators. `resolved`: the moderators handled it.
 */
@Serializable(with = ReportState.Serializer::class)
public enum class ReportState(
  public val `value`: String,
) {
  @SerialName("open")
  OPEN("open"),
  @SerialName("resolved")
  RESOLVED("resolved"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<ReportState> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.ReportState", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: ReportState) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): ReportState {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, ReportState> = entries.associateBy(ReportState::value)

    public fun fromValue(`value`: String): ReportState = mapping[value] ?: UNRECOGNIZED
  }
}
