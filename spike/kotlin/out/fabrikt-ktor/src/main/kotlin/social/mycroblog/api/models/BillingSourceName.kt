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

/**
 * A source of the billing imports (`billing_sources`): a cloud provider, or `plugin`, the
 * operator's plugin that aggregates any other providers.
 */
@Serializable(with = BillingSourceName.Serializer::class)
public enum class BillingSourceName(
  public val `value`: String,
) {
  @SerialName("aws")
  AWS("aws"),
  @SerialName("gcp")
  GCP("gcp"),
  @SerialName("cloudflare")
  CLOUDFLARE("cloudflare"),
  @SerialName("plugin")
  PLUGIN("plugin"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BillingSourceName> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.BillingSourceName", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BillingSourceName) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BillingSourceName {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BillingSourceName> =
        entries.associateBy(BillingSourceName::value)

    public fun fromValue(`value`: String): BillingSourceName = mapping[value] ?: UNRECOGNIZED
  }
}
