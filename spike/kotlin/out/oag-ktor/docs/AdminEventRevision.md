
# AdminEventRevision

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **title** | **kotlin.String** |  |  |
| **description** | **kotlin.String** |  |  |
| **visibility** | [**EventVisibility**](EventVisibility.md) |  |  |
| **timeZone** | **kotlin.String** |  |  |
| **placeDisclosure** | [**EventPlaceDisclosure**](EventPlaceDisclosure.md) |  |  |
| **capacity** | **kotlin.Int** | How many attendees may answer going; 0 for no limit. |  |
| **places** | [**kotlin.collections.List&lt;EventPlace&gt;**](EventPlace.md) |  |  |
| **revisedByAccountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |



