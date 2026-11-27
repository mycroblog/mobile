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
 * How often a subscription renews.
 */
@Serializable(with = SupporterInterval.Serializer::class)
public enum class SupporterInterval(
  public val `value`: String,
) {
  @SerialName("month")
  MONTH("month"),
  @SerialName("year")
  YEAR("year"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterInterval> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterInterval", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterInterval) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterInterval {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterInterval> =
        entries.associateBy(SupporterInterval::value)

    public fun fromValue(`value`: String): SupporterInterval = mapping[value] ?: UNRECOGNIZED
  }
}
