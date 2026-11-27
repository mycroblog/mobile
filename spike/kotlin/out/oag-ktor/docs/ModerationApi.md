# ModerationApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**appealModerationAction**](ModerationApi.md#appealModerationAction) | **POST** /moderation/actions/{id}/appeal | Appeal a moderation action on the authenticated user&#39;s account |
| [**listOwnModerationActions**](ModerationApi.md#listOwnModerationActions) | **GET** /moderation/actions | List the moderation actions on the authenticated user&#39;s account |


<a id="appealModerationAction"></a>
# **appealModerationAction**
> ModerationAction appealModerationAction(id, appealModerationActionRequest, idempotencyKey)

Appeal a moderation action on the authenticated user&#39;s account

Asks the moderators to reconsider an action on the user&#39;s own account, once per action and until its &#x60;appeal_deadline&#x60; (the instance&#39;s appeal window from the action, 180 days by default; for a &#x60;ban&#x60;, also no later than the deletion of the account 30 days after it). The moderators get an &#x60;admin_appeal&#x60; notification, and the user an &#x60;appeal_decided&#x60; one when they decide. A second appeal of the action is a 422 &#x60;/api/v1/problems/already-appealed&#x60;, an appeal after the deadline a 422 &#x60;/api/v1/problems/appeal-window-closed&#x60;, and an appeal of a &#x60;ban&#x60; whose account is deleted a 422 &#x60;/api/v1/problems/not-appealable&#x60;. A &#x60;text&#x60; that is empty, too long, or contains U+0000 is a 422 naming &#x60;text&#x60;. An action that is not on the user&#39;s account is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the action.
val appealModerationActionRequest : AppealModerationActionRequest =  // AppealModerationActionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ModerationAction = apiInstance.appealModerationAction(id, appealModerationActionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ModerationApi#appealModerationAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ModerationApi#appealModerationAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the action. | |
| **appealModerationActionRequest** | [**AppealModerationActionRequest**](AppealModerationActionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ModerationAction**](ModerationAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listOwnModerationActions"></a>
# **listOwnModerationActions**
> ModerationActionPage listOwnModerationActions(active, cursor, limit)

List the moderation actions on the authenticated user&#39;s account

The actions that moderators took on the user&#39;s own account, newest first, lifted and expired ones included unless &#x60;active&#x60; is set, also those that the user was not notified of. Each carries what the moderators chose to tell the user; never their private note, the report the action follows from, or who filed it. &#x60;kind_label&#x60; and &#x60;reason_category_label&#x60; are in the user&#39;s language. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ModerationApi()
val active : kotlin.Boolean = true // kotlin.Boolean | Keep only the actions in effect now, neither lifted nor expired.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : ModerationActionPage = apiInstance.listOwnModerationActions(active, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ModerationApi#listOwnModerationActions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ModerationApi#listOwnModerationActions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **active** | **kotlin.Boolean**| Keep only the actions in effect now, neither lifted nor expired. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**ModerationActionPage**](ModerationActionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

