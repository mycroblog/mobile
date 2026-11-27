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
 * Whether a supporter plan takes new subscriptions. `public`: offered. `hidden`: not offered;
 * its subscriptions go on.
 */
@Serializable(with = SupporterPlanVisibility.Serializer::class)
public enum class SupporterPlanVisibility(
  public val `value`: String,
) {
  @SerialName("public")
  PUBLIC("public"),
  @SerialName("hidden")
  HIDDEN("hidden"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterPlanVisibility> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterPlanVisibility", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterPlanVisibility) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterPlanVisibility {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterPlanVisibility> =
        entries.associateBy(SupporterPlanVisibility::value)

    public fun fromValue(`value`: String): SupporterPlanVisibility = mapping[value] ?: UNRECOGNIZED
  }
}
