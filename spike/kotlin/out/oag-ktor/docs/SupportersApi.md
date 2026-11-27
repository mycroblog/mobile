# SupportersApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**cancelOwnSupporterScheduledChange**](SupportersApi.md#cancelOwnSupporterScheduledChange) | **POST** /supporter/subscription/cancel_scheduled_change | Cancel the scheduled change of the authenticated user&#39;s subscription |
| [**cancelOwnSupporterSubscription**](SupportersApi.md#cancelOwnSupporterSubscription) | **POST** /supporter/subscription/cancel | Cancel the authenticated user&#39;s subscription at the end of its period |
| [**changeOwnSupporterSubscription**](SupportersApi.md#changeOwnSupporterSubscription) | **POST** /supporter/subscription/change | Change the plan or the interval of the authenticated user&#39;s subscription |
| [**getOwnSupporterSubscription**](SupportersApi.md#getOwnSupporterSubscription) | **GET** /supporter/subscription | Get the authenticated user&#39;s supporter subscription |
| [**getOwnSupporterTip**](SupportersApi.md#getOwnSupporterTip) | **GET** /supporter/tips/{id} | Get a tip of the authenticated user |
| [**openSupporterPortal**](SupportersApi.md#openSupporterPortal) | **POST** /supporter/portal | Open the payment provider&#39;s portal of the authenticated user |
| [**resumeOwnSupporterSubscription**](SupportersApi.md#resumeOwnSupporterSubscription) | **POST** /supporter/subscription/resume | Resume the authenticated user&#39;s canceling subscription |
| [**startSupporterCheckout**](SupportersApi.md#startSupporterCheckout) | **POST** /supporter/checkout | Start a subscription to a supporter plan |
| [**startSupporterTip**](SupportersApi.md#startSupporterTip) | **POST** /supporter/tips | Start a tip |
| [**syncOwnSupporterSubscription**](SupportersApi.md#syncOwnSupporterSubscription) | **POST** /supporter/subscription/sync | Re-read the authenticated user&#39;s subscription from the payment provider |


<a id="cancelOwnSupporterScheduledChange"></a>
# **cancelOwnSupporterScheduledChange**
> SupporterSubscription cancelOwnSupporterScheduledChange(idempotencyKey)

Cancel the scheduled change of the authenticated user&#39;s subscription

Cancels the change to a lower plan or to the monthly price that waits for the end of the period; the subscription renews at its current plan and interval. A 409 &#x60;/api/v1/problems/supporter-subscription-state&#x60; without a scheduled change. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SupporterSubscription = apiInstance.cancelOwnSupporterScheduledChange(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#cancelOwnSupporterScheduledChange")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#cancelOwnSupporterScheduledChange")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SupporterSubscription**](SupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="cancelOwnSupporterSubscription"></a>
# **cancelOwnSupporterSubscription**
> SupporterSubscription cancelOwnSupporterSubscription(idempotencyKey)

Cancel the authenticated user&#39;s subscription at the end of its period

Cancels an &#x60;active&#x60; or &#x60;past_due&#x60; subscription at the end of its period: the plan&#39;s role stays until then (&#x60;canceling&#x60;). A 409 &#x60;/api/v1/problems/supporter-subscription-state&#x60; in another state. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SupporterSubscription = apiInstance.cancelOwnSupporterSubscription(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#cancelOwnSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#cancelOwnSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SupporterSubscription**](SupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="changeOwnSupporterSubscription"></a>
# **changeOwnSupporterSubscription**
> SupporterSubscription changeOwnSupporterSubscription(supporterPlanChoice, idempotencyKey)

Change the plan or the interval of the authenticated user&#39;s subscription

Changes an &#x60;active&#x60; subscription to another plan or interval: to a higher plan or to the yearly price once the difference, charged pro rata at once, is paid; to a lower plan or to the monthly price at the end of the period, as &#x60;scheduled_change&#x60;. The new plan&#39;s current price applies. A 402 &#x60;/api/v1/problems/supporter-payment-failed&#x60; when the payment fails, and a 409 &#x60;/api/v1/problems/supporter-payment-processing&#x60; while it has not settled: in both the plan stays. A 409 &#x60;/api/v1/problems/supporter-subscription-state&#x60; unless the subscription is &#x60;active&#x60;, a 422 &#x60;/api/v1/problems/supporter-plan-unavailable&#x60; for a plan that takes no subscriptions, and a 422 &#x60;/api/v1/problems/supporter-same-plan&#x60; for the current plan and interval. An unknown plan is a 404, no subscription a 409 &#x60;/api/v1/problems/supporter-subscription-state&#x60;, and an interval that is neither &#x60;month&#x60; nor &#x60;year&#x60; a 422. While the supporter program is off, the operation answers the 404 or the 503 &#x60;/api/v1/problems/supporters-unavailable&#x60; that says what is missing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val supporterPlanChoice : SupporterPlanChoice =  // SupporterPlanChoice | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SupporterSubscription = apiInstance.changeOwnSupporterSubscription(supporterPlanChoice, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#changeOwnSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#changeOwnSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **supporterPlanChoice** | [**SupporterPlanChoice**](SupporterPlanChoice.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SupporterSubscription**](SupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="getOwnSupporterSubscription"></a>
# **getOwnSupporterSubscription**
> OwnSupporterSubscription getOwnSupporterSubscription()

Get the authenticated user&#39;s supporter subscription

The user&#39;s subscription that goes on (any state but &#x60;expired&#x60;, &#x60;revoked&#x60;, and &#x60;abandoned&#x60;), and why the user cannot subscribe when they cannot. A 404 while the supporter program is disabled (&#x60;Instance.supporters_enabled&#x60;). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
try {
    val result : OwnSupporterSubscription = apiInstance.getOwnSupporterSubscription()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#getOwnSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#getOwnSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OwnSupporterSubscription**](OwnSupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getOwnSupporterTip"></a>
# **getOwnSupporterTip**
> SupporterTip getOwnSupporterTip(id)

Get a tip of the authenticated user

The state of a tip of the authenticated user: &#x60;open&#x60; until its payment or the end of its checkout, then &#x60;paid&#x60; or &#x60;expired&#x60;. While it is &#x60;open&#x60;, reading it makes the server read the checkout again from the payment provider, so the return from the checkout reads it again shortly until it is &#x60;paid&#x60;. Another user&#39;s tip and an unknown one are a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the tip.
try {
    val result : SupporterTip = apiInstance.getOwnSupporterTip(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#getOwnSupporterTip")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#getOwnSupporterTip")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the tip. | |

### Return type

[**SupporterTip**](SupporterTip.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="openSupporterPortal"></a>
# **openSupporterPortal**
> SupporterPortal openSupporterPortal()

Open the payment provider&#39;s portal of the authenticated user

The payment provider&#39;s portal of the user&#39;s payment methods and invoices, which returns to &#x60;/settings/supporter&#x60; of the web client. Plans change on this server, not in the portal. A 409 &#x60;/api/v1/problems/subscription-ended&#x60; for a user whom the provider does not know. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
try {
    val result : SupporterPortal = apiInstance.openSupporterPortal()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#openSupporterPortal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#openSupporterPortal")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SupporterPortal**](SupporterPortal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="resumeOwnSupporterSubscription"></a>
# **resumeOwnSupporterSubscription**
> SupporterSubscription resumeOwnSupporterSubscription(idempotencyKey)

Resume the authenticated user&#39;s canceling subscription

Takes back the cancellation of a &#x60;canceling&#x60; subscription, which then renews as before. A 409 &#x60;/api/v1/problems/supporter-subscription-state&#x60; in another state. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SupporterSubscription = apiInstance.resumeOwnSupporterSubscription(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#resumeOwnSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#resumeOwnSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SupporterSubscription**](SupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="startSupporterCheckout"></a>
# **startSupporterCheckout**
> SupporterCheckout startSupporterCheckout(supporterPlanChoice)

Start a subscription to a supporter plan

Starts a subscription to a plan at an interval and returns the payment provider&#39;s hosted checkout, which the client opens in the same tab; no card details reach this server. The checkout returns to &#x60;/settings/supporter&#x60; of the web client. Starting again while the subscription is &#x60;pending&#x60; continues the open checkout for the same plan and interval, or expires it and makes a new one of the same subscription, so that one checkout at most can take a payment. When one was paid already, &#x60;redirect_url&#x60; is the return to &#x60;/settings/supporter&#x60; instead of a checkout. Only a local user with a verified primary address and without another subscription may subscribe: a 422 &#x60;/api/v1/problems/supporter-ineligible&#x60; otherwise, a 409 &#x60;/api/v1/problems/supporter-subscribed&#x60; with a subscription that is not pending, and a 422 &#x60;/api/v1/problems/supporter-plan-unavailable&#x60; for a plan that takes no subscriptions. A 404 for an unknown plan and while the program is disabled. From the announcement of the closure of the service on, nobody subscribes: a 409 &#x60;/api/v1/problems/service-closing&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val supporterPlanChoice : SupporterPlanChoice =  // SupporterPlanChoice | 
try {
    val result : SupporterCheckout = apiInstance.startSupporterCheckout(supporterPlanChoice)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#startSupporterCheckout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#startSupporterCheckout")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **supporterPlanChoice** | [**SupporterPlanChoice**](SupporterPlanChoice.md)|  | |

### Return type

[**SupporterCheckout**](SupporterCheckout.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="startSupporterTip"></a>
# **startSupporterTip**
> SupporterTipCheckout startSupporterTip(supporterTipRequest)

Start a tip

Starts a tip of one of the amounts of &#x60;GET /instance/tip_amounts&#x60; and returns the payment provider&#39;s hosted checkout of that one payment, which the client opens in the same tab; no card details reach this server. The checkout returns to &#x60;/settings/supporter/tip&#x60; of the web client with &#x60;tip&#x60; (the tip&#39;s ID) and &#x60;checkout&#x60; (&#x60;success&#x60; or &#x60;canceled&#x60;) in its query. A user has one open tip at most: starting again makes the open tip&#39;s checkout unpayable first, and when it was paid already, &#x60;redirect_url&#x60; is the return of that tip instead of a checkout. Only a local user with a verified primary address may tip: a 422 &#x60;/api/v1/problems/supporter-ineligible&#x60; otherwise, and a 422 &#x60;/api/v1/problems/supporter-tip-amount&#x60; for an amount that is not one of the choices. The starts are limited per user (5 an hour, 20 a day) and for the whole server (100 an hour): a 429 beyond them, with &#x60;Retry-After&#x60;. A 409 &#x60;/api/v1/problems/supporter-tip-pending&#x60; when another start of the user&#39;s runs at the same time. A 404 while the server takes no tips. From the announcement of the closure of the service on, nobody tips: a 409 &#x60;/api/v1/problems/service-closing&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val supporterTipRequest : SupporterTipRequest =  // SupporterTipRequest | 
try {
    val result : SupporterTipCheckout = apiInstance.startSupporterTip(supporterTipRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#startSupporterTip")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#startSupporterTip")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **supporterTipRequest** | [**SupporterTipRequest**](SupporterTipRequest.md)|  | |

### Return type

[**SupporterTipCheckout**](SupporterTipCheckout.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="syncOwnSupporterSubscription"></a>
# **syncOwnSupporterSubscription**
> OwnSupporterSubscription syncOwnSupporterSubscription(idempotencyKey)

Re-read the authenticated user&#39;s subscription from the payment provider

What the web client sends when the payment provider&#39;s checkout or portal returns to it: it queues a re-read of the user&#39;s subscription from the provider, without waiting for its webhook, and answers the subscription as it is now. Read it again to see the result. A 404 while the program is disabled. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SupportersApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : OwnSupporterSubscription = apiInstance.syncOwnSupporterSubscription(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SupportersApi#syncOwnSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SupportersApi#syncOwnSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**OwnSupporterSubscription**](OwnSupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

