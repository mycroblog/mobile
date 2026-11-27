# SessionsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**listSessions**](SessionsApi.md#listSessions) | **GET** /sessions | List the browsers signed in to the authenticated user&#39;s account |
| [**revokeOtherSessions**](SessionsApi.md#revokeOtherSessions) | **POST** /sessions/revoke_others | Sign out every browser but the one that asks |
| [**revokeSession**](SessionsApi.md#revokeSession) | **DELETE** /sessions/{id} | Sign out one of the browsers signed in to the account |


<a id="listSessions"></a>
# **listSessions**
> BrowserSessionList listSessions()

List the browsers signed in to the authenticated user&#39;s account

One entry per browser session that still works or from which a grant of the server&#39;s own web client still works, the browser that asks first, then the most recently used first. The apps the user authorized are not listed here (&#x60;/authorized_apps&#x60;). Only the server&#39;s own clients may call it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SessionsApi()
try {
    val result : BrowserSessionList = apiInstance.listSessions()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SessionsApi#listSessions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SessionsApi#listSessions")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**BrowserSessionList**](BrowserSessionList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeOtherSessions"></a>
# **revokeOtherSessions**
> revokeOtherSessions(idempotencyKey)

Sign out every browser but the one that asks

Does for every other browser of the list what &#x60;DELETE /sessions/{id}&#x60; does for one, and succeeds when there is no other browser. Only the server&#39;s own clients may call it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SessionsApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeOtherSessions(idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling SessionsApi#revokeOtherSessions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SessionsApi#revokeOtherSessions")
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

<a id="revokeSession"></a>
# **revokeSession**
> revokeSession(id, idempotencyKey)

Sign out one of the browsers signed in to the account

Ends the browser session and the web client&#39;s grants that started from it, so that their access and refresh tokens stop working at once (the next request with one is a 401). The password, two-factor authentication, and the apps the user authorized stay. An ID that is not in the user&#39;s list is a 404, and the browser that asks is a 409 with the type &#x60;/api/v1/problems/current-session&#x60;: signing out ends it. Only the server&#39;s own clients may call it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = SessionsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the session (the `id` of a browser session).
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeSession(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling SessionsApi#revokeSession")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SessionsApi#revokeSession")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the session (the &#x60;id&#x60; of a browser session). | |
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

