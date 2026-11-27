# AnnouncementsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getAnnouncement**](AnnouncementsApi.md#getAnnouncement) | **GET** /announcements/{id} | Get an announcement |
| [**listAnnouncementHistory**](AnnouncementsApi.md#listAnnouncementHistory) | **GET** /announcements/history | List the announcements, past ones included |
| [**listAnnouncements**](AnnouncementsApi.md#listAnnouncements) | **GET** /announcements | List the announcements |
| [**markAnnouncementRead**](AnnouncementsApi.md#markAnnouncementRead) | **POST** /announcements/{id}/read | Mark an announcement read |


<a id="getAnnouncement"></a>
# **getAnnouncement**
> Announcement getAnnouncement(id)

Get an announcement

One announcement of &#x60;listAnnouncementHistory&#x60;: a published one whose &#x60;published_at&#x60; has passed, one whose window has ended included (&#x60;ended&#x60;), with whether the user has read it. A draft, a withdrawn one, one that has not started to show, or an unknown ID is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AnnouncementsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
try {
    val result : Announcement = apiInstance.getAnnouncement(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AnnouncementsApi#getAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AnnouncementsApi#getAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |

### Return type

[**Announcement**](Announcement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAnnouncementHistory"></a>
# **listAnnouncementHistory**
> AnnouncementPage listAnnouncementHistory(cursor, limit)

List the announcements, past ones included

The server&#39;s announcements that have started to show to the local users: the published ones whose &#x60;published_at&#x60; has passed, those whose window has ended included (&#x60;ended&#x60;), and the withdrawn ones left out. The ones whose &#x60;published_at&#x60; is the latest come first, each with whether the user has read it. Only the announcements that show now can be marked read (&#x60;markAnnouncementRead&#x60;). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AnnouncementsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AnnouncementPage = apiInstance.listAnnouncementHistory(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AnnouncementsApi#listAnnouncementHistory")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AnnouncementsApi#listAnnouncementHistory")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AnnouncementPage**](AnnouncementPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAnnouncements"></a>
# **listAnnouncements**
> AnnouncementList listAnnouncements()

List the announcements

The server&#39;s announcements that every local user sees now: published, and within their window. The ones whose &#x60;published_at&#x60; is the latest come first, each with whether the user has read it. The list is not paginated; &#x60;listAnnouncementHistory&#x60; pages through the past ones too. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AnnouncementsApi()
try {
    val result : AnnouncementList = apiInstance.listAnnouncements()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AnnouncementsApi#listAnnouncements")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AnnouncementsApi#listAnnouncements")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AnnouncementList**](AnnouncementList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="markAnnouncementRead"></a>
# **markAnnouncementRead**
> Announcement markAnnouncementRead(id, idempotencyKey)

Mark an announcement read

Records that the user read the announcement; marking it again changes nothing. An announcement that the users do not see now (a draft, a withdrawn one, one outside its window, an unknown ID) is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AnnouncementsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Announcement = apiInstance.markAnnouncementRead(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AnnouncementsApi#markAnnouncementRead")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AnnouncementsApi#markAnnouncementRead")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Announcement**](Announcement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

