
# PaymentReconciliationRun

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **outcome** | [**inline**](#Outcome) | &#x60;succeeded&#x60;; &#x60;partial&#x60; when it used its &#x60;payments_reconcile_max_requests&#x60; and a continuation takes over; &#x60;failed&#x60; when the provider failed.  |  |
| **errorKind** | [**inline**](#ErrorKind) | Why it stopped: &#x60;request_cap&#x60; (a partial attempt), &#x60;permission&#x60; (the API key is wrong or lacks permissions), &#x60;not_found&#x60;, &#x60;invalid_request&#x60;, &#x60;throttled&#x60; and &#x60;unavailable&#x60; (the provider is busy or down; the next reconciliation tries again), &#x60;invalid_signature&#x60;, &#x60;stale_signature&#x60;, &#x60;mode_mismatch&#x60; (the key&#39;s mode differs from the account&#39;s), &#x60;invalid_response&#x60;; &#x60;none&#x60; for a success.  |  |
| **requests** | **kotlin.Int** | The requests it sent to the provider. |  |
| **checkedSubscriptions** | **kotlin.Int** | The subscriptions it read again from the provider. |  |
| **correctedSubscriptions** | **kotlin.Int** | The subscriptions among them whose state it changed here. |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | succeeded, partial, failed |


<a id="ErrorKind"></a>
## Enum: error_kind
| Name | Value |
| ---- | ----- |
| errorKind | none, request_cap, permission, not_found, invalid_request, throttled, unavailable, invalid_signature, stale_signature, mode_mismatch, invalid_response |



