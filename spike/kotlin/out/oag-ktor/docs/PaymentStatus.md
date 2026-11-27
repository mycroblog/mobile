
# PaymentStatus

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **provider** | **kotlin.String** | The provider of &#x60;payments&#x60; (&#x60;stripe&#x60;, &#x60;payjp&#x60;). |  |
| **mode** | [**inline**](#Mode) | &#x60;test&#x60; for test credentials, with which nothing is charged, and &#x60;live&#x60; for live credentials.  |  |
| **timeZone** | **kotlin.String** | The IANA time zone of the provider&#39;s reports (&#x60;UTC&#x60;), in which the income is dated. |  |
| **warnings** | [**inline**](#kotlin.collections.List&lt;Warnings&gt;) | What the operator should look at: &#x60;no_webhook&#x60; (no delivery is recorded) and &#x60;reconciliation_corrected&#x60; (the last reconciliation corrected subscriptions, which a webhook delivery should have done) are signs that the provider&#39;s webhook is not set up right, and &#x60;duplicate_charge&#x60; means that a second subscription of one payment is not canceled and refunded yet.  |  |
| **lastWebhookAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the newest webhook delivery was received; omitted when none is recorded (the deliveries are kept 45 days).  |  [optional] |
| **lastReconciliation** | [**PaymentReconciliationRun**](PaymentReconciliationRun.md) |  |  [optional] |


<a id="Mode"></a>
## Enum: mode
| Name | Value |
| ---- | ----- |
| mode | test, live |


<a id="kotlin.collections.List<Warnings>"></a>
## Enum: warnings
| Name | Value |
| ---- | ----- |
| warnings | no_webhook, reconciliation_corrected, duplicate_charge |



