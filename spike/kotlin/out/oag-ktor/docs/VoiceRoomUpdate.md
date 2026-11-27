
# VoiceRoomUpdate

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | 1 to 100 characters on one line. |  [optional] |
| **icon** | **kotlin.String** | The emoji shown before the name, as in &#x60;VoiceRoomSettings&#x60;; empty removes it. |  [optional] |
| **description** | **kotlin.String** | Up to 500 characters; empty for none. |  [optional] |
| **hashtags** | **kotlin.collections.List&lt;kotlin.String&gt;** | Up to 5 hashtags, which replace the room&#39;s; empty for none. |  [optional] |
| **visibility** | [**VoiceRoomVisibility**](VoiceRoomVisibility.md) |  |  [optional] |
| **speakers** | [**VoiceRoomSpeakers**](VoiceRoomSpeakers.md) |  |  [optional] |
| **maxParticipants** | **kotlin.Int** | How many people the room holds, from 2 to the server&#39;s maximum, as in &#x60;VoiceRoomSettings&#x60;. A maximum below the people in the room is accepted: nobody leaves, and joins are refused until fewer people are in it.  |  [optional] |



