# CustomEmojisApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createCustomEmojiRequest**](CustomEmojisApi.md#createCustomEmojiRequest) | **POST** /custom_emoji_requests | Request a custom emoji or a new image of one |
| [**deleteOwnCustomEmoji**](CustomEmojisApi.md#deleteOwnCustomEmoji) | **DELETE** /custom_emojis/mine/{id} | Delete a custom emoji that the user made |
| [**listCustomEmojiRequests**](CustomEmojisApi.md#listCustomEmojiRequests) | **GET** /custom_emoji_requests | List the user&#39;s custom emoji requests |
| [**listOwnCustomEmojis**](CustomEmojisApi.md#listOwnCustomEmojis) | **GET** /custom_emojis/mine | List the custom emoji that the user made |
| [**updateOwnCustomEmoji**](CustomEmojisApi.md#updateOwnCustomEmoji) | **PATCH** /custom_emojis/mine/{id} | Change a custom emoji that the user made |
| [**withdrawCustomEmojiRequest**](CustomEmojisApi.md#withdrawCustomEmojiRequest) | **POST** /custom_emoji_requests/{id}/withdraw | Withdraw a custom emoji request |


<a id="createCustomEmojiRequest"></a>
# **createCustomEmojiRequest**
> CustomEmojiRequest createCustomEmojiRequest(newCustomEmojiRequest, idempotencyKey)

Request a custom emoji or a new image of one

Requests a new local custom emoji (&#x60;create&#x60;) or a new image of a local emoji that the user made (&#x60;image&#x60;), which a holder of &#x60;manage_custom_emojis&#x60; approves or rejects. Until then, the request is no emoji: nothing shows its image. A pending &#x60;create&#x60; request reserves its shortcode. The image follows the rules of &#x60;POST /admin/custom_emojis&#x60;. A shortcode or an image that breaks the rules, a shortcode that a local emoji has or a pending request reserves, a &#x60;create&#x60; request once the approved and pending &#x60;create&#x60; requests that the user submitted this month (UTC) are as many as their monthly limit (&#x60;limit_reached&#x60;), and a second pending &#x60;image&#x60; request for an emoji (&#x60;request_pending&#x60;) are a 422 &#x60;/api/v1/problems/invalid-custom-emoji&#x60; whose &#x60;reason&#x60; says which. A &#x60;create&#x60; request without &#x60;shortcode&#x60; and an &#x60;image&#x60; request without &#x60;emoji_id&#x60; are a 422 naming the property, and an &#x60;image&#x60; request for an emoji that is not one the user made is a 404. The request is recorded in the audit log and notified to the holders of &#x60;manage_custom_emojis&#x60;. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val newCustomEmojiRequest : NewCustomEmojiRequest =  // NewCustomEmojiRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CustomEmojiRequest = apiInstance.createCustomEmojiRequest(newCustomEmojiRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#createCustomEmojiRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#createCustomEmojiRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **newCustomEmojiRequest** | [**NewCustomEmojiRequest**](NewCustomEmojiRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CustomEmojiRequest**](CustomEmojiRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteOwnCustomEmoji"></a>
# **deleteOwnCustomEmoji**
> deleteOwnCustomEmoji(id, idempotencyKey)

Delete a custom emoji that the user made

Deletes a local emoji that the user made for good, as &#x60;DELETE /admin/custom_emojis/{id}&#x60; does; the monthly limit counts requests, so the deletion gives no place back. A pending request for a new image of it lapses. The deletion is recorded in the audit log. An emoji that is not one the user made is a 404. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteOwnCustomEmoji(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#deleteOwnCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#deleteOwnCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
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

<a id="listCustomEmojiRequests"></a>
# **listCustomEmojiRequests**
> CustomEmojiRequestPage listCustomEmojiRequests(cursor, limit)

List the user&#39;s custom emoji requests

The user&#39;s requests for new custom emoji and new images of their own, newest first, in every state. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : CustomEmojiRequestPage = apiInstance.listCustomEmojiRequests(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#listCustomEmojiRequests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#listCustomEmojiRequests")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**CustomEmojiRequestPage**](CustomEmojiRequestPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listOwnCustomEmojis"></a>
# **listOwnCustomEmojis**
> OwnCustomEmojiPage listOwnCustomEmojis(cursor, limit)

List the custom emoji that the user made

The local custom emoji that the user made, newest first, with what the user used of their monthly limit: the approved and pending requests to create an emoji that they submitted this month (UTC), the limit, which a supporter plan sets and which is absent for no limit, and when the count resets. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : OwnCustomEmojiPage = apiInstance.listOwnCustomEmojis(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#listOwnCustomEmojis")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#listOwnCustomEmojis")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**OwnCustomEmojiPage**](OwnCustomEmojiPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateOwnCustomEmoji"></a>
# **updateOwnCustomEmoji**
> OwnCustomEmoji updateOwnCustomEmoji(id, updateOwnCustomEmojiRequest, idempotencyKey)

Change a custom emoji that the user made

Changes the aliases, the category, and the place in the picker of a local emoji that the user made, and keeps the rest; a new image is a request (&#x60;POST /custom_emoji_requests&#x60;). The rules of &#x60;PATCH /admin/custom_emojis/{id}&#x60; apply, and the change is recorded in the audit log. An emoji that is not one the user made is a 404. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val updateOwnCustomEmojiRequest : UpdateOwnCustomEmojiRequest =  // UpdateOwnCustomEmojiRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : OwnCustomEmoji = apiInstance.updateOwnCustomEmoji(id, updateOwnCustomEmojiRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#updateOwnCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#updateOwnCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **updateOwnCustomEmojiRequest** | [**UpdateOwnCustomEmojiRequest**](UpdateOwnCustomEmojiRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**OwnCustomEmoji**](OwnCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="withdrawCustomEmojiRequest"></a>
# **withdrawCustomEmojiRequest**
> CustomEmojiRequest withdrawCustomEmojiRequest(id, idempotencyKey)

Withdraw a custom emoji request

Withdraws a pending request of the user, which frees its shortcode; the withdrawal is recorded in the audit log. A request that is not pending is a 409 &#x60;/api/v1/problems/custom-emoji-request-conflict&#x60; (&#x60;not_pending&#x60;), and an unknown request or one of another user a 404. Needs the &#x60;create_custom_emojis&#x60; permission (a 403 &#x60;/api/v1/problems/missing-permission&#x60; otherwise). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = CustomEmojisApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji request.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CustomEmojiRequest = apiInstance.withdrawCustomEmojiRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CustomEmojisApi#withdrawCustomEmojiRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CustomEmojisApi#withdrawCustomEmojiRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji request. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CustomEmojiRequest**](CustomEmojiRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

