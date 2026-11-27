# AuthorizedAppsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**listAuthorizedApps**](AuthorizedAppsApi.md#listAuthorizedApps) | **GET** /authorized_apps | List the apps the authenticated user authorized |
| [**revokeAuthorizedApp**](AuthorizedAppsApi.md#revokeAuthorizedApp) | **DELETE** /authorized_apps/{id} | Revoke an authorized app |


<a id="listAuthorizedApps"></a>
# **listAuthorizedApps**
> AuthorizedAppList listAuthorizedApps()

List the apps the authenticated user authorized

One entry per client for which the user holds a grant that still works, most recently used first (an app without a recorded use after those with one, newest authorization first). A revoked or expired grant is not listed. The server&#39;s own web client is listed too (&#x60;first_party&#x60;). Only the server&#39;s own clients may call it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AuthorizedAppsApi()
try {
    val result : AuthorizedAppList = apiInstance.listAuthorizedApps()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthorizedAppsApi#listAuthorizedApps")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthorizedAppsApi#listAuthorizedApps")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AuthorizedAppList**](AuthorizedAppList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeAuthorizedApp"></a>
# **revokeAuthorizedApp**
> revokeAuthorizedApp(id, idempotencyKey)

Revoke an authorized app

Revokes every grant of the app that the user holds, so that its access and refresh tokens stop working at once (the next request with one is a 401). Revoking the server&#39;s own web client signs the user out of every browser, this one included. An app the user holds no working grant of is a 404. Only the server&#39;s own clients may call it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AuthorizedAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the app (the `id` of an authorized app).
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeAuthorizedApp(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AuthorizedAppsApi#revokeAuthorizedApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthorizedAppsApi#revokeAuthorizedApp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the app (the &#x60;id&#x60; of an authorized app). | |
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

