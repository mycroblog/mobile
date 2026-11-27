
# EventCandidate

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **yesCount** | **kotlin.Int** |  |  |
| **maybeCount** | **kotlin.Int** |  |  |
| **noCount** | **kotlin.Int** |  |  |
| **confirmed** | **kotlin.Boolean** | Whether the organizers fixed the event&#39;s date to this candidate. |  |
| **vote** | [**EventVote**](EventVote.md) | The user&#39;s own answer; absent before the user answers. |  [optional] |



