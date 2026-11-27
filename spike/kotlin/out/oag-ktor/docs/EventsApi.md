# EventsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**addEventCandidate**](EventsApi.md#addEventCandidate) | **POST** /events/{id}/candidates | Add a candidate date to an event |
| [**answerEvent**](EventsApi.md#answerEvent) | **PUT** /events/{id}/rsvp | Answer an event |
| [**approveEventAttendee**](EventsApi.md#approveEventAttendee) | **PUT** /events/{id}/attendees/{account_id}/approval | Approve an attendee of an event |
| [**assignEventCoorganizer**](EventsApi.md#assignEventCoorganizer) | **PUT** /events/{id}/coorganizers/{account_id} | Name a co-organizer of an event |
| [**cancelEvent**](EventsApi.md#cancelEvent) | **POST** /events/{id}/cancel | Cancel an event |
| [**confirmEventCandidate**](EventsApi.md#confirmEventCandidate) | **POST** /events/{id}/candidates/{candidate_id}/confirm | Confirm a candidate date of an event |
| [**createEvent**](EventsApi.md#createEvent) | **POST** /events | Create an event |
| [**getEvent**](EventsApi.md#getEvent) | **GET** /events/{id} | Get an event |
| [**getEventCalendar**](EventsApi.md#getEventCalendar) | **GET** /events/{id}/calendar.ics | Get an event as an iCalendar file |
| [**inviteToEvent**](EventsApi.md#inviteToEvent) | **POST** /events/{id}/invitations | Invite a user to an event |
| [**listEventAttendees**](EventsApi.md#listEventAttendees) | **GET** /events/{id}/attendees | List the attendees of an event |
| [**listEventBoard**](EventsApi.md#listEventBoard) | **GET** /events/{id}/board | List the posts of the board of an event |
| [**listEventCandidateVotes**](EventsApi.md#listEventCandidateVotes) | **GET** /events/{id}/candidates/{candidate_id}/votes | List the answers to a candidate date of an event |
| [**listEventCandidates**](EventsApi.md#listEventCandidates) | **GET** /events/{id}/candidates | List the candidate dates of an event |
| [**listEventInvitations**](EventsApi.md#listEventInvitations) | **GET** /events/{id}/invitations | List the invitations of an event |
| [**listEvents**](EventsApi.md#listEvents) | **GET** /events | List the upcoming events |
| [**postToEventBoard**](EventsApi.md#postToEventBoard) | **POST** /events/{id}/board | Post on the board of an event |
| [**removeEventCoorganizer**](EventsApi.md#removeEventCoorganizer) | **DELETE** /events/{id}/coorganizers/{account_id} | Remove a co-organizer of an event |
| [**revokeEventAttendeeApproval**](EventsApi.md#revokeEventAttendeeApproval) | **DELETE** /events/{id}/attendees/{account_id}/approval | Revoke the approval of an attendee of an event |
| [**revokeEventInvitation**](EventsApi.md#revokeEventInvitation) | **DELETE** /events/{id}/invitations/{account_id} | Revoke an invitation to an event |
| [**updateEvent**](EventsApi.md#updateEvent) | **PATCH** /events/{id} | Change an event |
| [**voteEventCandidate**](EventsApi.md#voteEventCandidate) | **PUT** /events/{id}/candidates/{candidate_id}/vote | Answer a candidate date of an event |
| [**withdrawEventCandidate**](EventsApi.md#withdrawEventCandidate) | **DELETE** /events/{id}/candidates/{candidate_id} | Withdraw a candidate date of an event |


<a id="addEventCandidate"></a>
# **addEventCandidate**
> EventCandidate addEventCandidate(id, eventCandidateCreate, idempotencyKey)

Add a candidate date to an event

Adds a candidate date to the date poll of an event that has no date yet, by the organizer or a co-organizer. A poll has up to 20 candidates; one more, the same date twice, and a date in the past are a 422. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event, and a 409 &#x60;/api/v1/problems/event-scheduled&#x60; for an event whose date was confirmed. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val eventCandidateCreate : EventCandidateCreate =  // EventCandidateCreate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventCandidate = apiInstance.addEventCandidate(id, eventCandidateCreate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#addEventCandidate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#addEventCandidate")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **eventCandidateCreate** | [**EventCandidateCreate**](EventCandidateCreate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventCandidate**](EventCandidate.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="answerEvent"></a>
# **answerEvent**
> Event answerEvent(id, eventRSVP, idempotencyKey)

Answer an event

Answers the event &#x60;going&#x60;, &#x60;maybe&#x60;, or &#x60;declined&#x60;, as often as the user likes until it starts; the answer puts the user&#39;s name in the attendee list, which everyone who sees the event reads, and keeps the event visible to the user. A new &#x60;going&#x60; to an event whose attendees who answer &#x60;going&#x60; reached its capacity is a 409 &#x60;/api/v1/problems/event-full&#x60;; &#x60;maybe&#x60; and &#x60;declined&#x60; are never refused. &#x60;declined&#x60; ends an approval. A 409 &#x60;/api/v1/problems/event-closed&#x60; for an event that started or was canceled, and a 409 &#x60;/api/v1/problems/event-unscheduled&#x60; for an event whose date poll was not confirmed yet. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val eventRSVP : EventRSVP =  // EventRSVP | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.answerEvent(id, eventRSVP, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#answerEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#answerEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **eventRSVP** | [**EventRSVP**](EventRSVP.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="approveEventAttendee"></a>
# **approveEventAttendee**
> EventAttendee approveEventAttendee(id, accountId, idempotencyKey)

Approve an attendee of an event

Approves the answer of an attendee who answers &#x60;going&#x60; or &#x60;maybe&#x60;, by the organizer or a co-organizer: the attendee sees the &#x60;physical&#x60; and &#x60;url&#x60; places of an event that shows them to the approved. An approval ends when the attendee answers &#x60;declined&#x60;. A 422 &#x60;/api/v1/problems/event-not-attending&#x60; for a user who does not answer &#x60;going&#x60; or &#x60;maybe&#x60;. A 422 &#x60;/api/v1/problems/event-attendee-organizes&#x60; for the organizer or a co-organizer, who always sees the places. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventAttendee = apiInstance.approveEventAttendee(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#approveEventAttendee")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#approveEventAttendee")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **accountId** | **java.util.UUID**| The ID of the account. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventAttendee**](EventAttendee.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="assignEventCoorganizer"></a>
# **assignEventCoorganizer**
> Event assignEventCoorganizer(id, accountId, idempotencyKey)

Name a co-organizer of an event

Names a local user a co-organizer of the event, by its organizer: a co-organizer changes the event, invites, and approves attendees, and sees every place. An event has at most 3 co-organizers (a 409 &#x60;/api/v1/problems/event-too-many-coorganizers&#x60;). A 422 &#x60;/api/v1/problems/event-invalid-coorganizer&#x60; for a user who is not a local, active user, is the organizer, or is in a block relation with the organizer or a co-organizer. A 403 &#x60;/api/v1/problems/event-organizer-only&#x60; for a co-organizer. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.assignEventCoorganizer(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#assignEventCoorganizer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#assignEventCoorganizer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **accountId** | **java.util.UUID**| The ID of the account. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="cancelEvent"></a>
# **cancelEvent**
> Event cancelEvent(id, idempotencyKey, eventCancelRequest)

Cancel an event

Cancels the event, by its organizer: it stays readable with its cancellation, and takes no more answers or changes. A 403 &#x60;/api/v1/problems/event-organizer-only&#x60; for a co-organizer. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for an event canceled already. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val eventCancelRequest : EventCancelRequest =  // EventCancelRequest | 
try {
    val result : Event = apiInstance.cancelEvent(id, idempotencyKey, eventCancelRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#cancelEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#cancelEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **eventCancelRequest** | [**EventCancelRequest**](EventCancelRequest.md)|  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="confirmEventCandidate"></a>
# **confirmEventCandidate**
> Event confirmEventCandidate(id, candidateId, idempotencyKey)

Confirm a candidate date of an event

Fixes the date of the event to a candidate, by the organizer or a co-organizer: the event is &#x60;scheduled&#x60;, its poll takes no more answers, the reminders follow the date, and the users who answered the poll and the invited users are notified (&#x60;event_schedule_confirmed&#x60;). A later change of the date is a &#x60;PATCH&#x60; of the event. A candidate that has started is a 422. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event, and a 409 &#x60;/api/v1/problems/event-scheduled&#x60; for an event whose date was confirmed. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val candidateId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the candidate date.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.confirmEventCandidate(id, candidateId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#confirmEventCandidate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#confirmEventCandidate")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **candidateId** | **java.util.UUID**| The ID of the candidate date. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="createEvent"></a>
# **createEvent**
> Event createEvent(eventCreate, idempotencyKey)

Create an event

Creates an event that the user organizes, with a fixed date or with the candidate dates of a date poll (&#x60;candidates&#x60;; the event is &#x60;unscheduled&#x60; until the organizers confirm one). Both, or neither, are a 422. The user must be a local, unrestricted user who may create events (the instance setting &#x60;event_creators&#x60;, or the &#x60;create_events&#x60; permission): a 403 &#x60;/api/v1/problems/event-not-eligible&#x60; otherwise. A &#x60;voice&#x60; place needs the voice chat on and a user who may host its rooms: a 422 &#x60;/api/v1/problems/event-voice-unavailable&#x60; otherwise. A user creates at most 10 events an hour (a 429 with &#x60;Retry-After&#x60;). Invalid settings, a URL other than &#x60;http&#x60; or &#x60;https&#x60;, and a date in the past are a 422. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val eventCreate : EventCreate =  // EventCreate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.createEvent(eventCreate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#createEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#createEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **eventCreate** | [**EventCreate**](EventCreate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="getEvent"></a>
# **getEvent**
> Event getEvent(id)

Get an event

The event, as the user sees it: the places that the user does not see are left out. A 404 for an event that the user does not see. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
try {
    val result : Event = apiInstance.getEvent(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#getEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#getEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getEventCalendar"></a>
# **getEventCalendar**
> kotlin.String getEventCalendar(id)

Get an event as an iCalendar file

The event as an iCalendar (RFC 5545) file of one &#x60;VEVENT&#x60; with &#x60;METHOD:PUBLISH&#x60;, to add to the user&#39;s own calendar. &#x60;UID&#x60; is &#x60;&lt;event id&gt;@&lt;host&gt;&#x60;, so that importing the file again updates the same entry; &#x60;DTSTART&#x60; and &#x60;DTEND&#x60; are in UTC, the same instants in every time zone; &#x60;SEQUENCE&#x60; is the number of revisions of the settings; a canceled event has &#x60;STATUS:CANCELLED&#x60;; and &#x60;URL&#x60; is the event&#39;s page. &#x60;LOCATION&#x60; names only the places that the user sees: a hidden place never appears in the file. A 404 for an event that the user does not see, and a 409 &#x60;/api/v1/problems/event-unscheduled&#x60; for an event without a date yet. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
try {
    val result : kotlin.String = apiInstance.getEventCalendar(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#getEventCalendar")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#getEventCalendar")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |

### Return type

**kotlin.String**

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/calendar, application/problem+json

<a id="inviteToEvent"></a>
# **inviteToEvent**
> EventInvitation inviteToEvent(id, eventInvitationCreate, idempotencyKey)

Invite a user to an event

Invites a local user to the event, by the organizer or a co-organizer: the user sees the event from now on, whatever its visibility. A user is invited to an event once (a 409 &#x60;/api/v1/problems/event-already-invited&#x60;, also after a revocation). A 422 &#x60;/api/v1/problems/event-invalid-invitee&#x60; for a user who is not a local, active user, organizes the event, is in a block relation with the organizer or a co-organizer, or whom the user may not see (moderation hides the profile); nothing is stored then. An event sends at most 100 invitations an hour (a 429 with &#x60;Retry-After&#x60;). A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val eventInvitationCreate : EventInvitationCreate =  // EventInvitationCreate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventInvitation = apiInstance.inviteToEvent(id, eventInvitationCreate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#inviteToEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#inviteToEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **eventInvitationCreate** | [**EventInvitationCreate**](EventInvitationCreate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventInvitation**](EventInvitation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listEventAttendees"></a>
# **listEventAttendees**
> EventAttendeePage listEventAttendees(id, cursor, limit)

List the attendees of an event

The attendees who answer &#x60;going&#x60; or &#x60;maybe&#x60;, in the order of their answers, for everyone who sees the event. Accounts that block the user, are deactivated or suspended, or whose profile moderation hides are left out, so a page may hold fewer than &#x60;limit&#x60; items before the last. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : EventAttendeePage = apiInstance.listEventAttendees(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEventAttendees")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEventAttendees")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**EventAttendeePage**](EventAttendeePage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listEventBoard"></a>
# **listEventBoard**
> EventBoardPage listEventBoard(id, cursor, limit)

List the posts of the board of an event

The posts of the event&#39;s board, newest first, for the users who read it: the organizer, the co-organizers, and the users who see the event and answer &#x60;going&#x60; or &#x60;maybe&#x60;. A 403 &#x60;/api/v1/problems/event-board-not-reader&#x60; for a user who sees the event but does not read its board. A post is a status that never leaves its event: it is not federated, and no timeline, search, profile, list, or conversation shows it. Its readers fetch, favourite, react to, and vote in it by its ID with the operations of statuses, and its author edits and deletes it there too; it is not boosted, quoted, bookmarked, or pinned (a 404, as a status the user does not see). A post whose author and the user block each other is left out. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : EventBoardPage = apiInstance.listEventBoard(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEventBoard")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEventBoard")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**EventBoardPage**](EventBoardPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listEventCandidateVotes"></a>
# **listEventCandidateVotes**
> EventBallotPage listEventCandidateVotes(id, candidateId, vote, cursor, limit)

List the answers to a candidate date of an event

Who answered a candidate date and how, in the order of the users&#39; IDs, for everyone who sees the event. Accounts that block the user, are deactivated or suspended, or whose profile moderation hides are left out, so a page may hold fewer than &#x60;limit&#x60; items before the last. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val candidateId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the candidate date.
val vote : EventVote =  // EventVote | Only the answers of this kind; every answer when omitted.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : EventBallotPage = apiInstance.listEventCandidateVotes(id, candidateId, vote, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEventCandidateVotes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEventCandidateVotes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **candidateId** | **java.util.UUID**| The ID of the candidate date. | |
| **vote** | [**EventVote**](.md)| Only the answers of this kind; every answer when omitted. | [optional] [enum: yes, maybe, no] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**EventBallotPage**](EventBallotPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listEventCandidates"></a>
# **listEventCandidates**
> EventCandidateList listEventCandidates(id)

List the candidate dates of an event

The candidate dates of the event&#39;s date poll that were not withdrawn, the earliest start first, with how many users answered each &#x60;yes&#x60;, &#x60;maybe&#x60;, or &#x60;no&#x60; and the user&#39;s own answer, for everyone who sees the event. An event created with a date has none. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
try {
    val result : EventCandidateList = apiInstance.listEventCandidates(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEventCandidates")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEventCandidates")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |

### Return type

[**EventCandidateList**](EventCandidateList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listEventInvitations"></a>
# **listEventInvitations**
> EventInvitationPage listEventInvitations(id, cursor, limit)

List the invitations of an event

The invitations of the event that were not revoked, oldest first, for the organizer and the co-organizers. Accounts that block the user, are deactivated or suspended, or whose profile moderation hides are left out, so a page may hold fewer than &#x60;limit&#x60; items before the last. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : EventInvitationPage = apiInstance.listEventInvitations(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEventInvitations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEventInvitations")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**EventInvitationPage**](EventInvitationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listEvents"></a>
# **listEvents**
> EventPage listEvents(organizerId, cursor, limit)

List the upcoming events

The events that the user sees and that have not ended, the nearest start first; with &#x60;organizer_id&#x60;, only those that the account organizes. A page may hold fewer events than &#x60;limit&#x60; and still have a &#x60;next_cursor&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val organizerId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | An account whose organized events to list, such as for its profile.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : EventPage = apiInstance.listEvents(organizerId, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#listEvents")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#listEvents")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **organizerId** | **java.util.UUID**| An account whose organized events to list, such as for its profile. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**EventPage**](EventPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="postToEventBoard"></a>
# **postToEventBoard**
> Status postToEventBoard(id, eventBoardPostCreate, idempotencyKey)

Post on the board of an event

Posts a status on the event&#39;s board (201), by a user whom the event&#39;s &#x60;board_writers&#x60; let post, while the board is not read-only. A 403 &#x60;/api/v1/problems/event-board-not-reader&#x60; for a user who sees the event but does not read its board, a 403 &#x60;/api/v1/problems/event-board-not-writer&#x60; for a reader whom &#x60;board_writers&#x60; leave out, and a 409 &#x60;/api/v1/problems/event-board-closed&#x60; once the event is canceled, and from 7 days after its end. The post is checked as &#x60;createStatus&#x60; checks a new status, and retrying with the same &#x60;Idempotency-Key&#x60; returns the status posted with it. A reply replies to a post of the same board (a 422 otherwise). A mention notifies only the mentioned users who read the board. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val eventBoardPostCreate : EventBoardPostCreate =  // EventBoardPostCreate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Status = apiInstance.postToEventBoard(id, eventBoardPostCreate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#postToEventBoard")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#postToEventBoard")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **eventBoardPostCreate** | [**EventBoardPostCreate**](EventBoardPostCreate.md)|  | |
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

<a id="removeEventCoorganizer"></a>
# **removeEventCoorganizer**
> Event removeEventCoorganizer(id, accountId, idempotencyKey)

Remove a co-organizer of an event

Removes a co-organizer of the event, by its organizer. A 403 &#x60;/api/v1/problems/event-organizer-only&#x60; for a co-organizer. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.removeEventCoorganizer(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#removeEventCoorganizer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#removeEventCoorganizer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **accountId** | **java.util.UUID**| The ID of the account. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeEventAttendeeApproval"></a>
# **revokeEventAttendeeApproval**
> EventAttendee revokeEventAttendeeApproval(id, accountId, idempotencyKey)

Revoke the approval of an attendee of an event

Revokes the approval of an attendee, by the organizer or a co-organizer. A 404 for a user who did not answer. A 422 &#x60;/api/v1/problems/event-attendee-organizes&#x60; for the organizer or a co-organizer. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventAttendee = apiInstance.revokeEventAttendeeApproval(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#revokeEventAttendeeApproval")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#revokeEventAttendeeApproval")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **accountId** | **java.util.UUID**| The ID of the account. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventAttendee**](EventAttendee.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeEventInvitation"></a>
# **revokeEventInvitation**
> revokeEventInvitation(id, accountId, idempotencyKey)

Revoke an invitation to an event

Revokes the invitation of a user, by the organizer or a co-organizer: the user sees the event only as its visibility allows, or while the user&#39;s answer stands. A 404 for a user who was not invited. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeEventInvitation(id, accountId, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#revokeEventInvitation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#revokeEventInvitation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **accountId** | **java.util.UUID**| The ID of the account. | |
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

<a id="updateEvent"></a>
# **updateEvent**
> Event updateEvent(id, eventUpdate, idempotencyKey)

Change an event

Changes the settings or the date of the event, by its organizer or a co-organizer; a date on an &#x60;unscheduled&#x60; event ends its date poll, as a confirmation does. Only the organizer changes the visibility and &#x60;board_writers&#x60; (a 403 &#x60;/api/v1/problems/event-organizer-only&#x60;). Adding a &#x60;voice&#x60; place needs the organizer to be able to host rooms (a 422 &#x60;/api/v1/problems/event-voice-unavailable&#x60;). A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. Invalid settings are a 422. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val eventUpdate : EventUpdate =  // EventUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Event = apiInstance.updateEvent(id, eventUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#updateEvent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#updateEvent")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **eventUpdate** | [**EventUpdate**](EventUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Event**](Event.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="voteEventCandidate"></a>
# **voteEventCandidate**
> EventCandidate voteEventCandidate(id, candidateId, eventVoteUpdate, idempotencyKey)

Answer a candidate date of an event

Answers a candidate date &#x60;yes&#x60;, &#x60;maybe&#x60;, or &#x60;no&#x60;, as often as the user likes until the organizers confirm a date. Everyone who sees the event may answer; the answer shows the user&#39;s name to everyone who sees the event and keeps the event visible to the user. A 409 &#x60;/api/v1/problems/event-scheduled&#x60; once the date was confirmed, and a 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val candidateId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the candidate date.
val eventVoteUpdate : EventVoteUpdate =  // EventVoteUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : EventCandidate = apiInstance.voteEventCandidate(id, candidateId, eventVoteUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#voteEventCandidate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#voteEventCandidate")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **candidateId** | **java.util.UUID**| The ID of the candidate date. | |
| **eventVoteUpdate** | [**EventVoteUpdate**](EventVoteUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**EventCandidate**](EventCandidate.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="withdrawEventCandidate"></a>
# **withdrawEventCandidate**
> withdrawEventCandidate(id, candidateId, idempotencyKey)

Withdraw a candidate date of an event

Withdraws a candidate date of the event&#39;s date poll, by the organizer or a co-organizer. The users who answered it still see the event. A 404 for a candidate withdrawn already. A 409 &#x60;/api/v1/problems/event-last-candidate&#x60; for the last candidate of the poll. A 403 &#x60;/api/v1/problems/event-not-organizer&#x60; for a user who neither organizes nor co-organizes the event. A 409 &#x60;/api/v1/problems/event-closed&#x60; for a canceled event, and a 409 &#x60;/api/v1/problems/event-scheduled&#x60; for an event whose date was confirmed. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = EventsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val candidateId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the candidate date.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.withdrawEventCandidate(id, candidateId, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling EventsApi#withdrawEventCandidate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EventsApi#withdrawEventCandidate")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **candidateId** | **java.util.UUID**| The ID of the candidate date. | |
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

