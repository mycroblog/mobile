
# SupporterSubscription

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **state** | [**SupporterSubscriptionState**](SupporterSubscriptionState.md) |  |  |
| **planId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **planName** | **kotlin.String** |  |  |
| **planRank** | **kotlin.Int** | The plan&#39;s &#x60;rank&#x60;, given also for a hidden plan, which the plan list leaves out: a change to a plan of a higher rank applies at once, to a lower one at the end of the period.  |  |
| **interval** | [**SupporterInterval**](SupporterInterval.md) |  |  |
| **price** | [**Money**](Money.md) |  |  |
| **startedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **periodEndsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The end of the current period: when it renews, or, while &#x60;canceling&#x60;, when it ends; omitted for &#x60;pending&#x60;.  |  [optional] |
| **graceEndsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a &#x60;past_due&#x60; subscription loses the plan&#39;s role unless a payment succeeds. |  [optional] |
| **endReason** | [**SupporterEndReason**](SupporterEndReason.md) |  |  [optional] |
| **scheduledChange** | [**SupporterScheduledChange**](SupporterScheduledChange.md) |  |  [optional] |



