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
 * How the source reads the provider's data: `cost_explorer` (AWS Cost
 * Explorer) and `bigquery` (the Cloud Billing export to BigQuery, `billing_gcp_method =
 * bigquery`) by service and charge type; `budget` (the notifications of a Cloud Billing budget,
 * `billing_gcp_method = budget`) an approximation, one estimated total per budget and month
 * without a breakdown by service; `billable_usage` (Cloudflare's billable usage) the usage
 * charges of a pay-as-you-go account by product, without fixed fees, taxes, or credits;
 * `plugin` (the operator's plugin) what the plugin reports.
 */
@Serializable(with = BillingImportMethod.Serializer::class)
public enum class BillingImportMethod(
  public val `value`: String,
) {
  @SerialName("cost_explorer")
  COST_EXPLORER("cost_explorer"),
  @SerialName("bigquery")
  BIGQUERY("bigquery"),
  @SerialName("budget")
  BUDGET("budget"),
  @SerialName("billable_usage")
  BILLABLE_USAGE("billable_usage"),
  @SerialName("plugin")
  PLUGIN("plugin"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<BillingImportMethod> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.BillingImportMethod", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: BillingImportMethod) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): BillingImportMethod {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, BillingImportMethod> =
        entries.associateBy(BillingImportMethod::value)

    public fun fromValue(`value`: String): BillingImportMethod = mapping[value] ?: UNRECOGNIZED
  }
}
