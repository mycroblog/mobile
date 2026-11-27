
# VoiceRoomEntry

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **name** | **kotlin.String** | The name of the room. |  |
| **role** | [**inline**](#Role) | Whether the account hosts the room or speaks in it. |  |
| **participantsCount** | **kotlin.Int** | How many people are in the room. |  |
| **icon** | [**VoiceRoomIcon**](VoiceRoomIcon.md) |  |  [optional] |


<a id="Role"></a>
## Enum: role
| Name | Value |
| ---- | ----- |
| role | host, speaker |



