
# SignupBlockImportSkipped

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **line** | **kotlin.Int** | The line of the list, from 1. |  |
| **&#x60;value&#x60;** | **kotlin.String** | The value as written on the line. |  |
| **reason** | [**inline**](#Reason) | &#x60;invalid&#x60;: not a valid value for the kind. &#x60;duplicate&#x60;: an earlier line has the same value in canonical form. &#x60;already_blocked&#x60;: a block in effect of the same kind and value with a severity at least as strong covers it.  |  |


<a id="Reason"></a>
## Enum: reason
| Name | Value |
| ---- | ----- |
| reason | invalid, duplicate, already_blocked |



