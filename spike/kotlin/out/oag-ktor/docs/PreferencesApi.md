# PreferencesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getAutoDeletePolicy**](PreferencesApi.md#getAutoDeletePolicy) | **GET** /preferences/auto_delete | Get the authenticated user&#39;s automatic deletion of old posts |
| [**getEventReminders**](PreferencesApi.md#getEventReminders) | **GET** /preferences/event_reminders | Get the authenticated user&#39;s reminders of events |
| [**getNotificationPolicy**](PreferencesApi.md#getNotificationPolicy) | **GET** /preferences/notification_policy | Get the authenticated user&#39;s notification policy |
| [**getNotificationSettings**](PreferencesApi.md#getNotificationSettings) | **GET** /preferences/notifications | Get the authenticated user&#39;s notification settings |
| [**getPreferences**](PreferencesApi.md#getPreferences) | **GET** /preferences | Get the authenticated user&#39;s preferences |
| [**getSupporterListing**](PreferencesApi.md#getSupporterListing) | **GET** /preferences/supporter_listing | Get whether the authenticated user is on the supporter list |
| [**listBadgeChoices**](PreferencesApi.md#listBadgeChoices) | **GET** /preferences/badges | List the badges that the authenticated user may show |
| [**previewAutoDeletePolicy**](PreferencesApi.md#previewAutoDeletePolicy) | **POST** /preferences/auto_delete/preview | Estimate what a draft automatic deletion of old posts would delete |
| [**updateAutoDeletePolicy**](PreferencesApi.md#updateAutoDeletePolicy) | **PUT** /preferences/auto_delete | Replace the authenticated user&#39;s automatic deletion of old posts |
| [**updateBadgeChoice**](PreferencesApi.md#updateBadgeChoice) | **PUT** /preferences/badges/{role_id} | Show or hide the badge of a role on the authenticated user&#39;s profile |
| [**updateEventReminders**](PreferencesApi.md#updateEventReminders) | **PUT** /preferences/event_reminders | Change the authenticated user&#39;s reminders of events |
| [**updateNotificationPolicy**](PreferencesApi.md#updateNotificationPolicy) | **PATCH** /preferences/notification_policy | Change the authenticated user&#39;s notification policy |
| [**updateNotificationSettings**](PreferencesApi.md#updateNotificationSettings) | **PATCH** /preferences/notifications | Change the authenticated user&#39;s notification settings |
| [**updatePreferences**](PreferencesApi.md#updatePreferences) | **PATCH** /preferences | Change the authenticated user&#39;s preferences |
| [**updateSupporterListing**](PreferencesApi.md#updateSupporterListing) | **PUT** /preferences/supporter_listing | Choose whether the authenticated user is on the supporter list |


<a id="getAutoDeletePolicy"></a>
# **getAutoDeletePolicy**
> AutoDeleteSettings getAutoDeletePolicy()

Get the authenticated user&#39;s automatic deletion of old posts

The policy that deletes the user&#39;s own posts some days after their publication, with the server maximum in &#x60;server_limit&#x60; when it applies to the user. A user who has never saved one gets the default policy, which is disabled; a user who signed up after the operator set a server default got that default as their own policy. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : AutoDeleteSettings = apiInstance.getAutoDeletePolicy()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getAutoDeletePolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getAutoDeletePolicy")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AutoDeleteSettings**](AutoDeleteSettings.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getEventReminders"></a>
# **getEventReminders**
> EventReminders getEventReminders()

Get the authenticated user&#39;s reminders of events

How long before the start of an event that the user answered going or maybe an &#x60;event_reminder&#x60; reminds the user: one day and one hour before until the user chooses. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : EventReminders = apiInstance.getEventReminders()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getEventReminders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getEventReminders")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**EventReminders**](EventReminders.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getNotificationPolicy"></a>
# **getNotificationPolicy**
> NotificationPolicy getNotificationPolicy()

Get the authenticated user&#39;s notification policy

What happens to the notifications of other accounts, by condition on their source: listed, filtered into the request of their source, or dropped. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : NotificationPolicy = apiInstance.getNotificationPolicy()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getNotificationPolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getNotificationPolicy")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NotificationPolicy**](NotificationPolicy.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getNotificationSettings"></a>
# **getNotificationSettings**
> NotificationSettings getNotificationSettings()

Get the authenticated user&#39;s notification settings

Whether the user gets each kind of notification in the app and by email, for the kinds the user gets. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : NotificationSettings = apiInstance.getNotificationSettings()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getNotificationSettings")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getNotificationSettings")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NotificationSettings**](NotificationSettings.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getPreferences"></a>
# **getPreferences**
> Preferences getPreferences()

Get the authenticated user&#39;s preferences

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : Preferences = apiInstance.getPreferences()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getPreferences")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getPreferences")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Preferences**](Preferences.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getSupporterListing"></a>
# **getSupporterListing**
> SupporterListing getSupporterListing()

Get whether the authenticated user is on the supporter list

Whether the user chose to be listed on the supporter list (&#x60;GET /instance/supporters&#x60;). Nobody is listed until they choose to be, and the choice ends with the subscription. A 404 for a user who is not a member of a plan (an &#x60;active&#x60;, &#x60;canceling&#x60;, or &#x60;past_due&#x60; subscription) and while the program is disabled. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : SupporterListing = apiInstance.getSupporterListing()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#getSupporterListing")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#getSupporterListing")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SupporterListing**](SupporterListing.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listBadgeChoices"></a>
# **listBadgeChoices**
> BadgeChoiceList listBadgeChoices()

List the badges that the authenticated user may show

The roles that the user holds and that have a badge, highest position first, with whether the user shows each badge on their profile. Every badge is hidden until the user shows it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
try {
    val result : BadgeChoiceList = apiInstance.listBadgeChoices()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#listBadgeChoices")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#listBadgeChoices")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**BadgeChoiceList**](BadgeChoiceList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="previewAutoDeletePolicy"></a>
# **previewAutoDeletePolicy**
> AutoDeletePreview previewAutoDeletePolicy(autoDeletePolicy, idempotencyKey)

Estimate what a draft automatic deletion of old posts would delete

Counts the user&#39;s posts that the given policy, which is not saved, would delete now, as if it were enabled (&#x60;enabled&#x60; is ignored): posts published more than &#x60;min_age_days&#x60; days ago that none of the exceptions keeps. The count stops at 1000 (&#x60;more&#x60; is then true), so the call stays cheap enough to repeat as the user edits the policy. The response also shows the oldest of those posts. While the server maximum applies to the user, it counts what the policy deletes under the maximum (&#x60;server_limit.effective&#x60; of &#x60;getAutoDeletePolicy&#x60; for the saved policy), so the preview of the saved policy of a bound user shows what goes once the grace period ends. A 422 problem names the property that is invalid in &#x60;detail&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val autoDeletePolicy : AutoDeletePolicy =  // AutoDeletePolicy | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AutoDeletePreview = apiInstance.previewAutoDeletePolicy(autoDeletePolicy, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#previewAutoDeletePolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#previewAutoDeletePolicy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **autoDeletePolicy** | [**AutoDeletePolicy**](AutoDeletePolicy.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AutoDeletePreview**](AutoDeletePreview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateAutoDeletePolicy"></a>
# **updateAutoDeletePolicy**
> AutoDeleteSettings updateAutoDeletePolicy(autoDeletePolicy, idempotencyKey)

Replace the authenticated user&#39;s automatic deletion of old posts

Replaces the whole policy. Once it is enabled, an hourly job deletes the user&#39;s posts published more than &#x60;min_age_days&#x60; days ago, unless an exception keeps them, as the user would delete them: each deletion is sent to the servers the post went to. A run deletes at most 100 posts of a user, so a policy that finds more spreads their deletion over the following hours. Boosts are not deleted. While the server maximum applies to the user, a policy that is disabled, keeps posts longer than the maximum, or uses an exception that the maximum does not allow is refused. A 422 problem names the property that is invalid or over the maximum in &#x60;detail&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val autoDeletePolicy : AutoDeletePolicy =  // AutoDeletePolicy | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AutoDeleteSettings = apiInstance.updateAutoDeletePolicy(autoDeletePolicy, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateAutoDeletePolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateAutoDeletePolicy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **autoDeletePolicy** | [**AutoDeletePolicy**](AutoDeletePolicy.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AutoDeleteSettings**](AutoDeleteSettings.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateBadgeChoice"></a>
# **updateBadgeChoice**
> BadgeChoice updateBadgeChoice(roleId, updateBadgeChoiceRequest, idempotencyKey)

Show or hide the badge of a role on the authenticated user&#39;s profile

Shows or hides the badge of a role that the user holds on their profile. The badge appears in &#x60;Account.badges&#x60; of the user&#39;s own account endpoints only, never on posts, in lists of accounts, or to other servers. A role that the user does not hold, or that has no badge, is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val roleId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the role whose badge it is.
val updateBadgeChoiceRequest : UpdateBadgeChoiceRequest =  // UpdateBadgeChoiceRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : BadgeChoice = apiInstance.updateBadgeChoice(roleId, updateBadgeChoiceRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateBadgeChoice")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateBadgeChoice")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **roleId** | **java.util.UUID**| The ID of the role whose badge it is. | |
| **updateBadgeChoiceRequest** | [**UpdateBadgeChoiceRequest**](UpdateBadgeChoiceRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**BadgeChoice**](BadgeChoice.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateEventReminders"></a>
# **updateEventReminders**
> EventReminders updateEventReminders(eventRemindersUpdate, idempotencyKey)

Change the authenticated user&#39;s reminders of events

Replaces the reminders with &#x60;minutes_before&#x60;, or restores the defaults with &#x60;defaults&#x60;. The reminders of the upcoming events that the user answered going or maybe follow the change. A 422 problem when &#x60;minutes_before&#x60; has more than 3 reminders, one shorter than 5 minutes or longer than 7 days, or the same one twice, and when the request has neither &#x60;minutes_before&#x60; nor &#x60;defaults&#x60; true; nothing is changed then. An empty &#x60;minutes_before&#x60; turns the reminders off. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val eventRemindersUpdate : EventRemindersUpdate =  // EventRemindersUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventReminders = apiInstance.updateEventReminders(eventRemindersUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateEventReminders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateEventReminders")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **eventRemindersUpdate** | [**EventRemindersUpdate**](EventRemindersUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventReminders**](EventReminders.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateNotificationPolicy"></a>
# **updateNotificationPolicy**
> NotificationPolicy updateNotificationPolicy(notificationPolicyUpdate, idempotencyKey)

Change the authenticated user&#39;s notification policy

Changes the actions of the conditions in the body and keeps the others. The notifications already created stay where they went. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val notificationPolicyUpdate : NotificationPolicyUpdate =  // NotificationPolicyUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : NotificationPolicy = apiInstance.updateNotificationPolicy(notificationPolicyUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateNotificationPolicy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateNotificationPolicy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **notificationPolicyUpdate** | [**NotificationPolicyUpdate**](NotificationPolicyUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**NotificationPolicy**](NotificationPolicy.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateNotificationSettings"></a>
# **updateNotificationSettings**
> NotificationSettings updateNotificationSettings(notificationSettingsUpdate, idempotencyKey)

Change the authenticated user&#39;s notification settings

Changes the cells of &#x60;changes&#x60; and keeps the others. A 422 problem when a change names a row the user does not get or a channel that is fixed; nothing is changed then. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val notificationSettingsUpdate : NotificationSettingsUpdate =  // NotificationSettingsUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : NotificationSettings = apiInstance.updateNotificationSettings(notificationSettingsUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateNotificationSettings")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateNotificationSettings")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **notificationSettingsUpdate** | [**NotificationSettingsUpdate**](NotificationSettingsUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**NotificationSettings**](NotificationSettings.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updatePreferences"></a>
# **updatePreferences**
> Preferences updatePreferences(updatePreferencesRequest, idempotencyKey)

Change the authenticated user&#39;s preferences

Properties left out of the request keep their current values. Language tags are canonicalized (&#x60;ja-jp&#x60; becomes &#x60;ja-JP&#x60;). A 422 problem names the property that is invalid in &#x60;detail&#x60;. On a one-person server (&#x60;Instance.single_user&#x60;) &#x60;default_visibility&#x60; cannot be &#x60;local&#x60;, and a stored &#x60;local&#x60; reads as &#x60;private&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val updatePreferencesRequest : UpdatePreferencesRequest =  // UpdatePreferencesRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Preferences = apiInstance.updatePreferences(updatePreferencesRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updatePreferences")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updatePreferences")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updatePreferencesRequest** | [**UpdatePreferencesRequest**](UpdatePreferencesRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Preferences**](Preferences.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateSupporterListing"></a>
# **updateSupporterListing**
> SupporterListing updateSupporterListing(supporterListing, idempotencyKey)

Choose whether the authenticated user is on the supporter list

Lists the user on the supporter list or takes them off it. The choice is one per user, whichever plan they are a member of; it stays when the plan changes and is deleted when the subscription ends. A 404 for a user who is not a member of a plan and while the program is disabled. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PreferencesApi()
val supporterListing : SupporterListing =  // SupporterListing | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SupporterListing = apiInstance.updateSupporterListing(supporterListing, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PreferencesApi#updateSupporterListing")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PreferencesApi#updateSupporterListing")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **supporterListing** | [**SupporterListing**](SupporterListing.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SupporterListing**](SupporterListing.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

