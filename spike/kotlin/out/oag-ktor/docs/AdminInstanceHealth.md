
# AdminInstanceHealth

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String** |  |  |
| **state** | [**AdminInstanceState**](AdminInstanceState.md) |  |  |
| **failureDays** | **kotlin.Int** | The number of distinct UTC days of the latest run of failed deliveries. |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the server entered its state; omitted for a server without failures. |  [optional] |
| **lastFailureDay** | [**kotlinx.datetime.LocalDate**](kotlinx.datetime.LocalDate.md) | The latest of those days; omitted without failures. |  [optional] |
| **firstSeenAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When this instance first stored a document of the server; omitted for a server known only from deliveries.  |  [optional] |
| **software** | [**AdminInstanceSoftware**](AdminInstanceSoftware.md) |  |  [optional] |



