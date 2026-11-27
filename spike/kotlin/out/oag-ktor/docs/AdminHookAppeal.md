
# AdminHookAppeal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **decision** | [**AdminHookDecision**](AdminHookDecision.md) |  |  |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The author. |  |
| **statusText** | **kotlin.String** | The text of the post the author tried to post, as plain text. |  |
| **state** | [**HookAppealState**](HookAppealState.md) |  |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **comment** | **kotlin.String** | What the author adds for the moderators, as plain text; absent when there is none. |  [optional] |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a moderator decided the appeal; absent while it is pending. |  [optional] |



