
# CostBreakdownLine

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **provider** | **kotlin.String** | The provider as entered, one spelling for every line of the same grouped provider in the period: the provider of the cost item whose current label was revised last among the items with payments in the period (the larger revision ID on a tie), or, for a provider of imported costs only, the import&#39;s provider name (&#x60;AWS&#x60;, &#x60;Google Cloud&#x60;).  |  |
| **category** | [**CostCategory**](CostCategory.md) |  |  |
| **amount** | [**Money**](Money.md) |  |  |



