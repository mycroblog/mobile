
# WebhookProblem

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **type** | **kotlin.String** | A URI that identifies the problem type, stable for clients to branch on. It is &#x60;about:blank&#x60; when the problem means no more than its HTTP status.  |  |
| **title** | **kotlin.String** | A short summary of the problem type, localized from &#x60;Accept-Language&#x60;. |  |
| **status** | **kotlin.Int** | The HTTP status code. |  |
| **detail** | **kotlin.String** | An explanation of this occurrence, localized from &#x60;Accept-Language&#x60;. |  [optional] |
| **requestId** | **kotlin.String** | The request ID (the &#x60;X-Request-Id&#x60; response header), for reporting the problem. |  [optional] |
| **&#x60;field&#x60;** | **kotlin.String** | The member of the request that a 422 is about, by its name in the request: a property of the JSON body by its path (&#x60;display_name&#x60;, &#x60;fields[].name&#x60;, &#x60;poll.options[1]&#x60;), a form field, a query parameter, or a header (&#x60;Idempotency-Key&#x60;). &#x60;detail&#x60; then says what is wrong with its value without naming it, for the client to show beside that field. Absent when the problem is not about one member.  |  [optional] |
| **reason** | [**WebhookProblemReason**](WebhookProblemReason.md) |  |  [optional] |
| **missingPermissions** | [**kotlin.collections.List&lt;Permission&gt;**](Permission.md) | The permissions that the events need and the user lacks, for &#x60;missing_permission&#x60;. |  [optional] |



