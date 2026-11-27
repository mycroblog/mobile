
# AdminSupporterSubscription

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **state** | [**SupporterSubscriptionState**](SupporterSubscriptionState.md) |  |  |
| **planId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **planName** | **kotlin.String** |  |  |
| **interval** | [**SupporterInterval**](SupporterInterval.md) |  |  |
| **price** | [**Money**](Money.md) |  |  |
| **startedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **periodEndsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The end of the current period; omitted for &#x60;pending&#x60; and &#x60;abandoned&#x60;. |  [optional] |
| **graceEndsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a &#x60;past_due&#x60; subscription loses its role. |  [optional] |
| **scheduledPlanId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **scheduledInterval** | [**SupporterInterval**](SupporterInterval.md) |  |  [optional] |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **username** | **kotlin.String** | The subscriber&#39;s username; omitted with &#x60;account_id&#x60; for an erased account. |  [optional] |



