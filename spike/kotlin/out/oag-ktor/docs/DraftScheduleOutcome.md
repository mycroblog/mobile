
# DraftScheduleOutcome

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **publishAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The time the post was scheduled for. |  |
| **outcome** | [**inline**](#Outcome) | &#x60;canceled&#x60;: the user canceled the schedule or scheduled another time. &#x60;failed&#x60;: the post could not be published at its time, for &#x60;reason&#x60;.  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the schedule ended. |  |
| **reason** | [**DraftFailureReason**](DraftFailureReason.md) |  |  [optional] |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | canceled, failed |



