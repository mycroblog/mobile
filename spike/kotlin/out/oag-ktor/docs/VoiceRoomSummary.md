
# VoiceRoomSummary

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **state** | [**inline**](#State) | Whether the room is open or ended. |  |
| **name** | **kotlin.String** | The name of the room. |  [optional] |
| **icon** | [**VoiceRoomIcon**](VoiceRoomIcon.md) | The icon of the room; present only with &#x60;name&#x60;, and omitted when the room has none. |  [optional] |
| **description** | **kotlin.String** | The description of the room, empty for none. |  [optional] |
| **host** | [**Account**](Account.md) |  |  [optional] |


<a id="State"></a>
## Enum: state
| Name | Value |
| ---- | ----- |
| state | open, ended |



