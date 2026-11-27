
# AnnounceServiceClosureRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **closesAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the server closes. It has no lower bound, so that the server can close at short notice. |  |
| **exportUntil** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | Until when the users may export their data after the closure; not before &#x60;closes_at&#x60;. 30 days after &#x60;closes_at&#x60; when omitted.  |  [optional] |
| **message** | **kotlin.String** | The operator&#39;s message to the users, as plain text of at most 5,000 characters; omitted or empty for none. |  [optional] |



