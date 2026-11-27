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

@Serializable(with = IncomeTotalsRulesWeekStart.Serializer::class)
public enum class IncomeTotalsRulesWeekStart(
  public val `value`: String,
) {
  @SerialName("monday")
  MONDAY("monday"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<IncomeTotalsRulesWeekStart> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.IncomeTotalsRulesWeekStart", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: IncomeTotalsRulesWeekStart) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): IncomeTotalsRulesWeekStart {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, IncomeTotalsRulesWeekStart> =
        entries.associateBy(IncomeTotalsRulesWeekStart::value)

    public fun fromValue(`value`: String): IncomeTotalsRulesWeekStart = mapping[value] ?: UNRECOGNIZED
  }
}
