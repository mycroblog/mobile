
# InstanceClosure

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **state** | [**ServiceClosureState**](ServiceClosureState.md) |  |  |
| **closesAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the server closes; it may have passed already. |  |
| **exportUntil** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | Until when the users may still sign in and export their data after the closure. |  |
| **message** | **kotlin.String** | The operator&#39;s message to the users, as plain text; omitted when there is none. |  [optional] |



