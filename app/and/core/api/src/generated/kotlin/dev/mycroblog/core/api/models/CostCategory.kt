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
 * The fixed category of a cost; clients translate its name.
 */
@Serializable(with = CostCategory.Serializer::class)
public enum class CostCategory(
  public val `value`: String,
) {
  @SerialName("compute")
  COMPUTE("compute"),
  @SerialName("database")
  DATABASE("database"),
  @SerialName("storage")
  STORAGE("storage"),
  @SerialName("network")
  NETWORK("network"),
  @SerialName("domain")
  DOMAIN("domain"),
  @SerialName("email")
  EMAIL("email"),
  @SerialName("software")
  SOFTWARE("software"),
  @SerialName("other")
  OTHER("other"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<CostCategory> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.CostCategory", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: CostCategory) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): CostCategory {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, CostCategory> = entries.associateBy(CostCategory::value)

    public fun fromValue(`value`: String): CostCategory = mapping[value] ?: UNRECOGNIZED
  }
}
