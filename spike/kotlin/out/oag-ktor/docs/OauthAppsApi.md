# OauthAppsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createOAuthApp**](OauthAppsApi.md#createOAuthApp) | **POST** /oauth_apps | Register an OAuth app |
| [**deleteOAuthApp**](OauthAppsApi.md#deleteOAuthApp) | **DELETE** /oauth_apps/{id} | Delete an OAuth app |
| [**getOAuthApp**](OauthAppsApi.md#getOAuthApp) | **GET** /oauth_apps/{id} | Get an OAuth app that the authenticated user registered |
| [**issueOAuthAppToken**](OauthAppsApi.md#issueOAuthAppToken) | **POST** /oauth_apps/{id}/tokens | Issue tokens of an OAuth app to the authenticated user |
| [**listOAuthApps**](OauthAppsApi.md#listOAuthApps) | **GET** /oauth_apps | List the OAuth apps that the authenticated user registered |
| [**rotateOAuthAppSecret**](OauthAppsApi.md#rotateOAuthAppSecret) | **POST** /oauth_apps/{id}/rotate_secret | Issue a new secret to a confidential OAuth app |
| [**updateOAuthApp**](OauthAppsApi.md#updateOAuthApp) | **PATCH** /oauth_apps/{id} | Change an OAuth app |


<a id="createOAuthApp"></a>
# **createOAuthApp**
> OAuthAppCreated createOAuthApp(createOAuthAppRequest)

Register an OAuth app

Registers a public or confidential app of the user for a bot, a script, or a client of their own. A confidential app&#39;s &#x60;client_secret&#x60; is in this response only. Other users authorize the app at &#x60;/oauth/authorize&#x60; as any third-party app, and the user may issue its tokens to their own account with &#x60;POST /oauth_apps/{id}/tokens&#x60;. The app stops working while the user cannot use the server (suspended or deleted).  A setting that breaks a rule is a 422 naming it, a user with 25 apps a 409 &#x60;/api/v1/problems/too-many-oauth-apps&#x60;, and a server that does not let users register apps (the instance setting &#x60;user_app_registration&#x60;) a 403 &#x60;/api/v1/problems/oauth-app-registration-disabled&#x60;. A user registers at most 5 apps an hour; past that the answer is 429. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val createOAuthAppRequest : CreateOAuthAppRequest =  // CreateOAuthAppRequest | 
try {
    val result : OAuthAppCreated = apiInstance.createOAuthApp(createOAuthAppRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#createOAuthApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#createOAuthApp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createOAuthAppRequest** | [**CreateOAuthAppRequest**](CreateOAuthAppRequest.md)|  | |

### Return type

[**OAuthAppCreated**](OAuthAppCreated.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteOAuthApp"></a>
# **deleteOAuthApp**
> deleteOAuthApp(id, idempotencyKey)

Delete an OAuth app

Deletes the app for good and revokes every token of it, also those of the other users who authorized it. An unknown or deleted app, or one that another user registered, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth app (not its `client_id`).
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteOAuthApp(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#deleteOAuthApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#deleteOAuthApp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth app (not its &#x60;client_id&#x60;). | |
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

<a id="getOAuthApp"></a>
# **getOAuthApp**
> OAuthApp getOAuthApp(id)

Get an OAuth app that the authenticated user registered

An unknown or deleted app, or one that another user registered, is a 404.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth app (not its `client_id`).
try {
    val result : OAuthApp = apiInstance.getOAuthApp(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#getOAuthApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#getOAuthApp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth app (not its &#x60;client_id&#x60;). | |

### Return type

[**OAuthApp**](OAuthApp.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="issueOAuthAppToken"></a>
# **issueOAuthAppToken**
> OAuthAppToken issueOAuthAppToken(id, issueOAuthAppTokenRequest)

Issue tokens of an OAuth app to the authenticated user

Issues an access token and a refresh token of the app for the user&#39;s own account, without the consent screen and a redirect: the usual pair with the usual lifetimes, refreshed and revoked at the token endpoints as any other. A scope beyond the app&#39;s is a 422 naming &#x60;scope&#x60;, and an app that an administrator disabled a 409 &#x60;/api/v1/problems/oauth-app-disabled&#x60;. An unknown or deleted app, or one that another user registered, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth app (not its `client_id`).
val issueOAuthAppTokenRequest : IssueOAuthAppTokenRequest =  // IssueOAuthAppTokenRequest | 
try {
    val result : OAuthAppToken = apiInstance.issueOAuthAppToken(id, issueOAuthAppTokenRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#issueOAuthAppToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#issueOAuthAppToken")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth app (not its &#x60;client_id&#x60;). | |
| **issueOAuthAppTokenRequest** | [**IssueOAuthAppTokenRequest**](IssueOAuthAppTokenRequest.md)|  | [optional] |

### Return type

[**OAuthAppToken**](OAuthAppToken.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listOAuthApps"></a>
# **listOAuthApps**
> OAuthAppList listOAuthApps()

List the OAuth apps that the authenticated user registered

The apps that the user registered and has not deleted, oldest first, and whether the user may register one now (&#x60;can_create&#x60;). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
try {
    val result : OAuthAppList = apiInstance.listOAuthApps()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#listOAuthApps")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#listOAuthApps")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OAuthAppList**](OAuthAppList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="rotateOAuthAppSecret"></a>
# **rotateOAuthAppSecret**
> OAuthAppSecret rotateOAuthAppSecret(id)

Issue a new secret to a confidential OAuth app

The old secret stops working at once: refreshing the app&#39;s tokens takes the new one, which is in this response only. The tokens of the app keep working. A public app is a 409 &#x60;/api/v1/problems/public-oauth-app&#x60;. An unknown or deleted app, or one that another user registered, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth app (not its `client_id`).
try {
    val result : OAuthAppSecret = apiInstance.rotateOAuthAppSecret(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#rotateOAuthAppSecret")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#rotateOAuthAppSecret")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth app (not its &#x60;client_id&#x60;). | |

### Return type

[**OAuthAppSecret**](OAuthAppSecret.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateOAuthApp"></a>
# **updateOAuthApp**
> OAuthApp updateOAuthApp(id, updateOAuthAppRequest, idempotencyKey)

Change an OAuth app

Changes what the request names and keeps the rest, checked as for a new app; a setting that breaks a rule is a 422 naming it. The type (public or confidential) never changes. The tokens of the app keep working. An unknown or deleted app, or one that another user registered, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = OauthAppsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth app (not its `client_id`).
val updateOAuthAppRequest : UpdateOAuthAppRequest =  // UpdateOAuthAppRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : OAuthApp = apiInstance.updateOAuthApp(id, updateOAuthAppRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OauthAppsApi#updateOAuthApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OauthAppsApi#updateOAuthApp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth app (not its &#x60;client_id&#x60;). | |
| **updateOAuthAppRequest** | [**UpdateOAuthAppRequest**](UpdateOAuthAppRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**OAuthApp**](OAuthApp.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

