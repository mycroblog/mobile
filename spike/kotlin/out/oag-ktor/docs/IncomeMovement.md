
# IncomeMovement

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **provider** | **kotlin.String** |  |  |
| **providerId** | **kotlin.String** |  |  |
| **kind** | [**IncomeKind**](IncomeKind.md) |  |  |
| **source** | [**IncomeSource**](IncomeSource.md) |  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **occurredOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  |
| **gross** | [**Money**](Money.md) |  |  |
| **fee** | [**Money**](Money.md) |  |  |
| **net** | [**Money**](Money.md) |  |  |
| **recordedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **parentProviderId** | **kotlin.String** |  |  [optional] |
| **subscriptionId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **tipId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **username** | **kotlin.String** | The username of the subscriber or of the user who paid the tip; omitted with &#x60;account_id&#x60; for an erased account. |  [optional] |
| **coversStartsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  [optional] |
| **coversEndsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  [optional] |



