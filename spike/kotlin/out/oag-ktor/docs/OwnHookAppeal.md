
# OwnHookAppeal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **hookDecisionId** | [**java.util.UUID**](java.util.UUID.md) | The decision appealed. |  |
| **state** | [**HookAppealState**](HookAppealState.md) |  |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a moderator upheld or overturned it; absent while it is &#x60;pending&#x60;. |  [optional] |



