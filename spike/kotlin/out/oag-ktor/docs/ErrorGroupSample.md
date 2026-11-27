
# ErrorGroupSample

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **type** | **kotlin.String** | The chain of the types of the error, outermost first. |  |
| **message** | **kotlin.String** |  |  |
| **stack** | **kotlin.String** | The stack, in the form of the runtime that reported it, without the arguments of the frames. |  |
| **version** | **kotlin.String** | The version of the server of the occurrence. |  |
| **role** | [**inline**](#Role) | The role of the process that reported it; the web client reports have the role of the server that received them. |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **requestId** | **kotlin.String** | The request ID of the occurrence; omitted when it had none. |  [optional] |
| **traceId** | **kotlin.String** | The trace ID of the occurrence; omitted when it had none. |  [optional] |


<a id="Role"></a>
## Enum: role
| Name | Value |
| ---- | ----- |
| role | all, web, worker |



