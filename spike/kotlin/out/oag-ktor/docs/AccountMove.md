
# AccountMove

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **target** | [**Account**](Account.md) | The account that the moved account moved to; omitted when it is gone. |  [optional] |
| **outcome** | [**inline**](#Outcome) | What became of the user&#39;s follow of the moved account, for an &#x60;account_moved&#x60; only: &#x60;followed&#x60;, the user follows &#x60;target&#x60; instead; &#x60;requested&#x60;, the user&#39;s follow of &#x60;target&#x60; awaits its approval instead; and the user still follows the moved account when the user blocks &#x60;target&#x60; (&#x60;blocking&#x60;), &#x60;target&#x60; blocks the user (&#x60;blocked_by&#x60;), or the user mutes the server of &#x60;target&#x60; (&#x60;domain_muted&#x60;).  |  [optional] |


<a id="Outcome"></a>
## Enum: outcome
| Name | Value |
| ---- | ----- |
| outcome | followed, requested, blocking, blocked_by, domain_muted |



