# NotificationsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**acceptNotificationRequest**](NotificationsApi.md#acceptNotificationRequest) | **POST** /notifications/requests/{id}/accept | Accept a notification request |
| [**dismissNotification**](NotificationsApi.md#dismissNotification) | **POST** /notifications/{id}/dismiss | Dismiss a notification |
| [**dismissNotificationRequest**](NotificationsApi.md#dismissNotificationRequest) | **POST** /notifications/requests/{id}/dismiss | Dismiss a notification request |
| [**getNotification**](NotificationsApi.md#getNotification) | **GET** /notifications/{id} | Get a notification |
| [**getNotificationMarker**](NotificationsApi.md#getNotificationMarker) | **GET** /notifications/marker | Get the read marker and the unread count |
| [**getNotificationRequest**](NotificationsApi.md#getNotificationRequest) | **GET** /notifications/requests/{id} | Get a notification request |
| [**getNotificationRequestSummary**](NotificationsApi.md#getNotificationRequestSummary) | **GET** /notifications/requests/summary | Count the notification requests |
| [**listNotificationRequestNotifications**](NotificationsApi.md#listNotificationRequestNotifications) | **GET** /notifications/requests/{id}/notifications | List the notifications of a notification request |
| [**listNotificationRequests**](NotificationsApi.md#listNotificationRequests) | **GET** /notifications/requests | List the notification requests |
| [**listNotifications**](NotificationsApi.md#listNotifications) | **GET** /notifications | List the authenticated user&#39;s notifications |
| [**readNotification**](NotificationsApi.md#readNotification) | **POST** /notifications/{id}/read | Mark one notification read |
| [**updateNotificationMarker**](NotificationsApi.md#updateNotificationMarker) | **PUT** /notifications/marker | Mark notifications read |


<a id="acceptNotificationRequest"></a>
# **acceptNotificationRequest**
> acceptNotificationRequest(id, idempotencyKey)

Accept a notification request

Moves the notifications of the request into the list, unread when they are newer than the read marker, without delivering them, and accepts the later notifications of the source whatever the policy says. A 404 when the source has no request. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.acceptNotificationRequest(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#acceptNotificationRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#acceptNotificationRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
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

<a id="dismissNotification"></a>
# **dismissNotification**
> dismissNotification(id, idempotencyKey)

Dismiss a notification

Removes the notification from the list and the unread count. Dismissing it again changes nothing; a notification that is not the user&#39;s is a 404, and so is a moderator notification to another client than the server&#39;s own. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the notification.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.dismissNotification(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#dismissNotification")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#dismissNotification")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the notification. | |
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

<a id="dismissNotificationRequest"></a>
# **dismissNotificationRequest**
> dismissNotificationRequest(id, idempotencyKey)

Dismiss a notification request

Removes the notifications of the request, which are never listed. The source is not accepted: a later notification that the policy filters opens a new request. A 404 when the source has no request. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.dismissNotificationRequest(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#dismissNotificationRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#dismissNotificationRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
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

<a id="getNotification"></a>
# **getNotification**
> Notification getNotification(id)

Get a notification

One of the user&#39;s notifications, as the list shows it. A notification that is not the user&#39;s, is dismissed, or that the list leaves out is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the notification.
try {
    val result : Notification = apiInstance.getNotification(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#getNotification")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#getNotification")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the notification. | |

### Return type

[**Notification**](Notification.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getNotificationMarker"></a>
# **getNotificationMarker**
> NotificationMarker getNotificationMarker()

Get the read marker and the unread count

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
try {
    val result : NotificationMarker = apiInstance.getNotificationMarker()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#getNotificationMarker")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#getNotificationMarker")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NotificationMarker**](NotificationMarker.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getNotificationRequest"></a>
# **getNotificationRequest**
> NotificationRequest getNotificationRequest(id)

Get a notification request

The request of a source account; a 404 when the list of requests does not show one.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
try {
    val result : NotificationRequest = apiInstance.getNotificationRequest(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#getNotificationRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#getNotificationRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |

### Return type

[**NotificationRequest**](NotificationRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getNotificationRequestSummary"></a>
# **getNotificationRequestSummary**
> NotificationRequestSummary getNotificationRequestSummary()

Count the notification requests

The number of requests that the list of requests shows, and of their notifications.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
try {
    val result : NotificationRequestSummary = apiInstance.getNotificationRequestSummary()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#getNotificationRequestSummary")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#getNotificationRequestSummary")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NotificationRequestSummary**](NotificationRequestSummary.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listNotificationRequestNotifications"></a>
# **listNotificationRequestNotifications**
> NotificationPage listNotificationRequestNotifications(id, cursor, limit)

List the notifications of a notification request

The notifications waiting in the request of a source account, newest first, without the dismissed ones and those about deleted posts; a 404 when the list of requests does not show the request. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : NotificationPage = apiInstance.listNotificationRequestNotifications(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#listNotificationRequestNotifications")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#listNotificationRequestNotifications")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**NotificationPage**](NotificationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listNotificationRequests"></a>
# **listNotificationRequests**
> NotificationRequestPage listNotificationRequests(cursor, limit)

List the notification requests

The sources whose notifications the user&#39;s notification policy filtered, one request per source, the one with the newest notification first. The sources that the user blocks, is blocked by, or mutes with notifications hidden, those of a domain the user mutes, and those deactivated, suspended, or whose profile moderation hides are left out, so a page may hold fewer than &#x60;limit&#x60; requests before the last. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : NotificationRequestPage = apiInstance.listNotificationRequests(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#listNotificationRequests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#listNotificationRequests")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**NotificationRequestPage**](NotificationRequestPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listNotifications"></a>
# **listNotifications**
> NotificationPage listNotifications(types, cursor, limit)

List the authenticated user&#39;s notifications

The user&#39;s notifications, newest first. Dismissed notifications, notifications about deleted posts, and notifications from accounts that the user blocks, is blocked by, or mutes with notifications hidden are left out, also those that came before the block or mute. The notifications that the user&#39;s notification policy filtered wait in the notification requests instead, until the user accepts the request of their source. The moderator notifications (&#x60;admin_report&#x60;, &#x60;admin_sign_up&#x60;, &#x60;admin_appeal&#x60;, &#x60;admin_account_moved&#x60;, &#x60;admin_webhook_disabled&#x60;, &#x60;admin_custom_emoji_request&#x60;, &#x60;admin_service_closure_due&#x60;, &#x60;admin_error_group&#x60;, &#x60;admin_hook_failing&#x60;, &#x60;admin_hook_appeal&#x60;, &#x60;admin_domain_list_failing&#x60;) are left out once the user&#39;s roles no longer grant the permission they need. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val types : kotlin.collections.List<NotificationType> =  // kotlin.collections.List<NotificationType> | Keep only notifications of these types; omitted keeps every type.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : NotificationPage = apiInstance.listNotifications(types, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#listNotifications")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#listNotifications")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **types** | [**kotlin.collections.List&lt;NotificationType&gt;**](NotificationType.md)| Keep only notifications of these types; omitted keeps every type. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**NotificationPage**](NotificationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="readNotification"></a>
# **readNotification**
> readNotification(id, idempotencyKey)

Mark one notification read

Marks one notification read, wherever it is in the list, for every client of the user. A notification is unread while it is newer than the marker&#39;s &#x60;last_read_id&#x60; and not marked read here; marking it again, or marking one the marker already passed, changes nothing. A notification that is not the user&#39;s is a 404, and so is a moderator notification to another client than the server&#39;s own, which does not get it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the notification.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.readNotification(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#readNotification")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#readNotification")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the notification. | |
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

<a id="updateNotificationMarker"></a>
# **updateNotificationMarker**
> NotificationMarker updateNotificationMarker(notificationMarkerUpdate, idempotencyKey)

Mark notifications read

Marks the notifications up to and including &#x60;last_read_id&#x60; as read. The marker only moves forward: a &#x60;last_read_id&#x60; older than the current marker changes nothing, so clients that report their positions concurrently never move it back. A &#x60;last_read_id&#x60; that is not one of the user&#39;s notifications is a 404.  The user has one marker, shared by all clients. Moving it marks everything up to &#x60;last_read_id&#x60; as read for every client, including notifications that this client does not list, such as the moderator notifications that reach first-party clients only. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = NotificationsApi()
val notificationMarkerUpdate : NotificationMarkerUpdate =  // NotificationMarkerUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : NotificationMarker = apiInstance.updateNotificationMarker(notificationMarkerUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationsApi#updateNotificationMarker")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationsApi#updateNotificationMarker")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **notificationMarkerUpdate** | [**NotificationMarkerUpdate**](NotificationMarkerUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**NotificationMarker**](NotificationMarker.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

