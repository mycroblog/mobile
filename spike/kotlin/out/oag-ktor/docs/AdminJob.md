
# AdminJob

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **type** | **kotlin.String** | The job type, such as &#x60;federation.deliver&#x60;. |  |
| **state** | [**AdminJobState**](AdminJobState.md) |  |  |
| **payload** | **kotlin.String** | The job&#39;s payload, a JSON document as text. |  |
| **attempts** | **kotlin.Int** | The number of runs the job had, the one in progress included. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **idempotencyKey** | **kotlin.String** |  |  [optional] |
| **orderingKey** | **kotlin.String** |  |  [optional] |
| **lastError** | **kotlin.String** | Why the last run failed; omitted for a job whose runs did not fail. |  [optional] |
| **runAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a pending job is due; omitted for a dead job. |  [optional] |
| **worker** | **kotlin.String** | The worker that runs a &#x60;running&#x60; job. |  [optional] |
| **lockedUntil** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the lease of a &#x60;running&#x60; job ends; another worker may run it after. |  [optional] |
| **failedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a dead job failed. |  [optional] |



