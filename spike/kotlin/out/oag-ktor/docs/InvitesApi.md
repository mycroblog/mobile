# InvitesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createOwnInvite**](InvitesApi.md#createOwnInvite) | **POST** /invites | Create an invite of the user&#39;s own |
| [**listOwnInvites**](InvitesApi.md#listOwnInvites) | **GET** /invites | List the user&#39;s own invites |
| [**revokeOwnInvite**](InvitesApi.md#revokeOwnInvite) | **POST** /invites/{id}/revoke | Revoke an invite of the user&#39;s own |


<a id="createOwnInvite"></a>
# **createOwnInvite**
> OwnInvite createOwnInvite(idempotencyKey, createOwnInviteRequest)

Create an invite of the user&#39;s own

Creates an invite with a new random code that people use to sign up while the registration mode is &#x60;open&#x60;, &#x60;approval&#x60;, or &#x60;invite&#x60;. In &#x60;approval&#x60; mode a person who signs up with it is approved at once. The user may create one when the instance setting &#x60;invite_creators&#x60; is &#x60;everyone&#x60; or their roles have &#x60;create_invites&#x60;, their account is neither suspended nor limited, their registration is approved, and the registration mode is not &#x60;closed&#x60;; otherwise a 403 &#x60;/api/v1/problems/invites-not-allowed&#x60; whose &#x60;reason&#x60; says which. &#x60;max_uses&#x60; defaults to 1 and &#x60;expires_in&#x60; to 7 days (or the caps when they are lower); a value over the cap of the instance settings (&#x60;member_invite_max_uses&#x60;, &#x60;member_invite_max_lifetime_seconds&#x60;) is a 422 naming it. A user who already holds &#x60;member_invite_max_active&#x60; usable invites gets a 409 &#x60;/api/v1/problems/too-many-invites&#x60;. The creation is recorded in the audit log with the user as the actor. Every sign-up with the invite checks again that the user may create invites; while they may not, the invite does not work.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InvitesApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val createOwnInviteRequest : CreateOwnInviteRequest =  // CreateOwnInviteRequest | 
try {
    val result : OwnInvite = apiInstance.createOwnInvite(idempotencyKey, createOwnInviteRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InvitesApi#createOwnInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InvitesApi#createOwnInvite")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **createOwnInviteRequest** | [**CreateOwnInviteRequest**](CreateOwnInviteRequest.md)|  | [optional] |

### Return type

[**OwnInvite**](OwnInvite.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listOwnInvites"></a>
# **listOwnInvites**
> OwnInvitePage listOwnInvites(cursor, limit)

List the user&#39;s own invites

The invites that the user created as a member, newest first, with how they have been used, and what the user may do: whether they may create an invite now (&#x60;can_create&#x60;) and why not (&#x60;cannot_create_reason&#x60;), the caps of the instance settings, and how many usable invites they hold. A user who may no longer create invites still lists and revokes the ones they made; an otherwise usable one is then &#x60;creator_ineligible&#x60;. Invites of the staff (&#x60;/admin/invites&#x60;) are not listed.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InvitesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : OwnInvitePage = apiInstance.listOwnInvites(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InvitesApi#listOwnInvites")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InvitesApi#listOwnInvites")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**OwnInvitePage**](OwnInvitePage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeOwnInvite"></a>
# **revokeOwnInvite**
> OwnInvite revokeOwnInvite(id, idempotencyKey)

Revoke an invite of the user&#39;s own

The invite stops working at once; the people who signed up with it keep their accounts. Revoking a revoked invite changes nothing. The revocation is recorded in the audit log with the user as the actor. A user revokes their own invites even when they may no longer create invites. An unknown ID, or an invite that the user did not create as a member, is a 404.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = InvitesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the invite.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : OwnInvite = apiInstance.revokeOwnInvite(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InvitesApi#revokeOwnInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InvitesApi#revokeOwnInvite")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the invite. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**OwnInvite**](OwnInvite.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

