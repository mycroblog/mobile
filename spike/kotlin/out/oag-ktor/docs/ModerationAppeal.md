
# ModerationAppeal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **text** | **kotlin.String** | What the user wrote, as plain text. |  |
| **state** | [**ModerationAppealState**](ModerationAppealState.md) |  |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the moderators decided the appeal; omitted while pending. |  [optional] |
| **decisionReason** | **kotlin.String** | The moderators&#39; explanation of the decision, as plain text; omitted when there is none. |  [optional] |



