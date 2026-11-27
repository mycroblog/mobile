# VoiceApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**assignVoiceCohost**](VoiceApi.md#assignVoiceCohost) | **POST** /voice/rooms/{id}/participants/{account_id}/cohost | Make a participant of a voice chat room a co-host |
| [**becomeVoiceListener**](VoiceApi.md#becomeVoiceListener) | **DELETE** /voice/rooms/{id}/speaker | Go back to listening in a voice chat room |
| [**becomeVoiceSpeaker**](VoiceApi.md#becomeVoiceSpeaker) | **POST** /voice/rooms/{id}/speaker | Become a speaker in a voice chat room |
| [**dismissVoiceHand**](VoiceApi.md#dismissVoiceHand) | **DELETE** /voice/rooms/{id}/participants/{account_id}/hand | Dismiss the raised hand of a participant of a voice chat room |
| [**endVoiceRoom**](VoiceApi.md#endVoiceRoom) | **DELETE** /voice/rooms/{id} | End a voice chat room |
| [**getVoiceRoom**](VoiceApi.md#getVoiceRoom) | **GET** /voice/rooms/{id} | Get a voice chat room |
| [**getVoiceRoomSummary**](VoiceApi.md#getVoiceRoomSummary) | **GET** /voice/rooms/{id}/summary | Get the summary of a voice chat room |
| [**grantVoiceSpeaking**](VoiceApi.md#grantVoiceSpeaking) | **POST** /voice/rooms/{id}/participants/{account_id}/speaking_permission | Let a participant of a voice chat room speak |
| [**handOverVoiceRoom**](VoiceApi.md#handOverVoiceRoom) | **POST** /voice/rooms/{id}/host | Hand a voice chat room over to another host |
| [**inviteToVoiceRoom**](VoiceApi.md#inviteToVoiceRoom) | **POST** /voice/rooms/{id}/invitations | Invite a user to a voice chat room |
| [**joinVoiceRoom**](VoiceApi.md#joinVoiceRoom) | **POST** /voice/rooms/{id}/participation | Join a voice chat room |
| [**leaveVoiceRoom**](VoiceApi.md#leaveVoiceRoom) | **DELETE** /voice/rooms/{id}/participation | Leave a voice chat room |
| [**listVoiceHistory**](VoiceApi.md#listVoiceHistory) | **GET** /voice/history | List the voice chat rooms the user hosted |
| [**listVoiceInvitations**](VoiceApi.md#listVoiceInvitations) | **GET** /voice/rooms/{id}/invitations | List the invitations of a voice chat room |
| [**listVoiceRooms**](VoiceApi.md#listVoiceRooms) | **GET** /voice/rooms | List the open voice chat rooms |
| [**lowerVoiceHand**](VoiceApi.md#lowerVoiceHand) | **DELETE** /voice/rooms/{id}/hand | Lower a hand in a voice chat room |
| [**muteVoiceParticipant**](VoiceApi.md#muteVoiceParticipant) | **POST** /voice/rooms/{id}/participants/{account_id}/mute | Mute a participant of a voice chat room |
| [**openVoiceRoom**](VoiceApi.md#openVoiceRoom) | **POST** /voice/rooms | Open a voice chat room |
| [**raiseVoiceHand**](VoiceApi.md#raiseVoiceHand) | **POST** /voice/rooms/{id}/hand | Raise a hand in a voice chat room |
| [**reactInVoiceRoom**](VoiceApi.md#reactInVoiceRoom) | **POST** /voice/rooms/{id}/reactions | React in a voice chat room |
| [**refreshVoiceGrant**](VoiceApi.md#refreshVoiceGrant) | **POST** /voice/rooms/{id}/grant | Get a new grant for a session of a voice chat room |
| [**removeVoiceCohost**](VoiceApi.md#removeVoiceCohost) | **DELETE** /voice/rooms/{id}/participants/{account_id}/cohost | Stop a participant of a voice chat room being a co-host |
| [**removeVoiceParticipant**](VoiceApi.md#removeVoiceParticipant) | **DELETE** /voice/rooms/{id}/participants/{account_id} | Remove a participant from a voice chat room |
| [**revokeVoiceInvitation**](VoiceApi.md#revokeVoiceInvitation) | **DELETE** /voice/rooms/{id}/invitations/{account_id} | Revoke an invitation to a voice chat room |
| [**revokeVoiceSpeaking**](VoiceApi.md#revokeVoiceSpeaking) | **DELETE** /voice/rooms/{id}/participants/{account_id}/speaking_permission | Stop a participant of a voice chat room from speaking |
| [**sendVoiceHeartbeat**](VoiceApi.md#sendVoiceHeartbeat) | **POST** /voice/rooms/{id}/heartbeat | Keep a session of a voice chat room alive |
| [**sendVoiceSignal**](VoiceApi.md#sendVoiceSignal) | **POST** /voice/rooms/{id}/signals | Send a signal to another session of a voice chat room |
| [**setVoiceMic**](VoiceApi.md#setVoiceMic) | **POST** /voice/rooms/{id}/mic | Turn the microphone on or off in a voice chat room |
| [**streamVoiceRoom**](VoiceApi.md#streamVoiceRoom) | **GET** /voice/rooms/{id}/stream | Stream the events of a voice chat room |
| [**updateVoiceRoom**](VoiceApi.md#updateVoiceRoom) | **PATCH** /voice/rooms/{id} | Change the settings of a voice chat room |


<a id="assignVoiceCohost"></a>
# **assignVoiceCohost**
> VoiceRoom assignVoiceCohost(id, accountId, idempotencyKey)

Make a participant of a voice chat room a co-host

Makes a participant a co-host, by the host; a room has at most 3 co-hosts (a 409 &#x60;/api/v1/problems/voice-too-many-cohosts&#x60;). The participants in a block relation, either way, with the new co-host leave the room. A co-host stays a co-host while the participation lasts: one who leaves and joins again is a participant until the host makes them a co-host again. A 403 &#x60;/api/v1/problems/voice-not-host&#x60; for anyone but the host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host, or one in a block relation with the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.assignVoiceCohost(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#assignVoiceCohost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#assignVoiceCohost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="becomeVoiceListener"></a>
# **becomeVoiceListener**
> VoiceRoom becomeVoiceListener(id, idempotencyKey)

Go back to listening in a voice chat room

Makes the user, a speaker in the room, a listener again. A permission to speak stays, so the user may speak again. A listener stays one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.becomeVoiceListener(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#becomeVoiceListener")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#becomeVoiceListener")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="becomeVoiceSpeaker"></a>
# **becomeVoiceSpeaker**
> VoiceRoom becomeVoiceSpeaker(id, idempotencyKey)

Become a speaker in a voice chat room

Makes the user, a listener in the room, a speaker. The host, the co-hosts, the users whom the room&#39;s speakers setting covers, and the users with a permission to speak (a granted raised hand, a grant, or an invitation as a speaker) may; a user whom a moderator limited needs a permission. A 403 &#x60;/api/v1/problems/voice-cannot-speak&#x60; for anyone else, who raises a hand instead. A raised hand goes down. A speaker stays one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.becomeVoiceSpeaker(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#becomeVoiceSpeaker")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#becomeVoiceSpeaker")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="dismissVoiceHand"></a>
# **dismissVoiceHand**
> VoiceRoom dismissVoiceHand(id, accountId, idempotencyKey)

Dismiss the raised hand of a participant of a voice chat room

Turns down a participant&#39;s request to speak, by the host or a co-host. A hand that is down stays down. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.dismissVoiceHand(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#dismissVoiceHand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#dismissVoiceHand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="endVoiceRoom"></a>
# **endVoiceRoom**
> endVoiceRoom(id, idempotencyKey)

End a voice chat room

Ends a room that the user hosts, for every participant. A 403 &#x60;/api/v1/problems/voice-not-host&#x60; for a user who is not the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.endVoiceRoom(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#endVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#endVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
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

<a id="getVoiceRoom"></a>
# **getVoiceRoom**
> VoiceRoom getVoiceRoom(id)

Get a voice chat room

An open room that the user sees. A room that ended or that the user does not see is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
try {
    val result : VoiceRoom = apiInstance.getVoiceRoom(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#getVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#getVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getVoiceRoomSummary"></a>
# **getVoiceRoomSummary**
> VoiceRoomSummary getVoiceRoomSummary(id)

Get the summary of a voice chat room

What the page of a room shows to a visitor who does not see the room: whether it is open or ended, and its name, description, and host only when the server shows the summaries of its rooms and the room is for every local user. It needs no sign-in and is the same for every visitor. An unknown room is a 404, and so is every room while the voice chat is off on the server. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
try {
    val result : VoiceRoomSummary = apiInstance.getVoiceRoomSummary(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#getVoiceRoomSummary")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#getVoiceRoomSummary")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |

### Return type

[**VoiceRoomSummary**](VoiceRoomSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="grantVoiceSpeaking"></a>
# **grantVoiceSpeaking**
> VoiceRoom grantVoiceSpeaking(id, accountId, idempotencyKey)

Let a participant of a voice chat room speak

Gives a participant a permission to speak, by the host or a co-host, with or without a raised hand: a raised hand is granted, and the participant then becomes a speaker. The permission lasts while the room is open, until it is revoked. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.grantVoiceSpeaking(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#grantVoiceSpeaking")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#grantVoiceSpeaking")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="handOverVoiceRoom"></a>
# **handOverVoiceRoom**
> VoiceRoom handOverVoiceRoom(id, voiceHostChoice, idempotencyKey)

Hand a voice chat room over to another host

Makes a participant who may host rooms the host of a room that the user hosts; the user stays in the room. A 403 &#x60;/api/v1/problems/voice-not-host&#x60; for a user who is not the host, and a 422 &#x60;/api/v1/problems/voice-invalid-next-host&#x60; for a participant who may not take over. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceHostChoice : VoiceHostChoice =  // VoiceHostChoice | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.handOverVoiceRoom(id, voiceHostChoice, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#handOverVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#handOverVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceHostChoice** | [**VoiceHostChoice**](VoiceHostChoice.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="inviteToVoiceRoom"></a>
# **inviteToVoiceRoom**
> VoiceRoomInvitation inviteToVoiceRoom(id, voiceRoomInvitationCreate, idempotencyKey)

Invite a user to a voice chat room

Invites a local user to the room as a listener or as a speaker, by the host or a co-host: the user sees the room from now on, and an invitation as a speaker lets the user speak without asking. A user is invited to a room once (a 409 &#x60;/api/v1/problems/voice-already-invited&#x60;, also after a revocation). A 422 &#x60;/api/v1/problems/voice-invalid-invitee&#x60; for a user who is not a local, active user, is the host or the user, is in a block relation with the host or a co-host, or was removed from the room. A room sends at most 100 invitations an hour (a 429 with &#x60;Retry-After&#x60;). A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceRoomInvitationCreate : VoiceRoomInvitationCreate =  // VoiceRoomInvitationCreate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoomInvitation = apiInstance.inviteToVoiceRoom(id, voiceRoomInvitationCreate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#inviteToVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#inviteToVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceRoomInvitationCreate** | [**VoiceRoomInvitationCreate**](VoiceRoomInvitationCreate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoomInvitation**](VoiceRoomInvitation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="joinVoiceRoom"></a>
# **joinVoiceRoom**
> VoiceRoomJoin joinVoiceRoom(id)

Join a voice chat room

Joins a room that the user sees, as a listener, with a new session. A user who is in the room already, from another device, keeps the place with a new session, and the previous session is disconnected. Joining leaves the room the user was in; the host of an open room leaves it first (a 409 &#x60;/api/v1/problems/voice-hosting-elsewhere&#x60;). A 409 &#x60;/api/v1/problems/voice-room-full&#x60; for a room that holds as many people as its &#x60;max_participants&#x60; allows, also after the host lowered it below the people in the room. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
try {
    val result : VoiceRoomJoin = apiInstance.joinVoiceRoom(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#joinVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#joinVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |

### Return type

[**VoiceRoomJoin**](VoiceRoomJoin.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="leaveVoiceRoom"></a>
# **leaveVoiceRoom**
> leaveVoiceRoom(id, session, nextHostId, end, idempotencyKey)

Leave a voice chat room

Leaves a room that the user is in. The host of a room with other participants chooses the next host (&#x60;next_host_id&#x60;, a participant who may host rooms) or ends the room (&#x60;end&#x60;): a 409 &#x60;/api/v1/problems/voice-host-must-choose&#x60; without either, and a 422 &#x60;/api/v1/problems/voice-invalid-next-host&#x60; for a next host who may not take over. The room ends when nobody is left in it. With &#x60;session&#x60;, only that session leaves: a session that no longer holds the user&#39;s place in the room (another device joined since) changes nothing, and the answer is a 204. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val session : kotlin.String = session_example // kotlin.String | The `session` of the join that leaves.
val nextHostId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The participant who becomes the host when the host leaves.
val end : kotlin.Boolean = true // kotlin.Boolean | Whether the host ends the room on leaving.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.leaveVoiceRoom(id, session, nextHostId, end, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#leaveVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#leaveVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **session** | **kotlin.String**| The &#x60;session&#x60; of the join that leaves. | [optional] |
| **nextHostId** | **java.util.UUID**| The participant who becomes the host when the host leaves. | [optional] |
| **end** | **kotlin.Boolean**| Whether the host ends the room on leaving. | [optional] [default to false] |
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

<a id="listVoiceHistory"></a>
# **listVoiceHistory**
> VoiceRoomHistoryPage listVoiceHistory(cursor, limit)

List the voice chat rooms the user hosted

The rooms that the user hosted at some time, from opening them or from taking them over, newest first, with when each opened and ended and why it ended. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : VoiceRoomHistoryPage = apiInstance.listVoiceHistory(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#listVoiceHistory")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#listVoiceHistory")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**VoiceRoomHistoryPage**](VoiceRoomHistoryPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listVoiceInvitations"></a>
# **listVoiceInvitations**
> VoiceRoomInvitationPage listVoiceInvitations(id, cursor, limit)

List the invitations of a voice chat room

Lists the invitations of the room that were not revoked, oldest first, for the host and the co-hosts. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : VoiceRoomInvitationPage = apiInstance.listVoiceInvitations(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#listVoiceInvitations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#listVoiceInvitations")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**VoiceRoomInvitationPage**](VoiceRoomInvitationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listVoiceRooms"></a>
# **listVoiceRooms**
> VoiceRoomPage listVoiceRooms(tag, cursor, limit)

List the open voice chat rooms

The open voice chat rooms that the user sees: the rooms where the user follows the host or a speaker first, then by the most participants. With &#x60;tag&#x60;, only the rooms with that hashtag. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val tag : kotlin.String = tag_example // kotlin.String | A hashtag, with or without the `#`, that the rooms have.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : VoiceRoomPage = apiInstance.listVoiceRooms(tag, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#listVoiceRooms")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#listVoiceRooms")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **tag** | **kotlin.String**| A hashtag, with or without the &#x60;#&#x60;, that the rooms have. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**VoiceRoomPage**](VoiceRoomPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="lowerVoiceHand"></a>
# **lowerVoiceHand**
> VoiceRoom lowerVoiceHand(id, idempotencyKey)

Lower a hand in a voice chat room

Withdraws the user&#39;s request to speak. A hand that is down stays down. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.lowerVoiceHand(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#lowerVoiceHand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#lowerVoiceHand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="muteVoiceParticipant"></a>
# **muteVoiceParticipant**
> muteVoiceParticipant(id, accountId, idempotencyKey)

Mute a participant of a voice chat room

Turns the microphone of a participant off, by the host or a co-host. The participant may turn it on again. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.muteVoiceParticipant(id, accountId, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#muteVoiceParticipant")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#muteVoiceParticipant")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
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

<a id="openVoiceRoom"></a>
# **openVoiceRoom**
> VoiceRoomJoin openVoiceRoom(voiceRoomSettings)

Open a voice chat room

Opens a room that the user hosts and joins the user to it as a speaker, with a new session. The user must be a local, unrestricted user who may start rooms (the instance setting &#x60;voice_room_starters&#x60;, or the &#x60;start_voice_rooms&#x60; permission): a 403 &#x60;/api/v1/problems/voice-not-eligible&#x60; otherwise. A 409 &#x60;/api/v1/problems/voice-too-many-rooms&#x60; while as many rooms are open as the server allows, and a 409 &#x60;/api/v1/problems/voice-hosting-elsewhere&#x60; while the user hosts another open room. Joining leaves the room the user was in. Invalid settings are a 422. With &#x60;post&#x60;, the user&#39;s start post, which links the room, is posted with it: a 422 for a room of the &#x60;following&#x60;, &#x60;mutuals&#x60;, or &#x60;invited&#x60; visibility, which has none, and the errors of posting a status for a post that cannot be posted. The local followers of the user who see the room get a &#x60;voice_room_started&#x60; notification. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val voiceRoomSettings : VoiceRoomSettings =  // VoiceRoomSettings | 
try {
    val result : VoiceRoomJoin = apiInstance.openVoiceRoom(voiceRoomSettings)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#openVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#openVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **voiceRoomSettings** | [**VoiceRoomSettings**](VoiceRoomSettings.md)|  | |

### Return type

[**VoiceRoomJoin**](VoiceRoomJoin.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="raiseVoiceHand"></a>
# **raiseVoiceHand**
> VoiceRoom raiseVoiceHand(id, idempotencyKey)

Raise a hand in a voice chat room

Asks the host and the co-hosts for a permission to speak. A raised hand stays up, and a speaker raises none. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.raiseVoiceHand(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#raiseVoiceHand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#raiseVoiceHand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="reactInVoiceRoom"></a>
# **reactInVoiceRoom**
> reactInVoiceRoom(id, voiceReaction, idempotencyKey)

React in a voice chat room

Sends a reaction of the user, a participant of the room, to the participants, as a &#x60;reaction&#x60; event of their streams; the server records nothing, and the participants in a block relation with the user do not receive it. The emoji is a Unicode emoji or the &#x60;:shortcode:&#x60; of an enabled custom emoji of this server; any other is a 422. A participant sends at most 10 reactions in 10 seconds; past it the answer is 429 with &#x60;Retry-After&#x60;. A 404 for a room the user is not in, and while the voice chat is off on the server. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceReaction : VoiceReaction =  // VoiceReaction | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.reactInVoiceRoom(id, voiceReaction, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#reactInVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#reactInVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceReaction** | [**VoiceReaction**](VoiceReaction.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="refreshVoiceGrant"></a>
# **refreshVoiceGrant**
> VoiceGrant refreshVoiceGrant(id, voiceSession)

Get a new grant for a session of a voice chat room

Returns a new grant for the user&#39;s session, with new credentials of the STUN and TURN servers; the client asks for it 10 minutes before its grant expires, sets the new ICE servers on its connections, and restarts ICE. A session that is not the user&#39;s current session in the room is a 404, and a 503 &#x60;/api/v1/problems/voice-unavailable&#x60; when the server cannot give a grant now. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceSession : VoiceSession =  // VoiceSession | 
try {
    val result : VoiceGrant = apiInstance.refreshVoiceGrant(id, voiceSession)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#refreshVoiceGrant")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#refreshVoiceGrant")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceSession** | [**VoiceSession**](VoiceSession.md)|  | |

### Return type

[**VoiceGrant**](VoiceGrant.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="removeVoiceCohost"></a>
# **removeVoiceCohost**
> VoiceRoom removeVoiceCohost(id, accountId, idempotencyKey)

Stop a participant of a voice chat room being a co-host

Makes a co-host a participant without the role of a co-host, by the host. A 403 &#x60;/api/v1/problems/voice-not-host&#x60; for anyone but the host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.removeVoiceCohost(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#removeVoiceCohost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#removeVoiceCohost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="removeVoiceParticipant"></a>
# **removeVoiceParticipant**
> VoiceRoom removeVoiceParticipant(id, accountId, idempotencyKey)

Remove a participant from a voice chat room

Takes a participant out of the room, by the host or a co-host, and bans the account from the room: it neither sees nor joins the room again. The host and the co-hosts do not remove each other. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host, or a co-host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.removeVoiceParticipant(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#removeVoiceParticipant")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#removeVoiceParticipant")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeVoiceInvitation"></a>
# **revokeVoiceInvitation**
> revokeVoiceInvitation(id, accountId, idempotencyKey)

Revoke an invitation to a voice chat room

Revokes the invitation of a user, by the host or a co-host: the user no longer sees the room unless in it, and the permission to speak of an invitation as a speaker goes too; a participant stays in the room as a speaker or a listener. A 404 for a user without an invitation that was not revoked. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.revokeVoiceInvitation(id, accountId, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#revokeVoiceInvitation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#revokeVoiceInvitation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
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

<a id="revokeVoiceSpeaking"></a>
# **revokeVoiceSpeaking**
> VoiceRoom revokeVoiceSpeaking(id, accountId, idempotencyKey)

Stop a participant of a voice chat room from speaking

Makes a speaker a listener again, by the host or a co-host, and takes back the participant&#39;s permission to speak. A 403 &#x60;/api/v1/problems/voice-not-host-or-cohost&#x60; for a participant who is neither the host nor a co-host, and a 422 &#x60;/api/v1/problems/voice-invalid-participant&#x60; for an account that is not in the room, the user, or the host. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account in the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.revokeVoiceSpeaking(id, accountId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#revokeVoiceSpeaking")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#revokeVoiceSpeaking")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **accountId** | **java.util.UUID**| The ID of the account in the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="sendVoiceHeartbeat"></a>
# **sendVoiceHeartbeat**
> sendVoiceHeartbeat(id, voiceSession, idempotencyKey)

Keep a session of a voice chat room alive

Tells the server that the session is alive; the client sends it every 15 seconds. A session without a heartbeat for 45 seconds leaves the room; the host keeps the room for 2 more minutes, and then the earliest co-host who may host rooms takes over, or the room ends. A session that is not the user&#39;s current session in the room is a 404: the client joins again. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceSession : VoiceSession =  // VoiceSession | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.sendVoiceHeartbeat(id, voiceSession, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#sendVoiceHeartbeat")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#sendVoiceHeartbeat")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceSession** | [**VoiceSession**](VoiceSession.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="sendVoiceSignal"></a>
# **sendVoiceSignal**
> sendVoiceSignal(id, voiceSignal, idempotencyKey)

Send a signal to another session of a voice chat room

Relays a WebRTC signal of a room whose method is &#x60;p2p&#x60; from the user&#39;s session to another live session of the room, which receives it as a &#x60;signal&#x60; event of its stream. Two sessions connect when at least one of them is a speaker; of the two, the session with the greater ID sends the first offer. The server relays only audio: the &#x60;sdp&#x60; of an offer or an answer has &#x60;m&#x3D;audio&#x60; sections only, and a participant who may not speak only receives (&#x60;recvonly&#x60; or &#x60;inactive&#x60;). A signal it does not relay is a 422 with the field that is wrong (&#x60;kind&#x60;, &#x60;to&#x60;, &#x60;sdp&#x60;, or &#x60;candidate&#x60;): a &#x60;to&#x60; that is not a live session of the room that the user&#39;s session connects to, or an &#x60;sdp&#x60; and a &#x60;candidate&#x60; of more than 16 KiB. A session sends at most 20 signals each second; past it the answer is 429 with &#x60;Retry-After&#x60;. A signal that its receiver&#39;s stream does not take within 30 seconds is lost; restart ICE. A session that is not the user&#39;s current session in the room is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceSignal : VoiceSignal =  // VoiceSignal | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.sendVoiceSignal(id, voiceSignal, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#sendVoiceSignal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#sendVoiceSignal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceSignal** | [**VoiceSignal**](VoiceSignal.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="setVoiceMic"></a>
# **setVoiceMic**
> setVoiceMic(id, voiceMic, idempotencyKey)

Turn the microphone on or off in a voice chat room

Tells the participants that the user&#39;s microphone is on or off, as a &#x60;mic&#x60; event of their streams; the server records nothing. Only a speaker turns it on (403 &#x60;/api/v1/problems/voice-cannot-speak&#x60;). After the host or a co-host muted the session, turning it on lifts the mute. A session that is not the user&#39;s current session in the room is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceMic : VoiceMic =  // VoiceMic | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.setVoiceMic(id, voiceMic, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#setVoiceMic")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#setVoiceMic")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceMic** | [**VoiceMic**](VoiceMic.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="streamVoiceRoom"></a>
# **streamVoiceRoom**
> StreamVoiceRoom200Response streamVoiceRoom(id, session)

Stream the events of a voice chat room

A Server-Sent Events stream (&#x60;text/event-stream&#x60;) of a voice chat room for one session of the user in it. Read it with &#x60;fetch&#x60; and send the access token in &#x60;Authorization&#x60;, like every other operation (&#x60;EventSource&#x60; cannot send it). Only the user&#39;s current session in the room, while it sends heartbeats, opens a stream; any other is a 404. A session holds one stream: opening it again ends the one opened before.  The stream starts with a comment line (&#x60;: connected&#x60;) and a &#x60;state&#x60; event, and a comment line (&#x60;: heartbeat&#x60;) arrives every 15 seconds. Opening the stream does not keep the session alive: keep sending its heartbeats. Events:  - &#x60;state&#x60;: the room and its participants changed. &#x60;data&#x60; is a &#x60;VoiceRoom&#x60;; each   participant has its &#x60;session&#x60; while it is alive, &#x60;blocked&#x60; marks the participants in a   block relation with the user, and &#x60;can_speak&#x60; is the user&#39;s. At most one &#x60;state&#x60; arrives   each second, with the newest room. - &#x60;mic&#x60;: a participant turned the microphone on or off. &#x60;data&#x60; is a &#x60;VoiceStreamMic&#x60;. - &#x60;reaction&#x60;: a participant reacted. &#x60;data&#x60; is a &#x60;VoiceStreamReaction&#x60;. The reactions of the   participants in a block relation with the user do not arrive. - &#x60;signal&#x60;: a signal from another session of the room (&#x60;POST /voice/rooms/{id}/signals&#x60;).   &#x60;data&#x60; is a &#x60;VoiceStreamSignal&#x60;. - &#x60;muted&#x60;: the host or a co-host muted the session; turn the microphone off. &#x60;data&#x60; is a   &#x60;VoiceStreamMuted&#x60;. - &#x60;removed&#x60;, &#x60;replaced&#x60;, &#x60;ended&#x60;: the session left the room, the user joined from another   device, or the room ended. &#x60;data&#x60; is a &#x60;VoiceStreamEnd&#x60;, and the stream ends after it.  When the stream ends otherwise, open it again with the same session; the first &#x60;state&#x60; brings the room up to date, and nothing is replayed. While the server cannot receive the events (it is shutting down) the answer is 503 with &#x60;Retry-After&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val session : kotlin.String = session_example // kotlin.String | The `session` of the join.
try {
    val result : StreamVoiceRoom200Response = apiInstance.streamVoiceRoom(id, session)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#streamVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#streamVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **session** | **kotlin.String**| The &#x60;session&#x60; of the join. | |

### Return type

[**StreamVoiceRoom200Response**](StreamVoiceRoom200Response.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/event-stream, application/problem+json

<a id="updateVoiceRoom"></a>
# **updateVoiceRoom**
> VoiceRoom updateVoiceRoom(id, voiceRoomUpdate, idempotencyKey)

Change the settings of a voice chat room

Changes the settings of a room that the user hosts. The participants stay whatever the new settings. A 403 &#x60;/api/v1/problems/voice-not-host&#x60; for a user who is not the host, and a 422 for invalid settings. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = VoiceApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val voiceRoomUpdate : VoiceRoomUpdate =  // VoiceRoomUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : VoiceRoom = apiInstance.updateVoiceRoom(id, voiceRoomUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VoiceApi#updateVoiceRoom")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VoiceApi#updateVoiceRoom")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **voiceRoomUpdate** | [**VoiceRoomUpdate**](VoiceRoomUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**VoiceRoom**](VoiceRoom.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

