
# ServerHealth

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **status** | [**inline**](#Status) | &#x60;ok&#x60; while the server is healthy, &#x60;unavailable&#x60; while the database is unreachable or its schema is not applied, &#x60;maintenance&#x60; during the maintenance mode.  |  |
| **checkedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When this process last checked the database; absent during the maintenance mode. |  [optional] |


<a id="Status"></a>
## Enum: status
| Name | Value |
| ---- | ----- |
| status | ok, unavailable, maintenance |



