
# ErrorGroupEvent

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**inline**](#Kind) |  |  |
| **actor** | [**inline**](#Actor) |  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **version** | **kotlin.String** | The version that a &#x60;resolved&#x60; or a &#x60;regressed&#x60; names; omitted for the others. |  [optional] |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | resolved, regressed, muted, unmuted |


<a id="Actor"></a>
## Enum: actor
| Name | Value |
| ---- | ----- |
| actor | user, cli, system |



