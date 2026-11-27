
# AdminReportVoiceRoom

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **reportedRevisionId** | [**java.util.UUID**](java.util.UUID.md) | The revision of the settings when the room was reported. |  |
| **openedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisions** | [**kotlin.collections.List&lt;AdminVoiceRoomRevision&gt;**](AdminVoiceRoomRevision.md) | The revisions of the settings, oldest first. |  |
| **participations** | [**kotlin.collections.List&lt;AdminVoiceRoomParticipation&gt;**](AdminVoiceRoomParticipation.md) | Who took part and when, in the order they joined. |  |
| **bans** | [**kotlin.collections.List&lt;AdminVoiceRoomBan&gt;**](AdminVoiceRoomBan.md) | The accounts removed from the room, who may not join it again. |  |
| **endedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the room ended; omitted while it is open. |  [optional] |
| **endReason** | [**VoiceEndReason**](VoiceEndReason.md) | Why the room ended; omitted while it is open. |  [optional] |
| **endedByAccountId** | [**java.util.UUID**](java.util.UUID.md) | Who ended the room; omitted for an automatic end and while it is open. |  [optional] |
| **endNote** | **kotlin.String** | The reason that a moderator gave for ending the room; omitted for none. |  [optional] |



