
# AdminServiceClosure

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **state** | [**ServiceClosureState**](ServiceClosureState.md) |  |  |
| **shortNotice** | **kotlin.Boolean** | Whether the announcement gives the users less than 30 days from &#x60;announced_at&#x60; to &#x60;closes_at&#x60;, which the admin screen and the CLI warn about: the users may not have the time to export their data and move their accounts. A short notice is allowed.  |  |
| **closesAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the server closes; omitted for &#x60;none&#x60;. |  [optional] |
| **exportUntil** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | Until when the users may export their data after the closure; omitted for &#x60;none&#x60;. |  [optional] |
| **message** | **kotlin.String** | The operator&#39;s message to the users; omitted when there is none. |  [optional] |
| **announcedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the announcement in effect, or its latest change of the dates, was made; omitted for &#x60;none&#x60;. |  [optional] |
| **changedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the state last changed; omitted for &#x60;none&#x60;. |  [optional] |
| **progress** | [**AdminServiceClosureProgress**](AdminServiceClosureProgress.md) |  |  [optional] |



