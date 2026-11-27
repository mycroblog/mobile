# TimelinesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getFederatedTimeline**](TimelinesApi.md#getFederatedTimeline) | **GET** /timelines/federated | Get the federated timeline |
| [**getHashtagTimeline**](TimelinesApi.md#getHashtagTimeline) | **GET** /timelines/tag/{hashtag} | Get the timeline of a hashtag |
| [**getHomeTimeline**](TimelinesApi.md#getHomeTimeline) | **GET** /timelines/home | Get the home timeline |
| [**getListTimeline**](TimelinesApi.md#getListTimeline) | **GET** /timelines/list/{id} | Get the timeline of a list |
| [**getLocalTimeline**](TimelinesApi.md#getLocalTimeline) | **GET** /timelines/local | Get the local timeline |
| [**listAccountStatuses**](TimelinesApi.md#listAccountStatuses) | **GET** /accounts/{id}/statuses | List the posts and boosts of an account |


<a id="getFederatedTimeline"></a>
# **getFederatedTimeline**
> StatusPage getFederatedTimeline(cursor, limit)

Get the federated timeline

The public posts of the instance&#39;s accounts and of the remote accounts whose posts the instance holds, newest first, in the viewer&#39;s timeline languages. Posts of other visibilities, &#x60;local&#x60; ones included, and boosts never appear on it. The instance setting &#x60;federated_timeline_visibility&#x60; decides who may read it: while it is &#x60;off&#x60; it is a 404, and while it is &#x60;signed_in&#x60; an anonymous request is a 401.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.getFederatedTimeline(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#getFederatedTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#getFederatedTimeline")
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

<a id="getHashtagTimeline"></a>
# **getHashtagTimeline**
> StatusPage getHashtagTimeline(hashtag, cursor, limit)

Get the timeline of a hashtag

The public posts with the hashtag, newest first, and its local posts for a logged-in viewer, in the viewer&#39;s timeline languages. A hashtag that no post uses has an empty timeline. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val hashtag : kotlin.String = hashtag_example // kotlin.String | The hashtag without the `#`, matched case-insensitively.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.getHashtagTimeline(hashtag, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#getHashtagTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#getHashtagTimeline")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hashtag** | **kotlin.String**| The hashtag without the &#x60;#&#x60;, matched case-insensitively. | |
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

<a id="getHomeTimeline"></a>
# **getHomeTimeline**
> StatusPage getHomeTimeline(cursor, limit)

Get the home timeline

The viewer&#39;s own posts and boosts and those of the accounts the viewer follows, and the &#x60;public&#x60; and &#x60;local&#x60; posts of other accounts with a hashtag the viewer follows (which carry &#x60;followed_tags&#x60;), newest first, each once, in the viewer&#39;s timeline languages. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.getHomeTimeline(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#getHomeTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#getHomeTimeline")
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

<a id="getListTimeline"></a>
# **getListTimeline**
> StatusPage getListTimeline(id, cursor, limit)

Get the timeline of a list

The posts and boosts of the list&#39;s members, newest first, as the home timeline shows them (the viewer&#39;s timeline languages, blocks, mutes, domain mutes, and each follow&#39;s choice of showing boosts apply). The user&#39;s own posts are not in it. Another user&#39;s list is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the list.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.getListTimeline(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#getListTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#getListTimeline")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the list. | |
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

<a id="getLocalTimeline"></a>
# **getLocalTimeline**
> StatusPage getLocalTimeline(cursor, limit)

Get the local timeline

The public posts of the instance&#39;s accounts, newest first, and their local posts for a logged-in viewer, in the viewer&#39;s timeline languages. The instance setting &#x60;local_timeline_visibility&#x60; decides who may read it: while it is &#x60;off&#x60; it is a 404, and while it is &#x60;signed_in&#x60; an anonymous request is a 401.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : StatusPage = apiInstance.getLocalTimeline(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#getLocalTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#getLocalTimeline")
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

<a id="listAccountStatuses"></a>
# **listAccountStatuses**
> AccountStatusPage listAccountStatuses(id, excludeReplies, excludeBoosts, pinned, articles, tagged, cursor, limit)

List the posts and boosts of an account

The account&#39;s posts and boosts that the viewer may see, newest first: its public and unlisted posts for everyone, its local posts for logged-in viewers, its followers-only posts for its followers, and its direct posts addressed to the viewer. With &#x60;pinned&#x60;, the page lists the account&#39;s pinned posts instead, newest pin first, in a single page. With &#x60;articles&#x60;, the page lists only the account&#39;s articles, newest first, and &#x60;exclude_replies&#x60; and &#x60;exclude_boosts&#x60; change nothing; a page may hold fewer items than &#x60;limit&#x60; and still have a &#x60;next_cursor&#x60;. With &#x60;tagged&#x60;, the page lists only the account&#39;s posts that carry that hashtag now, newest first, with the visibility of the profile: no boosts, so &#x60;exclude_boosts&#x60; changes nothing, while &#x60;exclude_replies&#x60; still applies. &#x60;pinned&#x60; takes precedence over &#x60;articles&#x60;, and &#x60;articles&#x60; over &#x60;tagged&#x60;. A reply whose parent the viewer may not see has no &#x60;in_reply_to_id&#x60;.  The page lists only the posts this server has received. For a remote account, the first page (no &#x60;cursor&#x60;, no &#x60;pinned&#x60;, no &#x60;articles&#x60;, no &#x60;tagged&#x60;) of a logged-in viewer may start fetching the account&#39;s recent public posts from its server in the background; &#x60;remote_fetch_started&#x60; tells that it did. It does at most once a day per account, for at most 30 accounts per viewer in 5 minutes, and never for an account that the viewer blocks or that blocks the viewer.  An account that is not found by &#x60;getAccount&#x60; is a 404 here too: an unknown, suspended, deactivated, or deleted account, the instance actor, and an account that blocks the viewer. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TimelinesApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account.
val excludeReplies : kotlin.Boolean = true // kotlin.Boolean | Leave out the account's replies.
val excludeBoosts : kotlin.Boolean = true // kotlin.Boolean | Leave out the account's boosts.
val pinned : kotlin.Boolean = true // kotlin.Boolean | List only the account's pinned posts.
val articles : kotlin.Boolean = true // kotlin.Boolean | List only the account's articles; `pinned` takes precedence.
val tagged : kotlin.String = tagged_example // kotlin.String | List only the account's posts with this hashtag, by its name with or without `#` and in any case; a name that no post used lists nothing. `pinned` and `articles` take precedence. 
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountStatusPage = apiInstance.listAccountStatuses(id, excludeReplies, excludeBoosts, pinned, articles, tagged, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TimelinesApi#listAccountStatuses")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TimelinesApi#listAccountStatuses")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account. | |
| **excludeReplies** | **kotlin.Boolean**| Leave out the account&#39;s replies. | [optional] [default to false] |
| **excludeBoosts** | **kotlin.Boolean**| Leave out the account&#39;s boosts. | [optional] [default to false] |
| **pinned** | **kotlin.Boolean**| List only the account&#39;s pinned posts. | [optional] [default to false] |
| **articles** | **kotlin.Boolean**| List only the account&#39;s articles; &#x60;pinned&#x60; takes precedence. | [optional] [default to false] |
| **tagged** | **kotlin.String**| List only the account&#39;s posts with this hashtag, by its name with or without &#x60;#&#x60; and in any case; a name that no post used lists nothing. &#x60;pinned&#x60; and &#x60;articles&#x60; take precedence.  | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountStatusPage**](AccountStatusPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

