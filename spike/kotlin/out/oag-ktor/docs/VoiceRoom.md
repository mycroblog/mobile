
# VoiceRoom

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **name** | **kotlin.String** | The name of the room, 1 to 100 characters. |  |
| **description** | **kotlin.String** | The description of the room, up to 500 characters; empty for none. |  |
| **hashtags** | **kotlin.collections.List&lt;kotlin.String&gt;** | The hashtags of the room, without the &#x60;#&#x60;, as the host typed them. |  |
| **visibility** | [**VoiceRoomVisibility**](VoiceRoomVisibility.md) |  |  |
| **speakers** | [**VoiceRoomSpeakers**](VoiceRoomSpeakers.md) |  |  |
| **maxParticipants** | **kotlin.Int** | How many people the room holds, the host included: the host&#39;s maximum, or the server&#39;s (&#x60;voice_max_participants&#x60; of &#x60;Instance&#x60;) when the host set none or the server&#39;s is lower. A join while the room holds as many people is a 409. The room may hold more after the host lowered it; nobody leaves for it.  |  |
| **host** | [**Account**](Account.md) |  |  |
| **openedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the room opened. |  |
| **participantsCount** | **kotlin.Int** | How many people are in the room, the host included. |  |
| **participants** | [**kotlin.collections.List&lt;VoiceRoomParticipant&gt;**](VoiceRoomParticipant.md) |  |  |
| **canSpeak** | **kotlin.Boolean** | Whether the user may become a speaker in the room now without raising a hand (&#x60;POST /voice/rooms/{id}/speaker&#x60;): true for the host and the co-hosts, and for the participants with a permission to speak (a granted raised hand, a grant, or an invitation as a speaker) or whom the room&#39;s speakers setting covers, unless a moderator limited them and they have no permission. False for any other user who is not in the room.  |  |
| **icon** | [**VoiceRoomIcon**](VoiceRoomIcon.md) |  |  [optional] |



