
# CostItem

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **schedule** | [**CostSchedule**](CostSchedule.md) |  |  |
| **interval** | **kotlin.Int** | The N of every N weeks, months, or years; 1 for &#x60;once&#x60;. |  |
| **startsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  |
| **name** | **kotlin.String** |  |  |
| **provider** | **kotlin.String** | Who is paid, as written; the breakdown groups it in NFKC and lower case. |  |
| **category** | [**CostCategory**](CostCategory.md) |  |  |
| **memo** | **kotlin.String** | The operator&#39;s note, never published; empty for none. |  |
| **prices** | [**kotlin.collections.List&lt;CostPriceRevision&gt;**](CostPriceRevision.md) | Every price revision, in the order they were recorded; the first one starts on &#x60;starts_on&#x60;. &#x60;ends_on&#x60; is the last date a payment can fall on (included), omitted for no end; &#x60;starts_on&#x60; is the first payment date, the payment date of &#x60;once&#x60;.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **endsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  [optional] |



