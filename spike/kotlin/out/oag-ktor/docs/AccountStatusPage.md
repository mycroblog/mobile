
# AccountStatusPage

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **items** | [**kotlin.collections.List&lt;Status&gt;**](Status.md) | The statuses of the page. |  |
| **nextCursor** | **kotlin.String** | An opaque pagination cursor. Clients pass it back unchanged and never parse it. |  [optional] |
| **remoteFetchStarted** | **kotlin.Boolean** | &#x60;true&#x60; when this request started fetching the remote account&#39;s recent public posts from its server. The fetch runs in the background; read the first page again a few seconds later to see what it stored. Absent otherwise, including when a fetch started in the last day.  |  [optional] |



