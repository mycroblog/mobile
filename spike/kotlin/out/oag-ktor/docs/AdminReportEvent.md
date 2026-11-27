
# AdminReportEvent

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **organizerAccountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **state** | [**EventState**](EventState.md) |  |  |
| **reportedRevisionId** | [**java.util.UUID**](java.util.UUID.md) | The revision of the settings that the reporter saw. |  |
| **revisions** | [**kotlin.collections.List&lt;AdminEventRevision&gt;**](AdminEventRevision.md) | The revisions of the settings, oldest first. |  |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the event starts; omitted while it has no date. |  [optional] |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the event ends; omitted while it has no date. |  [optional] |
| **cancellation** | [**AdminEventCancellation**](AdminEventCancellation.md) | How the event was canceled; omitted while it is not. |  [optional] |



