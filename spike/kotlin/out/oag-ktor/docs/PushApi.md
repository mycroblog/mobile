# PushApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**deletePushSubscription**](PushApi.md#deletePushSubscription) | **DELETE** /push/subscription | Delete the Web Push subscription of the grant |
| [**getPushSubscription**](PushApi.md#getPushSubscription) | **GET** /push/subscription | Get the Web Push subscription of the grant |
| [**putPushSubscription**](PushApi.md#putPushSubscription) | **PUT** /push/subscription | Register the Web Push subscription of the grant |


<a id="deletePushSubscription"></a>
# **deletePushSubscription**
> deletePushSubscription(idempotencyKey)

Delete the Web Push subscription of the grant

Deleting it when there is none changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PushApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deletePushSubscription(idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling PushApi#deletePushSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PushApi#deletePushSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="getPushSubscription"></a>
# **getPushSubscription**
> PushSubscription getPushSubscription()

Get the Web Push subscription of the grant

The subscription that this grant (the access token and the tokens that refreshing it issues) registered; a 404 when it has none. A subscription ends when the push service reports it expired or the grant ends (sign-out, revocation, expiry). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PushApi()
try {
    val result : PushSubscription = apiInstance.getPushSubscription()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PushApi#getPushSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PushApi#getPushSubscription")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PushSubscription**](PushSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="putPushSubscription"></a>
# **putPushSubscription**
> PushSubscription putPushSubscription(pushSubscriptionRequest, idempotencyKey)

Register the Web Push subscription of the grant

Registers the browser&#39;s push subscription (&#x60;PushSubscription.toJSON()&#x60;, subscribed with the instance&#39;s &#x60;push_server_key&#x60;) for the user&#39;s notifications of the &#x60;alerts&#x60; types, replacing the grant&#39;s earlier subscription and any subscription with the same endpoint. Sending it again with other &#x60;alerts&#x60; changes the types. The endpoint must be an &#x60;https&#x60; URL; an invalid endpoint, key, or type is a 422 that names the property. A 404 means that the instance sends no Web Push (&#x60;Instance.push_server_key&#x60; is omitted). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PushApi()
val pushSubscriptionRequest : PushSubscriptionRequest =  // PushSubscriptionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : PushSubscription = apiInstance.putPushSubscription(pushSubscriptionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PushApi#putPushSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PushApi#putPushSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pushSubscriptionRequest** | [**PushSubscriptionRequest**](PushSubscriptionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**PushSubscription**](PushSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

