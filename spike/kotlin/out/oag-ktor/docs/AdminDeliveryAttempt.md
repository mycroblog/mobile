
# AdminDeliveryAttempt

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **outcome** | [**inline**](#Outcome) | &#x60;succeeded&#x60;: the server accepted the activity. &#x60;retryable_failure&#x60;: it failed in a way that is tried again. &#x60;permanent_failure&#x60;: the server refused it, and it is not sent again. &#x60;skipped&#x60;: nothing was sent, for the reason in &#x60;error&#x60;.  |  |
| **attemptedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **signatureScheme** | [**inline**](#SignatureScheme) | The signature scheme of the request that got the response; absent without a response. |  [optional] |
| **statusCode** | **kotlin.Int** | The status code of the response; absent without a response. |  [optional] |
| **error** | **kotlin.String** | Why the attempt failed without a response, or why nothing was sent. |  [optional] |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | succeeded, retryable_failure, permanent_failure, skipped |


<a id="SignatureScheme"></a>
## Enum: signature_scheme
| Name | Value |
| ---- | ----- |
| signatureScheme | rfc9421, cavage |



