# ConversationsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getConversation**](ConversationsApi.md#getConversation) | **GET** /conversations/{id} | Get a conversation of the direct messages |
| [**hideConversation**](ConversationsApi.md#hideConversation) | **POST** /conversations/{id}/hide | Hide a conversation |
| [**listConversations**](ConversationsApi.md#listConversations) | **GET** /conversations | List the direct messages by conversation |
| [**markConversationRead**](ConversationsApi.md#markConversationRead) | **POST** /conversations/{id}/read | Mark a conversation read |


<a id="getConversation"></a>
# **getConversation**
> Conversation getConversation(id)

Get a conversation of the direct messages

The conversation as &#x60;listConversations&#x60; shows it, even when the user hid it. A conversation in which the user sent or received no direct post, or none that the user may see, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ConversationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into. 
try {
    val result : Conversation = apiInstance.getConversation(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ConversationsApi#getConversation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ConversationsApi#getConversation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into.  | |

### Return type

[**Conversation**](Conversation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="hideConversation"></a>
# **hideConversation**
> hideConversation(id, idempotencyKey)

Hide a conversation

Takes the conversation out of &#x60;listConversations&#x60; until a newer direct post arrives in it, on every device of the user. Its posts stay, and so do they for the other accounts. Hiding it again changes nothing. A conversation in which the user sent or received no direct post is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ConversationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.hideConversation(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling ConversationsApi#hideConversation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ConversationsApi#hideConversation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into.  | |
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

<a id="listConversations"></a>
# **listConversations**
> ConversationPage listConversations(cursor, limit)

List the direct messages by conversation

Every conversation with a &#x60;direct&#x60; post that the user sent or received, whether or not the user follows its author, the one with the newest such post first. Each shows its newest direct post that the user may see, as the home timeline would show it: the posts of accounts that the user blocks, is blocked by, or mutes, and of suspended accounts and domains, are skipped for the one before, and a conversation with none left is left out. The user&#39;s timeline languages do not apply. A conversation that the user hid (&#x60;hideConversation&#x60;) is left out until a newer direct post arrives in it.  A page may hold fewer conversations than &#x60;limit&#x60; while &#x60;next_cursor&#x60; is still given, when some have no post left that the user may see. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ConversationsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : ConversationPage = apiInstance.listConversations(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ConversationsApi#listConversations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ConversationsApi#listConversations")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**ConversationPage**](ConversationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="markConversationRead"></a>
# **markConversationRead**
> Conversation markConversationRead(id, idempotencyKey)

Mark a conversation read

Marks the conversation read up to its newest direct post, on every device of the user: &#x60;unread&#x60; is false until a newer direct post by another account arrives. The user&#39;s open streams receive the conversation again (&#x60;conversation&#x60;). Marking it again changes nothing. A conversation in which the user sent or received no direct post is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ConversationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Conversation = apiInstance.markConversationRead(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ConversationsApi#markConversationRead")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ConversationsApi#markConversationRead")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the conversation. The ID of a conversation that was merged into another, once a reply showed both to be one thread, names the conversation it was merged into.  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Conversation**](Conversation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

