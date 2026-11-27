
# BrowserSession

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of the browser&#39;s session, which &#x60;DELETE /sessions/{id}&#x60; takes. |  |
| **current** | **kotlin.Boolean** | Whether this is the browser that asks. |  |
| **signedInAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the browser signed in. |  |
| **ip** | **kotlin.String** | The IP address the browser signed in from, not where it is now. Absent when it is unknown or was erased after its retention.  |  [optional] |
| **userAgent** | **kotlin.String** | The &#x60;User-Agent&#x60; of the sign-in. Absent when it is unknown or was erased after its retention.  |  [optional] |
| **lastUsedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the browser was last used, exact to the hour. Absent when no use was recorded. |  [optional] |



