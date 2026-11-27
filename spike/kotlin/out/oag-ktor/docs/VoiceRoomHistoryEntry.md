
# VoiceRoomHistoryEntry

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **name** | **kotlin.String** | The name of the room. |  |
| **openedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the room opened. |  |
| **endedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the room ended; omitted while it is open. |  [optional] |
| **endReason** | [**VoiceEndReason**](VoiceEndReason.md) | Why the room ended; omitted while it is open. |  [optional] |



