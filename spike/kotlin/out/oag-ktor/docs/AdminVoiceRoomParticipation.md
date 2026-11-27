
# AdminVoiceRoomParticipation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **joinedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **leftAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account left; omitted while it takes part. |  [optional] |
| **leaveReason** | [**inline**](#LeaveReason) | Why the account left (&#x60;left&#x60;, &#x60;moved&#x60;, &#x60;disconnected&#x60;, &#x60;removed&#x60;, &#x60;blocked&#x60;, &#x60;suspended&#x60;, &#x60;room_ended&#x60;); omitted while it takes part.  |  [optional] |


<a id="LeaveReason"></a>
## Enum: leave_reason
| Name | Value |
| ---- | ----- |
| leaveReason | left, moved, disconnected, removed, blocked, suspended, room_ended |



