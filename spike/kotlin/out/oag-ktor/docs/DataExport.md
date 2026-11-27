
# DataExport

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **state** | [**inline**](#State) | &#x60;pending&#x60; while the archive is being built, &#x60;ready&#x60; while it can be downloaded, &#x60;failed&#x60; when it could not be built (request another one).  |  |
| **requestedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the user asked for the export. |  |
| **completedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the build ended; omitted while &#x60;pending&#x60;. |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the export and its archive are removed; omitted while &#x60;pending&#x60;. |  [optional] |
| **fileSize** | **kotlin.Long** | The size of the archive in bytes; only for &#x60;ready&#x60;. |  [optional] |


<a id="State"></a>
## Enum: state
| Name | Value |
| ---- | ----- |
| state | pending, ready, failed |



