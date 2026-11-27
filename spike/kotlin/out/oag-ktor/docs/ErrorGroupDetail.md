
# ErrorGroupDetail

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **fingerprint** | **kotlin.String** | The fingerprint of the errors, 32 hexadecimal digits; the same in the logs and the exporters. |  |
| **state** | [**ErrorGroupState**](ErrorGroupState.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the group entered its state; its first occurrence while it has always been open. |  |
| **source** | [**ErrorGroupSource**](ErrorGroupSource.md) |  |  |
| **kind** | [**ErrorGroupKind**](ErrorGroupKind.md) |  |  |
| **&#x60;where&#x60;** | **kotlin.String** | Where the errors happened, a finite name: the surface and the route pattern of an HTTP request, the type of a job, the name of a goroutine, or the route of the web client.  |  |
| **type** | **kotlin.String** | The last type of the chain of the error (a Go type or the name of a JavaScript error). |  |
| **message** | **kotlin.String** | The first line of the scrubbed message of the first occurrence, at most 200 characters. |  |
| **count24h** | **kotlin.Long** | The occurrences in the last 24 hours, by the hour, the current hour included. |  |
| **count7d** | **kotlin.Long** | The occurrences in the last 7 days, by the hour, the current hour included. |  |
| **firstSeenAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **lastSeenAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **firstVersion** | **kotlin.String** | The version of the server (&#x60;service.version&#x60;) of the first occurrence. |  |
| **lastVersion** | **kotlin.String** | The version of the server of the last occurrence. |  |
| **hourlyCounts** | [**kotlin.collections.List&lt;ErrorGroupHourCount&gt;**](ErrorGroupHourCount.md) | The occurrences of each hour that had any, oldest first, for the last 90 days. |  |
| **events** | [**kotlin.collections.List&lt;ErrorGroupEvent&gt;**](ErrorGroupEvent.md) | The changes of the state of the group, oldest first. |  |
| **resolvedVersion** | **kotlin.String** | The version of the server that a &#x60;resolved&#x60; group was resolved with; omitted for the other states. |  [optional] |
| **dailyCounts** | **kotlin.collections.List&lt;kotlin.Long&gt;** | The occurrences of each of the last 7 days, oldest first, each day 24 hours and the last one ending with the current hour: the last is &#x60;count_24h&#x60; and their sum &#x60;count_7d&#x60;. Only the list of the error groups has it.  |  [optional] |
| **sample** | [**ErrorGroupSample**](ErrorGroupSample.md) |  |  [optional] |



