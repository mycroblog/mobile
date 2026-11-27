# FinanceApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getFinanceCostTotals**](FinanceApi.md#getFinanceCostTotals) | **GET** /finance/cost_totals | Total the costs by period for the finance viewers |
| [**getFinanceIncomeTotals**](FinanceApi.md#getFinanceIncomeTotals) | **GET** /finance/income_totals | Total the income by period for the finance viewers |


<a id="getFinanceCostTotals"></a>
# **getFinanceCostTotals**
> FinanceCostTotals getFinanceCostTotals(granularity, from, to, basis)

Total the costs by period for the finance viewers

The totals of the instance&#39;s costs for the holders of &#x60;view_finance&#x60;. It takes the query of &#x60;GET /admin/cost_totals&#x60; and always answers its totals, their breakdown by provider, category, and currency, and the rules that cut the periods. Each period adds &#x60;items&#x60;, the amount of each cost item and each service of the imported costs, only while the instance setting &#x60;finance_viewer_item_names&#x60; or &#x60;finance_viewer_item_memos&#x60; is on: an item line carries &#x60;name&#x60; while names are shown and &#x60;memo&#x60; while memos are shown, so with memos only it has a memo and no name. A holder of &#x60;manage_finance&#x60; reads every item line with its name and memo whatever the settings. &#x60;viewer&#x60; reports the three settings. No answer carries who paid what, the revisions of the prices, or who recorded the items. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. Needs the &#x60;view_finance&#x60; or the &#x60;manage_finance&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FinanceApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : FinanceCostTotals = apiInstance.getFinanceCostTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FinanceApi#getFinanceCostTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FinanceApi#getFinanceCostTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**FinanceCostTotals**](FinanceCostTotals.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getFinanceIncomeTotals"></a>
# **getFinanceIncomeTotals**
> IncomeTotals getFinanceIncomeTotals(granularity, from, to, basis)

Total the income by period for the finance viewers

The income totals for the holders of &#x60;view_finance&#x60;. It takes the query of &#x60;GET /admin/cost_totals&#x60; and always answers the net of each currency by period with its &#x60;balance&#x60; against the costs, whatever &#x60;income_visibility&#x60; and &#x60;finance_visibility&#x60; publish. &#x60;plans&#x60;, the number of members of each supporter plan now, is present while the instance setting &#x60;finance_viewer_income&#x60; is &#x60;plans&#x60; or &#x60;income_visibility&#x60; publishes the plans; a holder of &#x60;manage_finance&#x60; always reads it. No answer carries individual payments, the subscribers, the amounts of each plan, or the gross and the fees. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. Needs the &#x60;view_finance&#x60; or the &#x60;manage_finance&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FinanceApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : IncomeTotals = apiInstance.getFinanceIncomeTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FinanceApi#getFinanceIncomeTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FinanceApi#getFinanceIncomeTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**IncomeTotals**](IncomeTotals.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

