# StatusesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**acceptInteractionRequest**](StatusesApi.md#acceptInteractionRequest) | **POST** /interaction_requests/{id}/accept | Approve an interaction request |
| [**appealHookDecision**](StatusesApi.md#appealHookDecision) | **POST** /hook_decisions/{id}/appeal | Appeal a post that the automated review refused |
| [**bookmarkStatus**](StatusesApi.md#bookmarkStatus) | **POST** /statuses/{id}/bookmark | Bookmark a status |
| [**boostStatus**](StatusesApi.md#boostStatus) | **POST** /statuses/{id}/boost | Boost a status |
| [**createArticle**](StatusesApi.md#createArticle) | **POST** /articles | Post an article |
| [**createStatus**](StatusesApi.md#createStatus) | **POST** /statuses | Post a status |
| [**deleteStatus**](StatusesApi.md#deleteStatus) | **DELETE** /statuses/{id} | Delete the authenticated user&#39;s status |
| [**editArticle**](StatusesApi.md#editArticle) | **PUT** /articles/{id} | Edit the authenticated user&#39;s article |
| [**editStatus**](StatusesApi.md#editStatus) | **PUT** /statuses/{id} | Edit the authenticated user&#39;s status |
| [**favouriteStatus**](StatusesApi.md#favouriteStatus) | **POST** /statuses/{id}/favourite | Favourite a status |
| [**followTag**](StatusesApi.md#followTag) | **POST** /tags/{hashtag}/follow | Follow a hashtag |
| [**getOwnHookAppeal**](StatusesApi.md#getOwnHookAppeal) | **GET** /hook_decisions/{id}/appeal | Show the user&#39;s appeal of a post that the automated review refused |
| [**getStatus**](StatusesApi.md#getStatus) | **GET** /statuses/{id} | Get a status |
| [**getStatusContext**](StatusesApi.md#getStatusContext) | **GET** /statuses/{id}/context | Get the thread around a status |
| [**getStatusHistory**](StatusesApi.md#getStatusHistory) | **GET** /statuses/{id}/history | Get the edit history of a status |
| [**getStatusSource**](StatusesApi.md#getStatusSource) | **GET** /statuses/{id}/source | Get the source of the authenticated user&#39;s status, for editing it |
| [**getTag**](StatusesApi.md#getTag) | **GET** /tags/{hashtag} | Get a hashtag |
| [**listBookmarks**](StatusesApi.md#listBookmarks) | **GET** /bookmarks | List the posts the authenticated user bookmarked, newest bookmark first |
| [**listFavourites**](StatusesApi.md#listFavourites) | **GET** /favourites | List the posts the authenticated user favourited, newest favourite first |
| [**listFollowedTags**](StatusesApi.md#listFollowedTags) | **GET** /followed_tags | List the hashtags the user follows |
| [**listInteractionRequests**](StatusesApi.md#listInteractionRequests) | **GET** /interaction_requests | List the interaction requests to the authenticated user |
| [**listStatusQuotes**](StatusesApi.md#listStatusQuotes) | **GET** /statuses/{id}/quotes | List the statuses that quote the authenticated user&#39;s status |
| [**listStatusReactions**](StatusesApi.md#listStatusReactions) | **GET** /statuses/{id}/reactions | List the emoji reactions to a status |
| [**lookupStatus**](StatusesApi.md#lookupStatus) | **GET** /statuses/lookup | Look up or resolve a status by its URL |
| [**muteStatusThread**](StatusesApi.md#muteStatusThread) | **POST** /statuses/{id}/mute | Mute the thread of a status |
| [**pinStatus**](StatusesApi.md#pinStatus) | **POST** /statuses/{id}/pin | Pin the authenticated user&#39;s status to the profile |
| [**previewArticle**](StatusesApi.md#previewArticle) | **POST** /articles/preview | Preview the body of an article |
| [**reactToStatus**](StatusesApi.md#reactToStatus) | **PUT** /statuses/{id}/reaction | React to a status with an emoji |
| [**rejectInteractionRequest**](StatusesApi.md#rejectInteractionRequest) | **POST** /interaction_requests/{id}/reject | Refuse an interaction request |
| [**revokeStatusQuoteApproval**](StatusesApi.md#revokeStatusQuoteApproval) | **DELETE** /statuses/{id}/quotes/{quote_id}/approval | Revoke the approval of a quote of the authenticated user&#39;s status |
| [**searchStatuses**](StatusesApi.md#searchStatuses) | **GET** /statuses/search | Search the text of posts |
| [**searchTags**](StatusesApi.md#searchTags) | **GET** /tags/search | Search hashtags by prefix |
| [**setStatusInteractionPolicy**](StatusesApi.md#setStatusInteractionPolicy) | **PUT** /statuses/{id}/interaction_policy | Change who may reply to, boost, and quote the authenticated user&#39;s status |
| [**translateStatus**](StatusesApi.md#translateStatus) | **POST** /statuses/{id}/translation | Translate a post into a language |
| [**unbookmarkStatus**](StatusesApi.md#unbookmarkStatus) | **DELETE** /statuses/{id}/bookmark | Remove the bookmark of a status |
| [**unboostStatus**](StatusesApi.md#unboostStatus) | **DELETE** /statuses/{id}/boost | Retract the boost of a status |
| [**unfavouriteStatus**](StatusesApi.md#unfavouriteStatus) | **DELETE** /statuses/{id}/favourite | Remove the favourite of a status |
| [**unfollowTag**](StatusesApi.md#unfollowTag) | **DELETE** /tags/{hashtag}/follow | Unfollow a hashtag |
| [**unmuteStatusThread**](StatusesApi.md#unmuteStatusThread) | **DELETE** /statuses/{id}/mute | Unmute the thread of a status |
| [**unpinStatus**](StatusesApi.md#unpinStatus) | **DELETE** /statuses/{id}/pin | Unpin a status |
| [**unreactToStatus**](StatusesApi.md#unreactToStatus) | **DELETE** /statuses/{id}/reaction | Take back the emoji reaction to a status |
| [**voteInPoll**](StatusesApi.md#voteInPoll) | **POST** /statuses/{id}/poll/votes | Vote in the poll of a status |


<a id="acceptInteractionRequest"></a>
# **acceptInteractionRequest**
> InteractionRequest acceptInteractionRequest(id, idempotencyKey)

Approve an interaction request

Approves the reply, boost, or quote: a reply joins the thread of the status, a boost is counted, and a quote shows the status. The account is told, and its server gets the approval (an &#x60;Accept&#x60; with the authorization). A request that is answered already is a 409; one of another user&#39;s status, or of a deleted status, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the interaction request.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : InteractionRequest = apiInstance.acceptInteractionRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#acceptInteractionRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#acceptInteractionRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the interaction request. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**InteractionRequest**](InteractionRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="appealHookDecision"></a>
# **appealHookDecision**
> OwnHookAppeal appealHookDecision(id, appealHookDecisionRequest, idempotencyKey)

Appeal a post that the automated review refused

Asks the moderators to let a refused post through (the 422 &#x60;/api/v1/problems/status-rejected&#x60; of &#x60;POST /statuses&#x60;), once per decision and until its &#x60;appeal_deadline&#x60; (the instance&#39;s appeal window from the refusal, 180 days by default). People review it; the refused post is not stored, so the appeal sends its text. When a moderator overturns the decision, the user&#39;s posts are no longer reviewed and the user posts again. A second appeal is a 422 &#x60;/api/v1/problems/already-appealed&#x60;, an appeal after the deadline a 422 &#x60;/api/v1/problems/appeal-window-closed&#x60;, and an appeal of a decision that is not such a refusal a 422 &#x60;/api/v1/problems/not-appealable&#x60;. A &#x60;status_text&#x60; that is blank or too long, or a field that contains U+0000, is a 422 naming it. A decision that is not about the user&#39;s post is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the decision.
val appealHookDecisionRequest : AppealHookDecisionRequest =  // AppealHookDecisionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : OwnHookAppeal = apiInstance.appealHookDecision(id, appealHookDecisionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#appealHookDecision")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#appealHookDecision")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the decision. | |
| **appealHookDecisionRequest** | [**AppealHookDecisionRequest**](AppealHookDecisionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**OwnHookAppeal**](OwnHookAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="bookmarkStatus"></a>
# **bookmarkStatus**
> Status bookmarkStatus(id, idempotencyKey)

Bookmark a status

Bookmarking a status that is already bookmarked changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.bookmarkStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#bookmarkStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#bookmarkStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="boostStatus"></a>
# **boostStatus**
> Status boostStatus(id, idempotencyKey)

Boost a status

Boosting a status that is already boosted changes nothing. A status whose visibility does not allow boosting is a 422. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.boostStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#boostStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#boostStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="createArticle"></a>
# **createArticle**
> Status createArticle(createArticleRequest, idempotencyKey)

Post an article

Posts an article as the authenticated user (201): a long post with a title, a summary, and a cover, whose body is Markdown. The server renders the body once, now, and the article is a &#x60;Status&#x60; with &#x60;article&#x60; from then on: its visibility, edits, interactions, reports, and deletion (&#x60;deleteStatus&#x60;) are those of a post. The answer carries the body in &#x60;content&#x60;, as &#x60;getStatus&#x60; does.  The body is CommonMark with the tables, strikethrough, and autolinks of GitHub Flavored Markdown; raw HTML is not rendered, a line break in a paragraph is kept, and &#x60;#&#x60; is the second level of heading (the title is the first). An image whose destination is &#x60;media:&#x60; followed by the ID of one of the user&#39;s own uploads that finished processing (&#x60;![alt text](media:ID)&#x60;) shows that image; any other image is a link with its alt text, so that readers never load an image from elsewhere. Mentions link to the accounts but notify nobody, and &#x60;#name&#x60; in the body stays text: the hashtags are those of &#x60;tags&#x60;.  A body whose HTML is over &#x60;Instance.limits.articles.max_bytes&#x60; bytes, or with more images than &#x60;Instance.limits.articles.max_images&#x60;, is a 422; &#x60;previewArticle&#x60; tells both before posting. An article is never &#x60;direct&#x60; (422). While the instance lets only some users write articles (&#x60;Instance.article_authoring&#x60; is &#x60;permission&#x60;), a user without the permission &#x60;create_articles&#x60; gets a 403; that user may still edit their earlier articles. Retrying with the same &#x60;Idempotency-Key&#x60; returns the article posted with it, as &#x60;createStatus&#x60; does. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. When the server&#39;s automated review refuses the article, nothing is posted and the answer is a 422 &#x60;/api/v1/problems/status-rejected&#x60;, as for a post. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val createArticleRequest : CreateArticleRequest =  // CreateArticleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.createArticle(createArticleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#createArticle")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#createArticle")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createArticleRequest** | [**CreateArticleRequest**](CreateArticleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createStatus"></a>
# **createStatus**
> Status createStatus(createStatusRequest, idempotencyKey)

Post a status

Posts a status as the authenticated user (201). Retrying with the same &#x60;Idempotency-Key&#x60; returns the status posted with it, before the post rate limit and the other checks of a new post (a 409 when that status is deleted). Attached media must be the user&#39;s own uploads that finished processing (422 otherwise). A status with a &#x60;poll&#x60; attaches no media, and a poll outside the limits of &#x60;PollInput&#x60; is a 422. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. When the server&#39;s automated review refuses the post, nothing is posted and the answer is a 422 &#x60;/api/v1/problems/status-rejected&#x60; with fixed text and the decision the user may appeal. On a one-person server (&#x60;Instance.single_user&#x60;) the visibility &#x60;local&#x60; is a 422, and &#x60;CredentialAccount.posting_limits&#x60; leaves it out. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val createStatusRequest : CreateStatusRequest =  // CreateStatusRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.createStatus(createStatusRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#createStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#createStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createStatusRequest** | [**CreateStatusRequest**](CreateStatusRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteStatus"></a>
# **deleteStatus**
> deleteStatus(id, idempotencyKey)

Delete the authenticated user&#39;s status

Another account&#39;s status is a 403 when the user may see it and a 404 otherwise. A &#x60;direct&#x60; status is a 404 to a token without &#x60;read:direct&#x60;, the user&#39;s own included, and nothing is deleted. The author deletes a post of the board of an event here, the board read-only or not. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteStatus(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#deleteStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#deleteStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="editArticle"></a>
# **editArticle**
> Status editArticle(id, editArticleRequest, idempotencyKey)

Edit the authenticated user&#39;s article

Replaces the title, summary, cover, body, hashtags, language, and sensitivity of an article, checked and rendered as &#x60;createArticle&#x60; does; the previous revision stays in the edit history. The visibility never changes, and who may reply to, boost, and quote it changes with &#x60;setStatusInteractionPolicy&#x60;. A status that is a post is a 422 (it is edited with &#x60;editStatus&#x60;), and so is a body over the limits. When the server&#39;s automated review refuses the edit, the answer is a 422 &#x60;/api/v1/problems/status-rejected&#x60; with no decision to appeal, and the article stays as it was. Another account&#39;s status is a 403 when the user may see it and a 404 otherwise. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val editArticleRequest : EditArticleRequest =  // EditArticleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.editArticle(id, editArticleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#editArticle")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#editArticle")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **editArticleRequest** | [**EditArticleRequest**](EditArticleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="editStatus"></a>
# **editStatus**
> Status editStatus(id, editStatusRequest, idempotencyKey)

Edit the authenticated user&#39;s status

Replaces the text, content warning, sensitivity, language, and media of a status; the previous content stays in the edit history. The visibility and reply target never change. Attached media must be the user&#39;s own uploads that finished processing (422 otherwise). A status with a poll cannot be edited (422): its votes belong to its poll. An article is edited with &#x60;editArticle&#x60; (422 here). When the server&#39;s automated review refuses the edit, the answer is a 422 &#x60;/api/v1/problems/status-rejected&#x60; with fixed text and no decision to appeal, and the status stays as it was. Another account&#39;s status is a 403 when the user may see it and a 404 otherwise. A &#x60;direct&#x60; status is a 404 to a token without &#x60;read:direct&#x60;, the user&#39;s own included. A post of the board of an event is edited here while its author may post on the board: a 403 &#x60;/api/v1/problems/event-board-not-reader&#x60; or &#x60;/api/v1/problems/event-board-not-writer&#x60; otherwise, and a 409 &#x60;/api/v1/problems/event-board-closed&#x60; once the board is read-only. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val editStatusRequest : EditStatusRequest =  // EditStatusRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.editStatus(id, editStatusRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#editStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#editStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **editStatusRequest** | [**EditStatusRequest**](EditStatusRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="favouriteStatus"></a>
# **favouriteStatus**
> Status favouriteStatus(id, idempotencyKey)

Favourite a status

Favouriting a status that is already favourited changes nothing. A user has one favourite or one emoji reaction on a status, so favouriting takes back the reaction. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.favouriteStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#favouriteStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#favouriteStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="followTag"></a>
# **followTag**
> Hashtag followTag(hashtag, idempotencyKey)

Follow a hashtag

The home timeline then also shows the &#x60;public&#x60; and &#x60;local&#x60; posts with the hashtag that the server has, from accounts the user does not follow, as the hashtag&#39;s timeline shows them: without boosts, and without the posts of accounts and servers kept from public timelines or that the user blocks or mutes. Such a post carries &#x60;followed_tags&#x60;. Following a hashtag that the user follows changes nothing, and a hashtag that no post has used yet can be followed. A user follows at most 100 hashtags; one more is a 422 &#x60;/api/v1/problems/too-many-followed-tags&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val hashtag : kotlin.String = hashtag_example // kotlin.String | The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the search of hashtags. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Hashtag = apiInstance.followTag(hashtag, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#followTag")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#followTag")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hashtag** | **kotlin.String**| The hashtag, with or without a leading &#x60;#&#x60; (&#x60;%23&#x60;), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming &#x60;hashtag&#x60;. The hashtag &#x60;search&#x60; is written &#x60;%23search&#x60;, since &#x60;/tags/search&#x60; is the search of hashtags.  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Hashtag**](Hashtag.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getOwnHookAppeal"></a>
# **getOwnHookAppeal**
> OwnHookAppeal getOwnHookAppeal(id)

Show the user&#39;s appeal of a post that the automated review refused

Where the user&#39;s appeal of a refused post stands: &#x60;pending&#x60; until a moderator reviews it, then &#x60;upheld&#x60; (the refusal stays) or &#x60;overturned&#x60; (the user&#39;s posts are no longer reviewed, and the user posts again). A &#x60;hook_appeal_decided&#x60; notification tells the user when it is reviewed. A decision that the user has not appealed, or that is not about the user&#39;s post, is a 404, as is an appeal no longer kept. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the decision, `hook_decision_id` of the refusal.
try {
    val result : OwnHookAppeal = apiInstance.getOwnHookAppeal(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getOwnHookAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getOwnHookAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the decision, &#x60;hook_decision_id&#x60; of the refusal. | |

### Return type

[**OwnHookAppeal**](OwnHookAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getStatus"></a>
# **getStatus**
> Status getStatus(id)

Get a status

A status that the viewer may not see is a 404, the same as a missing one. An article comes with its body in &#x60;content&#x60;. A post of the board of an event (&#x60;listEventBoard&#x60;) is seen only by the users who read the board. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
try {
    val result : Status = apiInstance.getStatus(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |

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

<a id="getStatusContext"></a>
# **getStatusContext**
> StatusContext getStatusContext(id)

Get the thread around a status

The ancestors and descendants of a status that the viewer may see. A status that the viewer may not see is a 404, the same as a missing one. For a remote status, any request, an anonymous one included, may start fetching the rest of its thread from other servers; a thread is fetched once at a time, 20 levels deep and at most 100 documents, so the answer may grow on a later request. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
try {
    val result : StatusContext = apiInstance.getStatusContext(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getStatusContext")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getStatusContext")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |

### Return type

[**StatusContext**](StatusContext.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getStatusHistory"></a>
# **getStatusHistory**
> StatusHistory getStatusHistory(id)

Get the edit history of a status

Every revision of a status, oldest first; the last one is the current content. A status that the viewer may not see is a 404, the same as a missing one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
try {
    val result : StatusHistory = apiInstance.getStatusHistory(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getStatusHistory")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getStatusHistory")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |

### Return type

[**StatusHistory**](StatusHistory.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getStatusSource"></a>
# **getStatusSource**
> StatusSource getStatusSource(id)

Get the source of the authenticated user&#39;s status, for editing it

Another account&#39;s status is a 404, the same as a missing one, and so is a &#x60;direct&#x60; status without &#x60;read:direct&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
try {
    val result : StatusSource = apiInstance.getStatusSource(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getStatusSource")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getStatusSource")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |

### Return type

[**StatusSource**](StatusSource.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getTag"></a>
# **getTag**
> Hashtag getTag(hashtag)

Get a hashtag

The hashtag and whether the user follows it. A hashtag that no post has used yet is answered too, so that it can be followed. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val hashtag : kotlin.String = hashtag_example // kotlin.String | The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the search of hashtags. 
try {
    val result : Hashtag = apiInstance.getTag(hashtag)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#getTag")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#getTag")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hashtag** | **kotlin.String**| The hashtag, with or without a leading &#x60;#&#x60; (&#x60;%23&#x60;), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming &#x60;hashtag&#x60;. The hashtag &#x60;search&#x60; is written &#x60;%23search&#x60;, since &#x60;/tags/search&#x60; is the search of hashtags.  | |

### Return type

[**Hashtag**](Hashtag.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listBookmarks"></a>
# **listBookmarks**
> StatusPage listBookmarks(cursor, limit)

List the posts the authenticated user bookmarked, newest bookmark first

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.listBookmarks(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listBookmarks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listBookmarks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**StatusPage**](StatusPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listFavourites"></a>
# **listFavourites**
> StatusPage listFavourites(cursor, limit)

List the posts the authenticated user favourited, newest favourite first

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.listFavourites(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listFavourites")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listFavourites")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**StatusPage**](StatusPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listFollowedTags"></a>
# **listFollowedTags**
> FollowedHashtagCollection listFollowedTags()

List the hashtags the user follows

Every hashtag that the user follows (at most 100), the latest followed first.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
try {
    val result : FollowedHashtagCollection = apiInstance.listFollowedTags()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listFollowedTags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listFollowedTags")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**FollowedHashtagCollection**](FollowedHashtagCollection.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInteractionRequests"></a>
# **listInteractionRequests**
> InteractionRequestPage listInteractionRequests(kind, cursor, limit)

List the interaction requests to the authenticated user

The replies, boosts, and quotes of the user&#39;s statuses that wait for the user&#39;s approval, newest first, of one kind when &#x60;kind&#x60; is given. A request whose status the user deleted, or whose account the user may not see, is left out, so a page may hold fewer than &#x60;limit&#x60; requests before the last. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val kind : InteractionKind =  // InteractionKind | Only the requests of this kind.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : InteractionRequestPage = apiInstance.listInteractionRequests(kind, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listInteractionRequests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listInteractionRequests")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**InteractionKind**](.md)| Only the requests of this kind. | [optional] [enum: reply, announce, quote] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**InteractionRequestPage**](InteractionRequestPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listStatusQuotes"></a>
# **listStatusQuotes**
> StatusPage listStatusQuotes(id, cursor, limit)

List the statuses that quote the authenticated user&#39;s status

The statuses that quote a status of the user, newest first, each with its &#x60;quote&#x60; and its state. Statuses the user may not see, and quotes taken away, are left out, so a page may hold fewer than &#x60;limit&#x60; statuses before the last. Another account&#39;s status is a 404, and so is the user&#39;s own &#x60;direct&#x60; status without &#x60;read:direct&#x60;, which also leaves out the &#x60;direct&#x60; statuses that quote one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.listStatusQuotes(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listStatusQuotes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listStatusQuotes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**StatusPage**](StatusPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listStatusReactions"></a>
# **listStatusReactions**
> StatusReactionPage listStatusReactions(id, emoji, cursor, limit)

List the emoji reactions to a status

The emoji reactions to a status that the viewer may see, newest first, each with the account that reacted, only those with one emoji when &#x60;emoji&#x60; is given. Accounts that block the viewer, are deactivated, or are deleted are left out, so a page may hold fewer than &#x60;limit&#x60; reactions before the last. A status that the viewer may not see is a 404, the same as a missing one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val emoji : kotlin.String = emoji_example // kotlin.String | The `name` of an emoji (`ReactionEmoji`) to list the reactions with; an emoji that no reaction has gives an empty page. 
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusReactionPage = apiInstance.listStatusReactions(id, emoji, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#listStatusReactions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#listStatusReactions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **emoji** | **kotlin.String**| The &#x60;name&#x60; of an emoji (&#x60;ReactionEmoji&#x60;) to list the reactions with; an emoji that no reaction has gives an empty page.  | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**StatusReactionPage**](StatusReactionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="lookupStatus"></a>
# **lookupStatus**
> Status lookupStatus(uri)

Look up or resolve a status by its URL

Finds the status whose ActivityPub id is &#x60;uri&#x60;, among the statuses the instance knows, or else fetches it from its server, as a reply button of another server asks to (the page that &#x60;Create&#x60; intents of WebFinger open). A page that serves the post, such as its &#x60;url&#x60;, is followed to the post&#39;s id. The post&#39;s parent is fetched too, one level. Posts of this instance are found by their id only.  The answer is the status as &#x60;getStatus&#x60; gives it: a status that the viewer may not see is a 404, the same as a URL that leads to no post, or to an account.  Only signed-in users can look a status up (anonymous requests get the 401 of &#x60;/api/v1/problems/unauthenticated&#x60;). Fetching an unknown status shares the limit of &#x60;lookupAccount&#x60;&#39;s &#x60;resolve&#x60;, 30 fetches per account every 5 minutes (429 with &#x60;Retry-After&#x60;); finding a known one is not limited. While the instance takes nothing in from other servers (&#x60;Instance.federation.mode&#x60; &#x60;off&#x60; or &#x60;outbound&#x60;), only the known statuses are found: an unknown one is a 403 &#x60;/api/v1/problems/federation-restricted&#x60;, and nothing is fetched. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val uri : kotlin.String = uri_example // kotlin.String | The `https` URL of the post.
try {
    val result : Status = apiInstance.lookupStatus(uri)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#lookupStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#lookupStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **uri** | **kotlin.String**| The &#x60;https&#x60; URL of the post. | |

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

<a id="muteStatusThread"></a>
# **muteStatusThread**
> Status muteStatusThread(id, idempotencyKey)

Mute the thread of a status

Mutes the thread (conversation) that the status belongs to: from then on, replies and mentions in it, boosts, favourites, reactions, and quotes of its posts, and new posts in it by accounts the user is notified of no longer notify the user, the user&#39;s own posts in the thread included. Notifications that came before stay, and the posts still appear in timelines. The mute has no expiry and follows the thread when it is joined with another. Muting a thread that is already muted changes nothing; the status&#39;s &#x60;muted&#x60; tells the state. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.muteStatusThread(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#muteStatusThread")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#muteStatusThread")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="pinStatus"></a>
# **pinStatus**
> Status pinStatus(id, idempotencyKey)

Pin the authenticated user&#39;s status to the profile

Pinning a status that is already pinned changes nothing. Another account&#39;s status is a 403, as for editing it, and a direct status or a pin beyond the limit a 422. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.pinStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#pinStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#pinStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="previewArticle"></a>
# **previewArticle**
> ArticlePreview previewArticle(articlePreviewRequest, idempotencyKey)

Preview the body of an article

Renders the Markdown body of an article of the authenticated user as &#x60;createArticle&#x60; would store it, without storing anything: the same HTML that the published article has, the summary it would get, and the size and images of the body with their limits. A body over the limits still renders, so that the editor can say by how much. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val articlePreviewRequest : ArticlePreviewRequest =  // ArticlePreviewRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ArticlePreview = apiInstance.previewArticle(articlePreviewRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#previewArticle")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#previewArticle")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **articlePreviewRequest** | [**ArticlePreviewRequest**](ArticlePreviewRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ArticlePreview**](ArticlePreview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="reactToStatus"></a>
# **reactToStatus**
> Status reactToStatus(id, reactToStatusRequest, idempotencyKey)

React to a status with an emoji

Sets the authenticated user&#39;s emoji reaction to a status: a Unicode emoji, or a custom emoji of this server that is enabled. A user has one favourite or one reaction on a status, so reacting takes back the favourite and the reaction with another emoji; reacting again with the same emoji changes nothing. A custom emoji of another server, or anything else that is not one emoji, is a 422. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val reactToStatusRequest : ReactToStatusRequest =  // ReactToStatusRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.reactToStatus(id, reactToStatusRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#reactToStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#reactToStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **reactToStatusRequest** | [**ReactToStatusRequest**](ReactToStatusRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="rejectInteractionRequest"></a>
# **rejectInteractionRequest**
> InteractionRequest rejectInteractionRequest(id, idempotencyKey)

Refuse an interaction request

Refuses the reply, boost, or quote: it stays out of the thread and the counts. The account is told, and its server gets a &#x60;Reject&#x60;. A request that is answered already is a 409; one of another user&#39;s status, or of a deleted status, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the interaction request.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : InteractionRequest = apiInstance.rejectInteractionRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#rejectInteractionRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#rejectInteractionRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the interaction request. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**InteractionRequest**](InteractionRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeStatusQuoteApproval"></a>
# **revokeStatusQuoteApproval**
> revokeStatusQuoteApproval(id, quoteId, idempotencyKey)

Revoke the approval of a quote of the authenticated user&#39;s status

Withdraws this server&#39;s approval of the quote: the quote shows the quoted status no more, and the quoting server is told. A quote without this server&#39;s approval (from software without approvals, or of the user&#39;s own status) is a 404, as is one revoked already. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val quoteId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status that quotes.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeStatusQuoteApproval(id, quoteId, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#revokeStatusQuoteApproval")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#revokeStatusQuoteApproval")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **quoteId** | **java.util.UUID**| The ID of the status that quotes. | |
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

<a id="searchStatuses"></a>
# **searchStatuses**
> StatusPage searchStatuses(q, cursor, limit)

Search the text of posts

The posts that contain every word of &#x60;q&#x60;, newest first, as &#x60;Status&#x60; objects like those of a timeline. It looks in the text, the content warning, and the alternative texts of the current version of a post. A word&#39;s matching, including in Japanese, depends on the search engine of the server, and there is no order by relevance. &#x60;q&#x60; is plain text: nothing in it is an operator.  Read &#x60;Instance.full_text_search&#x60; before offering this: it says whether the server has a search engine (&#x60;available&#x60;) and which visibilities the search covers (&#x60;visibilities&#x60;). Without one, the response is a 404 of the type &#x60;/api/v1/problems/search-unavailable&#x60;. When the engine fails, it is a 503 of the type &#x60;/api/v1/problems/search-failed&#x60;, which only this operation answers; try again later.  Only signed-in users can search, and only &#x60;public&#x60; and &#x60;local&#x60; posts of accounts with &#x60;indexable&#x60; turned on are found, within the visibilities the server covers. The results never include &#x60;unlisted&#x60;, &#x60;private&#x60;, or &#x60;direct&#x60; posts, even the viewer&#39;s own. Posts of accounts or domains that the viewer blocks, mutes, or is blocked by are left out.  A page can hold fewer posts than &#x60;limit&#x60;, or none at all, while more follow, because posts are left out after the search engine answered. Continue while the response has a &#x60;next_cursor&#x60;, and stop only when it has none. A cursor works only on the search it came from: changing &#x60;q&#x60; needs a new first page. A cursor the search engine did not issue is a 400.  The query is part of the URL, so a reverse proxy or CDN in front of the server may log it. The server itself writes it to no log or trace, and the response is &#x60;private, no-store&#x60;.  Past 30 searches in 5 minutes by one account, in addition to the limit of the API as a whole, the response is a 429 with &#x60;Retry-After&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val q : kotlin.String = q_example // kotlin.String | The words to find, 1 to 200 characters. Anything else is a 400.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.searchStatuses(q, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#searchStatuses")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#searchStatuses")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **q** | **kotlin.String**| The words to find, 1 to 200 characters. Anything else is a 400. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**StatusPage**](StatusPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="searchTags"></a>
# **searchTags**
> TagSuggestionList searchTags(q, limit)

Search hashtags by prefix

The hashtags whose name starts with &#x60;q&#x60;, for the autocomplete of hashtags in the compose form and the hashtag search. &#x60;q&#x60; and the names are compared under NFKC and lowercase.  Only the hashtags that a post of visibility &#x60;public&#x60;, &#x60;local&#x60;, or &#x60;unlisted&#x60; carries now are found: a hashtag of private and direct posts only, or one whose posts are all deleted or edited away, is left out. The results come in this order: the hashtag equal to &#x60;q&#x60;, then those carried by more posts, then the more recently used. There is no paging. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val q : kotlin.String = q_example // kotlin.String | The start of a hashtag: 1 to 100 characters after a leading `#`, which is ignored. Anything else is a 400. 
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of results of a prefix search for the autocomplete.
try {
    val result : TagSuggestionList = apiInstance.searchTags(q, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#searchTags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#searchTags")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **q** | **kotlin.String**| The start of a hashtag: 1 to 100 characters after a leading &#x60;#&#x60;, which is ignored. Anything else is a 400.  | |
| **limit** | **kotlin.Int**| The maximum number of results of a prefix search for the autocomplete. | [optional] [default to 8] |

### Return type

[**TagSuggestionList**](TagSuggestionList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="setStatusInteractionPolicy"></a>
# **setStatusInteractionPolicy**
> Status setStatusInteractionPolicy(id, interactionPolicyChange, idempotencyKey)

Change who may reply to, boost, and quote the authenticated user&#39;s status

Changes the &#x60;interaction_policy&#x60; of a status of the user for the kinds the request has; the others stay. Interactions approved before stay, and an approval of a quote is revoked on its own. Another account&#39;s status is a 403 when the user may see it and a 404 otherwise. A &#x60;direct&#x60; status is a 404 to a token without &#x60;read:direct&#x60;, the user&#39;s own included. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val interactionPolicyChange : InteractionPolicyChange =  // InteractionPolicyChange | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.setStatusInteractionPolicy(id, interactionPolicyChange, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#setStatusInteractionPolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#setStatusInteractionPolicy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **interactionPolicyChange** | [**InteractionPolicyChange**](InteractionPolicyChange.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="translateStatus"></a>
# **translateStatus**
> StatusTranslation translateStatus(id, statusTranslationRequest, idempotencyKey)

Translate a post into a language

Returns the current version of a post in &#x60;target_language&#x60;: its text, content warning, the alt text of its media, and its poll options. A post that the viewer cannot see is a 404, as for &#x60;GET /statuses/{id}&#x60;, so a token without &#x60;read:direct&#x60; gets a 404 for a direct post.  &#x60;provider&#x60; is omitted when no machine translated the answer: the post already has a body in the language, whose author wrote it, or it has no words to translate. Otherwise the server answers with a translation that it keeps, or asks the providers that its operator enabled. Only the text is sent to a provider, with no address, author, viewer, mention, or link. Characters sent count against monthly limits of the user and of the server, whichever app asks. &#x60;read:statuses&#x60; is enough on purpose: a translation is a way to read a post and changes nothing of the user&#39;s, so any app that shows posts may offer it, and the user&#39;s monthly limit bounds what one app can spend.  A 422 &#x60;/api/v1/problems/translation-unavailable&#x60; when no provider may receive this post: none is enabled, or none is allowed its visibility. A 422 &#x60;/api/v1/problems/translation-unsupported-language&#x60; when the providers do not translate the language, and a 422 without a type for a &#x60;target_language&#x60; that is not a language tag. A 429 &#x60;/api/v1/problems/translation-limit&#x60; over a monthly limit, until the next month in UTC, with &#x60;Retry-After&#x60; the seconds until then. A 503 &#x60;/api/v1/problems/translation-failed&#x60; when no provider returned a usable translation, which may pass. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val statusTranslationRequest : StatusTranslationRequest =  // StatusTranslationRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : StatusTranslation = apiInstance.translateStatus(id, statusTranslationRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#translateStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#translateStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **statusTranslationRequest** | [**StatusTranslationRequest**](StatusTranslationRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**StatusTranslation**](StatusTranslation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="unbookmarkStatus"></a>
# **unbookmarkStatus**
> Status unbookmarkStatus(id, idempotencyKey)

Remove the bookmark of a status

Without a bookmark it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unbookmarkStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unbookmarkStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unbookmarkStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="unboostStatus"></a>
# **unboostStatus**
> Status unboostStatus(id, idempotencyKey)

Retract the boost of a status

Without a boost it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unboostStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unboostStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unboostStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="unfavouriteStatus"></a>
# **unfavouriteStatus**
> Status unfavouriteStatus(id, idempotencyKey)

Remove the favourite of a status

Without a favourite it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unfavouriteStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unfavouriteStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unfavouriteStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="unfollowTag"></a>
# **unfollowTag**
> Hashtag unfollowTag(hashtag, idempotencyKey)

Unfollow a hashtag

Without a follow it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val hashtag : kotlin.String = hashtag_example // kotlin.String | The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the search of hashtags. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Hashtag = apiInstance.unfollowTag(hashtag, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unfollowTag")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unfollowTag")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hashtag** | **kotlin.String**| The hashtag, with or without a leading &#x60;#&#x60; (&#x60;%23&#x60;), matched under NFKC and lowercase: letters, marks, digits, and underscores with at least one letter or mark. Anything else is a 422 naming &#x60;hashtag&#x60;. The hashtag &#x60;search&#x60; is written &#x60;%23search&#x60;, since &#x60;/tags/search&#x60; is the search of hashtags.  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Hashtag**](Hashtag.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unmuteStatusThread"></a>
# **unmuteStatusThread**
> Status unmuteStatusThread(id, idempotencyKey)

Unmute the thread of a status

Ends the mute of the thread that the status belongs to. What happened in the thread while it was muted does not notify the user afterwards. Without a mute it changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unmuteStatusThread(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unmuteStatusThread")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unmuteStatusThread")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="unpinStatus"></a>
# **unpinStatus**
> Status unpinStatus(id, idempotencyKey)

Unpin a status

Without a pin it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unpinStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unpinStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unpinStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="unreactToStatus"></a>
# **unreactToStatus**
> Status unreactToStatus(id, idempotencyKey)

Take back the emoji reaction to a status

Without a reaction it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.unreactToStatus(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#unreactToStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#unreactToStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
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

<a id="voteInPoll"></a>
# **voteInPoll**
> Status voteInPoll(id, voteInPollRequest, idempotencyKey)

Vote in the poll of a status

Votes for one or more choices of the poll of a status. &#x60;poll_id&#x60; names the poll the user saw: a poll that an edit of the status replaced is a 409. A status without a poll, a poll that has ended, a second vote, or choices the poll does not take (none, an unknown one, the same one twice, or several in a single-choice poll) is a 422. A vote in a poll of another server is sent to that server, whose counts in &#x60;poll&#x60; include it once the server reports them again. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StatusesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the status.
val voteInPollRequest : VoteInPollRequest =  // VoteInPollRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.voteInPoll(id, voteInPollRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatusesApi#voteInPoll")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatusesApi#voteInPoll")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the status. | |
| **voteInPollRequest** | [**VoteInPollRequest**](VoteInPollRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Status**](Status.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

