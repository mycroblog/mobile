
# DataImportSkipped

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **line** | **kotlin.Int** | The line of the file, from 1. |  |
| **&#x60;value&#x60;** | **kotlin.String** | The line as written. |  |
| **reason** | [**inline**](#Reason) | &#x60;invalid&#x60;: the line names no account address (for bookmarks, no post URL; for domain mutes, no domain). &#x60;duplicate&#x60;: an earlier line names the same account, post, or domain.  |  |


<a id="Reason"></a>
## Enum: reason
| Name | Value |
| ---- | ----- |
| reason | invalid, duplicate |



