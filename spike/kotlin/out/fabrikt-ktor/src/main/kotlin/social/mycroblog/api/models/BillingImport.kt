package social.mycroblog.api.models

import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The state of the imports of a billing source.
 */
@Serializable
public data class BillingImport(
  /**
   * A source of the billing imports (`billing_sources`): a cloud provider, or `plugin`, the
   * operator's plugin that aggregates any other providers.
   */
  @SerialName("source")
  public val source: BillingSourceName,
  /**
   * The provider's name, as the breakdown of the totals shows it.
   */
  @SerialName("provider")
  public val provider: String,
  /**
   * How the source reads the provider's data: `cost_explorer` (AWS Cost
   * Explorer) and `bigquery` (the Cloud Billing export to BigQuery, `billing_gcp_method =
   * bigquery`) by service and charge type; `budget` (the notifications of a Cloud Billing budget,
   * `billing_gcp_method = budget`) an approximation, one estimated total per budget and month
   * without a breakdown by service; `billable_usage` (Cloudflare's billable usage) the usage
   * charges of a pay-as-you-go account by product, without fixed fees, taxes, or credits;
   * `plugin` (the operator's plugin) what the plugin reports.
   */
  @SerialName("method")
  public val method: BillingImportMethod,
  /**
   * The IANA time zone of the provider's billing months (`UTC`, `America/Los_Angeles`, or
   * the operator's choice for a plugin), kept as the provider reports them.
   */
  @SerialName("time_zone")
  public val timeZone: String,
  /**
   * The last attempt of a billing import; omitted before the first.
   */
  @SerialName("last_run")
  public val lastRun: BillingImportRun? = null,
  /**
   * When the last successful import ran; omitted before the first.
   */
  @SerialName("last_success_at")
  public val lastSuccessAt: Instant? = null,
  /**
   * The billed requests of the imports this UTC day.
   */
  @SerialName("requests_today")
  public val requestsToday: Int,
  /**
   * The billed requests of the imports this UTC month.
   */
  @SerialName("requests_this_month")
  public val requestsThisMonth: Int,
  /**
   * The cap of the billed requests per UTC day (`billing_aws_max_requests_per_day`);
   * omitted for a source without caps.
   */
  @SerialName("max_requests_per_day")
  public val maxRequestsPerDay: Int? = null,
  /**
   * The cap of the billed requests per UTC month (`billing_aws_max_requests_per_month`);
   * omitted for a source without caps.
   */
  @SerialName("max_requests_per_month")
  public val maxRequestsPerMonth: Int? = null,
  /**
   * The bytes billed this UTC month, of BigQuery or as a plugin reports them; 0 for the other sources.
   */
  @SerialName("bytes_billed_this_month")
  public val bytesBilledThisMonth: Long,
  /**
   * The billing months of the last year with imported costs, newest first.
   */
  @SerialName("months")
  public val months: List<BillingImportMonth>,
)
