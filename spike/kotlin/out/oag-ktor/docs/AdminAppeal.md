
# AdminAppeal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The account that appealed, which the action is on. |  |
| **text** | **kotlin.String** | What the user wrote, as plain text. |  |
| **state** | [**ModerationAppealState**](ModerationAppealState.md) |  |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **action** | [**AdminAccountAction**](AdminAccountAction.md) |  |  |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a moderator decided the appeal; omitted while pending. |  [optional] |
| **decisionReason** | **kotlin.String** | The moderators&#39; explanation of the decision to the user; omitted when there is none. |  [optional] |
| **decidedByAccountId** | [**java.util.UUID**](java.util.UUID.md) | The account of the moderator who decided; omitted while pending and for a decision from the command line.  |  [optional] |



