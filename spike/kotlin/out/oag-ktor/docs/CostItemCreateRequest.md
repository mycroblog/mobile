
# CostItemCreateRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **schedule** | [**CostSchedule**](CostSchedule.md) |  |  |
| **interval** | **kotlin.Int** | The N of every N weeks, months, or years, 1 to 99; 1 for &#x60;once&#x60;. |  |
| **startsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  |
| **name** | **kotlin.String** | 1 to 100 characters without control characters. |  |
| **provider** | **kotlin.String** | 1 to 100 characters without control characters. |  |
| **category** | [**CostCategory**](CostCategory.md) |  |  |
| **price** | [**Money**](Money.md) |  |  |
| **endsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  [optional] |
| **memo** | **kotlin.String** | Up to 10,000 characters, never published. |  [optional] |



