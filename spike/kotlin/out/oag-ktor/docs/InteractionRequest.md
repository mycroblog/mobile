
# InteractionRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**InteractionKind**](InteractionKind.md) |  |  |
| **state** | [**inline**](#State) | &#x60;pending&#x60; until the user answers; then &#x60;accepted&#x60; or &#x60;rejected&#x60;.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **account** | [**Account**](Account.md) | The account that interacts. |  |
| **status** | [**Status**](Status.md) | The status of the user that the account interacts with. |  |
| **interactingStatus** | [**Status**](Status.md) | The reply or the quoting status, when the server has it and the user may see it; omitted for a boost, and for a reply that a remote server sends only once it is approved.  |  [optional] |


<a id="State"></a>
## Enum: state
| Name | Value |
| ---- | ----- |
| state | pending, accepted, rejected |



