
# RelationshipSeverance

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**inline**](#Kind) |  |  |
| **followersLost** | **kotlin.Int** | The accounts that followed the user and no longer do. |  |
| **followingLost** | **kotlin.Int** | The accounts that the user followed and no longer does. |  |
| **domain** | **kotlin.String** | The suspended server of a &#x60;domain_suspension&#x60;, or the server whose allow was removed of an &#x60;allowlist_cutoff&#x60;; omitted otherwise.  |  [optional] |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | domain_suspension, account_suspension, allowlist_cutoff |



