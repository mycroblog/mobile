
# BillingImportRun

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **outcome** | [**inline**](#Outcome) |  |  |
| **errorKind** | [**inline**](#ErrorKind) | Why it failed: &#x60;permission&#x60; (the credentials may not read the billing data), &#x60;not_enabled&#x60; (the billing data is not enabled), &#x60;not_found&#x60; (the dataset, table, or subscription does not exist), &#x60;throttled&#x60; and &#x60;unavailable&#x60; (retried), &#x60;request_cap&#x60; (the cap of the day or the month is reached; nothing was called), &#x60;bytes_cap&#x60; (the query would scan too much), &#x60;invalid_response&#x60;; &#x60;none&#x60; for a success.  |  |
| **requests** | **kotlin.Int** | The billed requests it made. |  |
| **bytesBilled** | **kotlin.Long** |  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | succeeded, failed |


<a id="ErrorKind"></a>
## Enum: error_kind
| Name | Value |
| ---- | ----- |
| errorKind | none, permission, not_enabled, not_found, throttled, request_cap, bytes_cap, invalid_response, unavailable |



