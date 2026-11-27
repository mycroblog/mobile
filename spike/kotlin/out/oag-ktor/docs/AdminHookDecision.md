
# AdminHookDecision

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **hook** | [**HookName**](HookName.md) |  |  |
| **subjectKey** | **kotlin.String** | What identifies the subject for the hook, such as the URI of a received activity. |  |
| **mode** | [**HookDecisionMode**](HookDecisionMode.md) |  |  |
| **outcome** | [**HookOutcome**](HookOutcome.md) |  |  |
| **latencyMs** | **kotlin.Long** | How long the call took, in milliseconds. |  |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **overridable** | **kotlin.Boolean** | Whether a moderator can cancel it here: a verdict in force that stopped or changed something. A &#x60;reject&#x60; of &#x60;local.status&#x60; goes through its appeal instead.  |  |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The account the subject belongs to; absent when there is none. |  [optional] |
| **reason** | [**HookReason**](HookReason.md) |  |  [optional] |
| **note** | **kotlin.String** | The plugin&#39;s short plain-text note for the moderators; absent when there is none. |  [optional] |
| **overriddenAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a moderator canceled the decision; absent while it stands. |  [optional] |
| **restorableUntil** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | For a &#x60;drop&#x60; of &#x60;inbound.status&#x60; or &#x60;inbound.follow&#x60;: until about when cancelling it processes the activity again, which needs the document as it was received. After that, cancelling records the cancellation and brings nothing back. Absent for any other decision: cancelling a &#x60;drop&#x60; of &#x60;inbound.actor&#x60; fetches the account again from its server, which stores its present profile.  |  [optional] |



