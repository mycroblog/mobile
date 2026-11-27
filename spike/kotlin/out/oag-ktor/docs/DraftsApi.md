# DraftsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createDraft**](DraftsApi.md#createDraft) | **POST** /drafts | Save a new draft |
| [**deleteDraft**](DraftsApi.md#deleteDraft) | **DELETE** /drafts/{id} | Delete a draft |
| [**getDraft**](DraftsApi.md#getDraft) | **GET** /drafts/{id} | Get a draft |
| [**listDrafts**](DraftsApi.md#listDrafts) | **GET** /drafts | List the user&#39;s drafts, newest first |
| [**publishDraft**](DraftsApi.md#publishDraft) | **POST** /drafts/{id}/publish | Publish a draft now |
| [**scheduleDraft**](DraftsApi.md#scheduleDraft) | **PUT** /drafts/{id}/schedule | Schedule a draft, or move its schedule |
| [**unscheduleDraft**](DraftsApi.md#unscheduleDraft) | **DELETE** /drafts/{id}/schedule | Cancel the schedule of a draft |
| [**updateDraft**](DraftsApi.md#updateDraft) | **PUT** /drafts/{id} | Replace the content of a draft |


<a id="createDraft"></a>
# **createDraft**
> Draft createDraft(draftRequest, idempotencyKey)

Save a new draft

Saves a new draft of the user (201). A user with &#x60;max_drafts&#x60; drafts that are not scheduled (&#x60;limits&#x60; of &#x60;GET /instance&#x60;) gets a 422 that names the limit. Media must be the user&#39;s own uploads (422 otherwise), processed or not. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val draftRequest : DraftRequest =  // DraftRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Draft = apiInstance.createDraft(draftRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#createDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#createDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **draftRequest** | [**DraftRequest**](DraftRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Draft**](Draft.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteDraft"></a>
# **deleteDraft**
> deleteDraft(id, idempotencyKey)

Delete a draft

Deletes a draft with its schedule (204). The media it attaches, when no post attaches them, are deleted with the next cleanup of unattached uploads. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteDraft(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#deleteDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#deleteDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |
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

<a id="getDraft"></a>
# **getDraft**
> Draft getDraft(id)

Get a draft

A draft of the user, with its &#x60;ETag&#x60; for the next save. Another user&#39;s draft is a 404, and so is a &#x60;direct&#x60; draft without &#x60;read:direct&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
try {
    val result : Draft = apiInstance.getDraft(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#getDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#getDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |

### Return type

[**Draft**](Draft.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDrafts"></a>
# **listDrafts**
> DraftPage listDrafts(cursor, limit, scheduled)

List the user&#39;s drafts, newest first

The drafts the user saved on the server, scheduled or not. &#x60;scheduled&#x60; keeps the scheduled ones (&#x60;true&#x60;) or the others (&#x60;false&#x60;). Without &#x60;read:direct&#x60; the &#x60;direct&#x60; drafts are left out, and a page may be shorter than &#x60;limit&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
val scheduled : kotlin.Boolean = true // kotlin.Boolean | Whether to list only the scheduled drafts (`true`) or only the others (`false`).
try {
    val result : DraftPage = apiInstance.listDrafts(cursor, limit, scheduled)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#listDrafts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#listDrafts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |
| **scheduled** | **kotlin.Boolean**| Whether to list only the scheduled drafts (&#x60;true&#x60;) or only the others (&#x60;false&#x60;). | [optional] |

### Return type

[**DraftPage**](DraftPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="publishDraft"></a>
# **publishDraft**
> Status publishDraft(id, idempotencyKey)

Publish a draft now

Publishes the draft as a new post (201), with the checks and the answers of &#x60;createStatus&#x60;, and deletes the draft with its schedule; the draft of an article (&#x60;article&#x60;) is published with those of &#x60;createArticle&#x60;, and the status has its &#x60;content&#x60;. A draft that cannot be published stays as it was. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.publishDraft(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#publishDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#publishDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="scheduleDraft"></a>
# **scheduleDraft**
> Draft scheduleDraft(id, draftScheduleRequest, idempotencyKey)

Schedule a draft, or move its schedule

Schedules the draft to be published at &#x60;publish_at&#x60;, replacing its schedule if it has one. The draft is checked now as a new post (422 with the problem of &#x60;createStatus&#x60;; media still being processed pass), and again when it is published, by the user&#39;s state and the posts it replies to and quotes at that time: a post that cannot be published then leaves the draft, ends the schedule as &#x60;failed&#x60; (&#x60;last_schedule&#x60; of the draft), and notifies the user (&#x60;scheduled_status_failed&#x60;). A time sooner than &#x60;scheduled_post_min_lead_minutes&#x60; or further than &#x60;scheduled_post_max_lead_days&#x60;, and a user at &#x60;max_scheduled_posts&#x60; or at &#x60;max_scheduled_posts_per_day&#x60; for the UTC day of the time, get a 422 that names the limit. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
val draftScheduleRequest : DraftScheduleRequest =  // DraftScheduleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Draft = apiInstance.scheduleDraft(id, draftScheduleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#scheduleDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#scheduleDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |
| **draftScheduleRequest** | [**DraftScheduleRequest**](DraftScheduleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Draft**](Draft.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="unscheduleDraft"></a>
# **unscheduleDraft**
> Draft unscheduleDraft(id, idempotencyKey)

Cancel the schedule of a draft

The draft stays, without a schedule. A draft that is not scheduled is a 409.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Draft = apiInstance.unscheduleDraft(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#unscheduleDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#unscheduleDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Draft**](Draft.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateDraft"></a>
# **updateDraft**
> Draft updateDraft(id, draftRequest, ifMatch, idempotencyKey)

Replace the content of a draft

Replaces the whole content of a draft. &#x60;If-Match&#x60; must be the &#x60;ETag&#x60; of the draft as the client last read it: a draft that another save changed since is a 412, and a request without &#x60;If-Match&#x60; a 428. A scheduled draft stays scheduled and is published with the content it has then. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = DraftsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the draft.
val draftRequest : DraftRequest =  // DraftRequest | 
val ifMatch : kotlin.String = ifMatch_example // kotlin.String | The `ETag` of the draft as the client last read it. When another save changed the draft since, the answer is a 412 and nothing is saved, so that a save on one device never silently overwrites one made on another; read the draft again and merge. Without it, the save is a 428. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Draft = apiInstance.updateDraft(id, draftRequest, ifMatch, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DraftsApi#updateDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DraftsApi#updateDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the draft. | |
| **draftRequest** | [**DraftRequest**](DraftRequest.md)|  | |
| **ifMatch** | **kotlin.String**| The &#x60;ETag&#x60; of the draft as the client last read it. When another save changed the draft since, the answer is a 412 and nothing is saved, so that a save on one device never silently overwrites one made on another; read the draft again and merge. Without it, the save is a 428.  | [optional] |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Draft**](Draft.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

