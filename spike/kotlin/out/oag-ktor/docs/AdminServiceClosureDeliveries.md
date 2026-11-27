
# AdminServiceClosureDeliveries

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **pending** | **kotlin.Int** | Not attempted yet, or failed and attempted again later. |  |
| **succeeded** | **kotlin.Int** | Accepted by the inbox. |  |
| **permanentFailure** | **kotlin.Int** | Refused for good. |  |
| **skipped** | **kotlin.Int** | Not sent, because the domain was suspended or the server unavailable. |  |
| **dead** | **kotlin.Int** | Used up their attempts; an operator may requeue their dead jobs. |  |



