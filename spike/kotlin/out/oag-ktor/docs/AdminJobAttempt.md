
# AdminJobAttempt

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **attempt** | **kotlin.Int** | The number of the run, from 1. |  |
| **type** | **kotlin.String** | The job type. |  |
| **outcome** | [**inline**](#Outcome) | &#x60;retry&#x60;: the job runs again. &#x60;dead&#x60;: it failed for good. |  |
| **error** | **kotlin.String** | Why the run failed, at most 2,000 bytes. |  |
| **worker** | **kotlin.String** | The worker that ran it. |  |
| **startedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **finishedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | retry, dead |



