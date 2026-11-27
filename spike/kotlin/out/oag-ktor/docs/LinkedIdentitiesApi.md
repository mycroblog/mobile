# LinkedIdentitiesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**beginExternalConfirmation**](LinkedIdentitiesApi.md#beginExternalConfirmation) | **POST** /linked_identities/confirmation | Start a confirmation with a linked login provider |
| [**beginLinkIdentity**](LinkedIdentitiesApi.md#beginLinkIdentity) | **POST** /linked_identities | Start linking an account at a login provider |
| [**listLinkedIdentities**](LinkedIdentitiesApi.md#listLinkedIdentities) | **GET** /linked_identities | List the authenticated user&#39;s linked accounts and the login providers |
| [**unlinkIdentity**](LinkedIdentitiesApi.md#unlinkIdentity) | **DELETE** /linked_identities/{id} | Unlink an account at a login provider |


<a id="beginExternalConfirmation"></a>
# **beginExternalConfirmation**
> ExternalAuthorizationStart beginExternalConfirmation(externalConfirmationRequest)

Start a confirmation with a linked login provider

Returns the &#x60;start_url&#x60; that the browser opens to sign in again at a provider that the user links an account at, which confirms &#x60;operation&#x60; in place of the password or a passkey. The browser must be signed in to the server&#39;s sign-in pages as the same user; one that is not signs in first. An OpenID Connect provider is asked to authenticate the user again, and an authentication older than the request does not count. The provider sends the browser back to &#x60;return_to&#x60; with the outcome in the fragment, next to &#x60;operation&#x60; and &#x60;provider&#x60;: &#x60;external_confirmation&#x3D;&lt;token&gt;&#x60;, which the operation takes once in its &#x60;external_confirmation&#x60; within 10 minutes, or &#x60;external_error&#x3D;&lt;reason&gt;&#x60; where the reason is &#x60;mismatch&#x60; (the user signed in with an account that the user does not link), &#x60;denied&#x60;, &#x60;unavailable&#x60;, &#x60;failed&#x60;, &#x60;rate_limited&#x60;, or &#x60;expired&#x60;. A mismatch and a token that does not verify count against the wrong confirmations of the operation. A provider that the user links no account at, or that is disabled, is a 422 &#x60;/api/v1/problems/login-provider-unavailable&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = LinkedIdentitiesApi()
val externalConfirmationRequest : ExternalConfirmationRequest =  // ExternalConfirmationRequest | 
try {
    val result : ExternalAuthorizationStart = apiInstance.beginExternalConfirmation(externalConfirmationRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LinkedIdentitiesApi#beginExternalConfirmation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LinkedIdentitiesApi#beginExternalConfirmation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **externalConfirmationRequest** | [**ExternalConfirmationRequest**](ExternalConfirmationRequest.md)|  | |

### Return type

[**ExternalAuthorizationStart**](ExternalAuthorizationStart.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="beginLinkIdentity"></a>
# **beginLinkIdentity**
> ExternalAuthorizationStart beginLinkIdentity(linkIdentityRequest)

Start linking an account at a login provider

Checks the confirmation (&#x60;password&#x60;, &#x60;passkey&#x60;, or &#x60;external_confirmation&#x60; of the &#x60;link&#x60; operation) and returns the &#x60;start_url&#x60; that the browser opens to sign in at the provider. The browser must be signed in to the server&#39;s sign-in pages as the same user; one that is not signs in first. The provider sends the browser back to &#x60;return_to&#x60; with the outcome in the fragment, next to &#x60;provider&#x60;: &#x60;linked&#x3D;&lt;provider ID&gt;&#x60;, or &#x60;external_error&#x3D;&lt;reason&gt;&#x60; where the reason is &#x60;denied&#x60; (the provider&#39;s conditions refuse the account), &#x60;linked_elsewhere&#x60; (another user links the account), &#x60;already_linked&#x60;, &#x60;unavailable&#x60;, &#x60;failed&#x60;, &#x60;rate_limited&#x60;, or &#x60;expired&#x60;. A provider that is unknown, disabled, or does not allow links is a 422 &#x60;/api/v1/problems/login-provider-unavailable&#x60;, a provider that the user links an account at already a 409 &#x60;/api/v1/problems/login-provider-linked&#x60;, and a wrong confirmation as for the other confirmed operations; at most 5 wrong ones per user are accepted every hour. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = LinkedIdentitiesApi()
val linkIdentityRequest : LinkIdentityRequest =  // LinkIdentityRequest | 
try {
    val result : ExternalAuthorizationStart = apiInstance.beginLinkIdentity(linkIdentityRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LinkedIdentitiesApi#beginLinkIdentity")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LinkedIdentitiesApi#beginLinkIdentity")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **linkIdentityRequest** | [**LinkIdentityRequest**](LinkIdentityRequest.md)|  | |

### Return type

[**ExternalAuthorizationStart**](ExternalAuthorizationStart.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listLinkedIdentities"></a>
# **listLinkedIdentities**
> LinkedIdentityList listLinkedIdentities()

List the authenticated user&#39;s linked accounts and the login providers

The accounts at external login providers that the user links, which sign the user in and confirm the user&#39;s operations, oldest first, and the login providers that users sign in with, which the user can link an account at (&#x60;allow_link&#x60;) or confirm with. A server without login providers answers two empty lists. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = LinkedIdentitiesApi()
try {
    val result : LinkedIdentityList = apiInstance.listLinkedIdentities()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LinkedIdentitiesApi#listLinkedIdentities")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LinkedIdentitiesApi#listLinkedIdentities")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**LinkedIdentityList**](LinkedIdentityList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unlinkIdentity"></a>
# **unlinkIdentity**
> unlinkIdentity(id, idempotencyKey)

Unlink an account at a login provider

The account no longer signs the user in or confirms the user&#39;s operations. An unknown or unlinked link is a 404, and the last way to sign in of a user without a password and without a passkey a 409 &#x60;/api/v1/problems/last-sign-in-method&#x60;. While registration and sign-in are limited to the login providers (&#x60;login_providers_only&#x60; of &#x60;GET /instance&#x60;), a password and a passkey do not sign in, so the user&#39;s last link to a provider that signs in is a 409 too. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = LinkedIdentitiesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the link.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.unlinkIdentity(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling LinkedIdentitiesApi#unlinkIdentity")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LinkedIdentitiesApi#unlinkIdentity")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the link. | |
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

