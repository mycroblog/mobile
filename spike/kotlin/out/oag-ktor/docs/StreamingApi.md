# StreamingApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**streamEvents**](StreamingApi.md#streamEvents) | **GET** /streaming | Stream real-time updates |
| [**streamTimeline**](StreamingApi.md#streamTimeline) | **GET** /streaming/timelines/{timeline} | Stream the new posts of the local or the federated timeline |


<a id="streamEvents"></a>
# **streamEvents**
> kotlin.String streamEvents()

Stream real-time updates

A Server-Sent Events stream (&#x60;text/event-stream&#x60;) of the authenticated user&#39;s new home and list timeline entries, notifications, conversations of the direct messages, announcements, and the edits and deletions of the statuses it may see, from the moment it opens. Read it with &#x60;fetch&#x60; and send the access token in &#x60;Authorization&#x60;, like every other operation (&#x60;EventSource&#x60; cannot send it).  Events:  - &#x60;update&#x60;: a new post or boost in the home timeline, as it would appear there (the   viewer&#39;s follows, timeline languages, blocks, and mutes apply). &#x60;data&#x60; is a &#x60;Status&#x60;. - &#x60;list_update&#x60;: the same post or boost, once for each of the viewer&#39;s lists whose   timeline shows it (its author is a member), right after its &#x60;update&#x60;. &#x60;data&#x60; is   &#x60;{\&quot;list_id\&quot;: \&quot;&lt;list id&gt;\&quot;, \&quot;status\&quot;: &lt;Status&gt;}&#x60;. - &#x60;notification&#x60;: a new notification. &#x60;data&#x60; is a &#x60;Notification&#x60;. A notification that the   user&#39;s notification policy filters into a notification request sends none. - &#x60;notification_requests&#x60;: the counts of the notification requests changed (a notification   was filtered into one, or one was accepted or dismissed). &#x60;data&#x60; is a   &#x60;NotificationRequestSummary&#x60;. - &#x60;notifications_read&#x60;: the user read notifications on a device: the marker moved, or one   notification was read on its own. &#x60;data&#x60; is a &#x60;NotificationsRead&#x60;. - &#x60;conversation&#x60;: a conversation of the direct messages changed: a &#x60;direct&#x60; post that the   user sent or received arrived in it, or the user read it on a device. &#x60;data&#x60; is a   &#x60;Conversation&#x60;, as &#x60;listConversations&#x60; shows it; nothing is sent for a conversation that it   leaves out, nor without the &#x60;read:direct&#x60; scope. - &#x60;announcement&#x60;: an announcement that the users see was published or changed. &#x60;data&#x60;   is an &#x60;Announcement&#x60;. - &#x60;announcement_removed&#x60;: an announcement no longer shows (it was withdrawn, or its   window changed). &#x60;data&#x60; is &#x60;{\&quot;id\&quot;: \&quot;&lt;announcement id&gt;\&quot;}&#x60;. - &#x60;status_update&#x60;: a status that the user may see was edited, by a local author or on its   remote server. &#x60;data&#x60; is the edited &#x60;Status&#x60; as the user sees it; replace the status, and   the status inside the boosts of it, wherever they show. It is sent to the users who may see   the status now (its visibility, their follows, the accounts it mentions, and blocks   apply), not without the &#x60;read:direct&#x60; scope for a &#x60;direct&#x60; post. The keyword filters of   the home timeline apply as for &#x60;update&#x60;: a status that a warning filter matches arrives   with &#x60;filtered&#x60;, and one that a hiding filter matches sends a &#x60;delete&#x60; instead. - &#x60;delete&#x60;: remove a status that the user may have seen: it was deleted by its author, a   moderator, or its remote server, or it was edited and a hiding keyword filter of the   home timeline now matches it. &#x60;data&#x60; is &#x60;{\&quot;id\&quot;: \&quot;&lt;status id&gt;\&quot;}&#x60;; remove the status, and   the boosts of it, wherever they show. A deletion is sent to the users who may see the   status now (its visibility, their follows, and blocks apply), not to the recipients of a   &#x60;direct&#x60; post other than its author, nor without the &#x60;read:direct&#x60; scope for a &#x60;direct&#x60;   post. Purges of many statuses at once (an account&#39;s deletion, the automatic deletion of   old posts) send none.  A comment line (&#x60;: heartbeat&#x60;) arrives every 30 seconds. The server ends the stream when the access token stops being valid, when the client does not keep up, and when it shuts down; nothing is replayed, so after reconnecting read the home timeline, the open list timelines, the notifications, and the conversations again for what was missed. Announcements whose window starts later arrive with no event: list the announcements again from time to time. A user may hold a limited number of streams open at once; past it the answer is 429 with type &#x60;/api/v1/problems/too-many-streams&#x60;. While the server cannot receive updates (its broker is not ready, or it is shutting down) the answer is 503 with &#x60;Retry-After&#x60;; try again after that many seconds. The server may also end an open stream when it may have missed updates; reconnect and read again, as after any other end.  With the &#x60;stream &#x3D; hub&#x60; setting, each server process delivers only the updates that it produced itself, so a deployment that runs several web processes, or separate web and worker processes, misses updates on some streams. A deployment with a shared stream broker does not. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StreamingApi()
try {
    val result : kotlin.String = apiInstance.streamEvents()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StreamingApi#streamEvents")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StreamingApi#streamEvents")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.String**

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/event-stream, application/problem+json

<a id="streamTimeline"></a>
# **streamTimeline**
> kotlin.String streamTimeline(timeline)

Stream the new posts of the local or the federated timeline

A Server-Sent Events stream (&#x60;text/event-stream&#x60;) of the new posts of the local timeline (&#x60;local&#x60;) or the federated timeline (&#x60;federated&#x60;), from the moment it opens. Read it with &#x60;fetch&#x60; and send the access token in &#x60;Authorization&#x60;, like every other operation (&#x60;EventSource&#x60; cannot send it). Only logged-in users open it, whatever the instance setting &#x60;local_timeline_visibility&#x60; or &#x60;federated_timeline_visibility&#x60; allows anonymous visitors; while the setting of the timeline is &#x60;off&#x60; it is a 404.  Events:  - &#x60;update&#x60;: a new post of the timeline, as &#x60;GET /api/v1/timelines/local&#x60; or   &#x60;GET /api/v1/timelines/federated&#x60; would list it for the user at that moment (the visibility,   the user&#39;s blocks, mutes, and muted domains, and the user&#39;s timeline languages apply).   &#x60;data&#x60; is a &#x60;Status&#x60;. The local timeline has the public posts of the local accounts and   their &#x60;local&#x60; posts; the federated timeline has the public posts of local and remote   accounts, never &#x60;local&#x60; posts, and neither has boosts. A remote post arrives when the   server stores it. - &#x60;status_update&#x60;: a status that the timeline may list for the user was edited, as   &#x60;GET /api/v1/streaming&#x60; sends it, with the keyword filters of the public timelines.   &#x60;data&#x60; is a &#x60;Status&#x60;. - &#x60;delete&#x60;: a status that the timeline may have listed for the user was deleted, or was   edited and a hiding keyword filter of the public timelines now matches it, as   &#x60;GET /api/v1/streaming&#x60; sends it. &#x60;data&#x60; is &#x60;{\&quot;id\&quot;: \&quot;&lt;status id&gt;\&quot;}&#x60;.  A comment line (&#x60;: heartbeat&#x60;) arrives every 30 seconds. The server ends the stream when the access token stops being valid, when the setting of the timeline becomes &#x60;off&#x60;, when the client does not keep up, and when it shuts down; nothing is replayed, so after reconnecting read the first page of the timeline again for what was missed. The stream counts toward the streams a user may hold open at once, with &#x60;GET /api/v1/streaming&#x60;; past the limit the answer is 429 with type &#x60;/api/v1/problems/too-many-streams&#x60;. While the server cannot receive updates (its broker is not ready, or it is shutting down) the answer is 503 with &#x60;Retry-After&#x60;; try again after that many seconds. The server may also end an open stream when it may have missed updates; reconnect and read again, as after any other end.  With the &#x60;stream &#x3D; hub&#x60; setting, each server process delivers only the updates that it produced itself, so a deployment that runs several web processes, or separate web and worker processes, misses updates on some streams. A deployment with a shared stream broker does not.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;, and an open stream ends at the next heartbeat after the server becomes one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = StreamingApi()
val timeline : kotlin.String = timeline_example // kotlin.String | The timeline to stream, `local` or `federated`; any other is a 404.
try {
    val result : kotlin.String = apiInstance.streamTimeline(timeline)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StreamingApi#streamTimeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StreamingApi#streamTimeline")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **timeline** | **kotlin.String**| The timeline to stream, &#x60;local&#x60; or &#x60;federated&#x60;; any other is a 404. | [enum: local, federated] |

### Return type

**kotlin.String**

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/event-stream, application/problem+json

