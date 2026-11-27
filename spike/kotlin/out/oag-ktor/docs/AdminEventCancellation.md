
# AdminEventCancellation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **reason** | [**EventCancelReason**](EventCancelReason.md) |  |  |
| **canceledAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **canceledByAccountId** | [**java.util.UUID**](java.util.UUID.md) | Who canceled the event; omitted for an automatic cancellation. |  [optional] |
| **note** | **kotlin.String** | The reason given for the cancellation; omitted for none. |  [optional] |



