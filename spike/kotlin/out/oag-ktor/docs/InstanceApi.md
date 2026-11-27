# InstanceApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getInstance**](InstanceApi.md#getInstance) | **GET** /instance | Get the public information of the instance |
| [**getInstanceCostTotals**](InstanceApi.md#getInstanceCostTotals) | **GET** /instance/cost_totals | Total the instance&#39;s public costs by period |
| [**getInstanceDocument**](InstanceApi.md#getInstanceDocument) | **GET** /instance/documents/{kind} | Get a document of the instance |
| [**getInstanceIncomeTotals**](InstanceApi.md#getInstanceIncomeTotals) | **GET** /instance/income_totals | Total the instance&#39;s public income by period |
| [**getInstanceOverview**](InstanceApi.md#getInstanceOverview) | **GET** /instance/overview | Get the counts of the instance |
| [**getInstanceTipAmounts**](InstanceApi.md#getInstanceTipAmounts) | **GET** /instance/tip_amounts | Get the amounts of the tips |
| [**getServerHealth**](InstanceApi.md#getServerHealth) | **GET** /instance/health | Get the health of the server |
| [**listCustomEmojis**](InstanceApi.md#listCustomEmojis) | **GET** /custom_emojis | List the custom emoji of the picker |
| [**listInstanceDomainActions**](InstanceApi.md#listInstanceDomainActions) | **GET** /instance/domain_actions | List the domains that the instance moderates |
| [**listInstanceSupporters**](InstanceApi.md#listInstanceSupporters) | **GET** /instance/supporters | List the supporters who chose to be listed |
| [**listSupporterPlans**](InstanceApi.md#listSupporterPlans) | **GET** /instance/supporter_plans | List the supporter plans |


<a id="getInstance"></a>
# **getInstance**
> Instance getInstance()

Get the public information of the instance

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : Instance = apiInstance.getInstance()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstance")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstance")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Instance**](Instance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceCostTotals"></a>
# **getInstanceCostTotals**
> CostTotals getInstanceCostTotals(granularity, from, to, basis)

Total the instance&#39;s public costs by period

The totals of the instance&#39;s costs that the instance setting &#x60;finance_visibility&#x60; publishes. It takes the query of &#x60;GET /admin/cost_totals&#x60; and answers the same shape: &#x60;private&#x60; makes it a 404; &#x60;totals&#x60; leaves every &#x60;breakdown&#x60; empty and the &#x60;rules&#x60; without &#x60;sources&#x60;, which would name the providers; &#x60;breakdown&#x60; gives the breakdown by provider, category, and currency, and the providers&#39; rules. No level returns the names, memos, or individual payments of the cost items. The periods stop at the one that holds today (UTC): a &#x60;to&#x60; after today reads as today, and a &#x60;from&#x60; after today gives no period. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period; a date after today reads as today.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : CostTotals = apiInstance.getInstanceCostTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstanceCostTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstanceCostTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period; a date after today reads as today. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**CostTotals**](CostTotals.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceDocument"></a>
# **getInstanceDocument**
> InstanceDocument getInstanceDocument(kind)

Get a document of the instance

The body of the terms of service (&#x60;terms&#x60;), the privacy policy (&#x60;privacy_policy&#x60;) or the commerce disclosures of the supporter plans (&#x60;commerce_disclosure&#x60;) that the instance has published, as sanitized HTML with the time of its last revision. A 404 when the instance has not published the document (&#x60;Instance.terms_url&#x60;, &#x60;Instance.privacy_policy_url&#x60; and &#x60;Instance.commerce_disclosure_url&#x60; are omitted then), and for an unknown kind. The web client shows it at &#x60;/about/terms&#x60;, &#x60;/about/privacy&#x60; and &#x60;/about/commerce&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
val kind : InstanceDocumentKind =  // InstanceDocumentKind | The kind of the document.
try {
    val result : InstanceDocument = apiInstance.getInstanceDocument(kind)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstanceDocument")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstanceDocument")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**InstanceDocumentKind**](.md)| The kind of the document. | [enum: terms, privacy_policy, commerce_disclosure] |

### Return type

[**InstanceDocument**](InstanceDocument.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceIncomeTotals"></a>
# **getInstanceIncomeTotals**
> IncomeTotals getInstanceIncomeTotals(granularity, from, to, basis)

Total the instance&#39;s public income by period

The net income that the instance setting &#x60;income_visibility&#x60; publishes. It takes the query of &#x60;GET /admin/cost_totals&#x60;: &#x60;private&#x60; makes it a 404; &#x60;totals&#x60; gives the net of each currency by period; &#x60;plans&#x60; adds &#x60;plans&#x60;, the number of members of each supporter plan now. Each period carries &#x60;balance&#x60;, the net less the costs, only while &#x60;finance_visibility&#x60; is not &#x60;private&#x60; either, since the balance would otherwise tell the hidden side. No level returns individual payments, the subscribers, the amounts of each plan, or the gross and the fees. The periods stop at the one that holds today (UTC): a &#x60;to&#x60; after today reads as today, and a &#x60;from&#x60; after today gives no period. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period; a date after today reads as today.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : IncomeTotals = apiInstance.getInstanceIncomeTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstanceIncomeTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstanceIncomeTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period; a date after today reads as today. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**IncomeTotals**](IncomeTotals.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceOverview"></a>
# **getInstanceOverview**
> InstanceOverview getInstanceOverview()

Get the counts of the instance

What the landing page shows about the instance besides &#x60;GET /instance&#x60;: how many members it has and how active they are. Only local, public data: the members are counted, never listed, and only public posts count. The instance setting &#x60;public_counts&#x60; decides whether it shows them; while it is off, they are left out. The counts are read again every few minutes, so they may lag behind. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : InstanceOverview = apiInstance.getInstanceOverview()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstanceOverview")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstanceOverview")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InstanceOverview**](InstanceOverview.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceTipAmounts"></a>
# **getInstanceTipAmounts**
> TipAmounts getInstanceTipAmounts()

Get the amounts of the tips

The amounts that a tip, a one-time payment to the server&#39;s operation with nothing in return, may be: the operator&#39;s choices, smallest first, in one currency. A 404 while the server takes no tips (&#x60;Instance.tips_enabled&#x60;). To a request whose access token grants &#x60;read:supporters&#x60;, it also says why the user may not tip when they may not (&#x60;ineligible_reason&#x60;, the check that &#x60;POST /supporter/tips&#x60; makes), and that answer is &#x60;no-store&#x60;; a request without a token, or with one that does not grant the scope, gets the amounts alone. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : TipAmounts = apiInstance.getInstanceTipAmounts()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getInstanceTipAmounts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getInstanceTipAmounts")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TipAmounts**](TipAmounts.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getServerHealth"></a>
# **getServerHealth**
> ServerHealth getServerHealth()

Get the health of the server

Whether the server can serve requests, for uptime monitors, status pages, and clients that tell a server outage from their own network. The check is the readiness check&#39;s: the database is reachable and its schema is applied. Each process caches the result for 60 seconds and runs one check for concurrent requests, so the database is checked at most once a minute however often the endpoint is called. The endpoint takes no access token and counts against no rate limit.  The body holds only the fixed words of &#x60;status&#x60;, never the reason of a failure. During the maintenance mode the answer is a 503 with &#x60;Retry-After&#x60; and &#x60;{\&quot;status\&quot;:\&quot;maintenance\&quot;}&#x60;, without touching the database. Uptime monitors tell the maintenance from an outage by the body. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : ServerHealth = apiInstance.getServerHealth()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#getServerHealth")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#getServerHealth")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ServerHealth**](ServerHealth.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listCustomEmojis"></a>
# **listCustomEmojis**
> PickerCustomEmojiList listCustomEmojis()

List the custom emoji of the picker

The custom emoji of the instance that the picker offers: every enabled local emoji that is not hidden from the picker, by category (those without one first) and shortcode. A text can use every enabled local emoji, hidden or not. The list is not paginated. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : PickerCustomEmojiList = apiInstance.listCustomEmojis()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#listCustomEmojis")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#listCustomEmojis")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PickerCustomEmojiList**](PickerCustomEmojiList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceDomainActions"></a>
# **listInstanceDomainActions**
> InstanceDomainActionPage listInstanceDomainActions(cursor, limit)

List the domains that the instance moderates

The moderation actions in effect on remote domains that the instance lists publicly: &#x60;limit&#x60; and &#x60;suspend&#x60;, one item per action, newest first. The instance setting &#x60;domain_actions_visibility&#x60;, which &#x60;GET /instance&#x60; gives, decides who may read it: &#x60;off&#x60; makes it a 404, &#x60;signed_in&#x60; a 401 &#x60;/api/v1/problems/unauthenticated&#x60; for a request without an access token, and &#x60;everyone&#x60; serves everyone. The setting &#x60;domain_actions_rationale_visibility&#x60; decides, in the same way, who gets each action&#39;s public comment. The moderators&#39; private notes are never listed. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : InstanceDomainActionPage = apiInstance.listInstanceDomainActions(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#listInstanceDomainActions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#listInstanceDomainActions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**InstanceDomainActionPage**](InstanceDomainActionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceSupporters"></a>
# **listInstanceSupporters**
> SupporterListPage listInstanceSupporters(planId, cursor, limit)

List the supporters who chose to be listed

The members of the supporter plans who chose to be listed (&#x60;PUT /preferences/supporter_listing&#x60;), by plan in the plans&#39; order and newest first within a plan, with the plans that are not retired for the headings. Only each member&#39;s account is listed: never an amount, an interval, a date, or a count. Accounts that the viewer may not see are left out, so a page may be shorter than &#x60;limit&#x60;. The instance setting &#x60;supporter_list_visibility&#x60; decides who may read it: &#x60;off&#x60; makes it a 404, &#x60;signed_in&#x60; a 401 &#x60;/api/v1/problems/unauthenticated&#x60; for a request without an access token, and &#x60;public&#x60; serves everyone. The responses always carry &#x60;X-Robots-Tag: noindex, noarchive&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
val planId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | Only the members of this plan.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : SupporterListPage = apiInstance.listInstanceSupporters(planId, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#listInstanceSupporters")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#listInstanceSupporters")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **planId** | **java.util.UUID**| Only the members of this plan. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**SupporterListPage**](SupporterListPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listSupporterPlans"></a>
# **listSupporterPlans**
> SupporterPlanList listSupporterPlans()

List the supporter plans

The supporter plans that take subscriptions, by rank, with their monthly and yearly prices. A 404 while the supporter program is disabled (&#x60;Instance.supporters_enabled&#x60;). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InstanceApi()
try {
    val result : SupporterPlanList = apiInstance.listSupporterPlans()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstanceApi#listSupporterPlans")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstanceApi#listSupporterPlans")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SupporterPlanList**](SupporterPlanList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

