
# AdminDomainListFetch

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **outcome** | [**AdminDomainListFetchOutcome**](AdminDomainListFetchOutcome.md) |  |  |
| **entryCount** | **kotlin.Int** | The domains the subscription holds after the fetch. |  |
| **fetchedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **httpStatus** | **kotlin.Int** | The status code of the answer; omitted when no answer came. |  [optional] |
| **errorCode** | [**AdminDomainListFetchError**](AdminDomainListFetchError.md) |  |  [optional] |



