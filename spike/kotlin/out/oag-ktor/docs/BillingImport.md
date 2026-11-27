
# BillingImport

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **source** | [**BillingSourceName**](BillingSourceName.md) |  |  |
| **provider** | **kotlin.String** | The provider&#39;s name, as the breakdown of the totals shows it. |  |
| **method** | [**inline**](#Method) | How the source reads the provider&#39;s data: &#x60;cost_explorer&#x60; (AWS Cost Explorer) and &#x60;bigquery&#x60; (the Cloud Billing export to BigQuery, &#x60;billing_gcp_method &#x3D; bigquery&#x60;) by service and charge type; &#x60;budget&#x60; (the notifications of a Cloud Billing budget, &#x60;billing_gcp_method &#x3D; budget&#x60;) an approximation, one estimated total per budget and month without a breakdown by service; &#x60;billable_usage&#x60; (Cloudflare&#39;s billable usage) the usage charges of a pay-as-you-go account by product, without fixed fees, taxes, or credits; &#x60;plugin&#x60; (the operator&#39;s plugin) what the plugin reports.  |  |
| **timeZone** | **kotlin.String** | The IANA time zone of the provider&#39;s billing months (&#x60;UTC&#x60;, &#x60;America/Los_Angeles&#x60;, or the operator&#39;s choice for a plugin), kept as the provider reports them.  |  |
| **requestsToday** | **kotlin.Int** | The billed requests of the imports this UTC day. |  |
| **requestsThisMonth** | **kotlin.Int** | The billed requests of the imports this UTC month. |  |
| **bytesBilledThisMonth** | **kotlin.Long** | The bytes billed this UTC month, of BigQuery or as a plugin reports them; 0 for the other sources. |  |
| **months** | [**kotlin.collections.List&lt;BillingImportMonth&gt;**](BillingImportMonth.md) | The billing months of the last year with imported costs, newest first. |  |
| **lastRun** | [**BillingImportRun**](BillingImportRun.md) |  |  [optional] |
| **lastSuccessAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the last successful import ran; omitted before the first. |  [optional] |
| **maxRequestsPerDay** | **kotlin.Int** | The cap of the billed requests per UTC day (&#x60;billing_aws_max_requests_per_day&#x60;); omitted for a source without caps.  |  [optional] |
| **maxRequestsPerMonth** | **kotlin.Int** | The cap of the billed requests per UTC month (&#x60;billing_aws_max_requests_per_month&#x60;); omitted for a source without caps.  |  [optional] |


<a id="Method"></a>
## Enum: method
| Name | Value |
| ---- | ----- |
| method | cost_explorer, bigquery, budget, billable_usage, plugin |



