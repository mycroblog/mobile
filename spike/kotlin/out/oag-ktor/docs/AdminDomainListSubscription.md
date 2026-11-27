
# AdminDomainListSubscription

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **url** | **kotlin.String** | The https URL of the list. |  |
| **format** | [**AdminDomainListFormat**](AdminDomainListFormat.md) |  |  |
| **title** | **kotlin.String** |  |  |
| **mode** | [**AdminDomainListMode**](AdminDomainListMode.md) |  |  |
| **suspendAs** | [**AdminDomainListSuspendAs**](AdminDomainListSuspendAs.md) |  |  |
| **silenceAs** | [**AdminDomainListSilenceAs**](AdminDomainListSilenceAs.md) |  |  |
| **applyRejectMedia** | **kotlin.Boolean** | Whether the &#x60;reject_media&#x60; of a row issues a &#x60;reject_media&#x60; action. |  |
| **applyRejectReports** | **kotlin.Boolean** | Whether the &#x60;reject_reports&#x60; of a row issues a &#x60;reject_reports&#x60; action. |  |
| **publicCommentFromList** | **kotlin.Boolean** | Whether the actions copy the public comment of their row. |  |
| **consecutiveFailures** | **kotlin.Int** | The failed fetches since the last one that did not fail. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **updatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the settings last changed; the creation for settings never changed. |  |
| **lastFetch** | [**AdminDomainListFetch**](AdminDomainListFetch.md) |  |  [optional] |



