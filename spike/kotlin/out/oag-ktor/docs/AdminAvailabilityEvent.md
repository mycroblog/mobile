
# AdminAvailabilityEvent

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**inline**](#Kind) |  |  |
| **reason** | [**inline**](#Reason) | &#x60;failure_days&#x60;: deliveries failed on 7 distinct days. &#x60;delivery_succeeded&#x60;: a delivery succeeded. &#x60;inbound_request&#x60;: the server sent a signed request. &#x60;probe_succeeded&#x60;: the daily probe fetched its NodeInfo. &#x60;operator&#x60;: an operator marked it.  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | marked_unavailable, marked_available |


<a id="Reason"></a>
## Enum: reason
| Name | Value |
| ---- | ----- |
| reason | failure_days, delivery_succeeded, inbound_request, probe_succeeded, operator |



