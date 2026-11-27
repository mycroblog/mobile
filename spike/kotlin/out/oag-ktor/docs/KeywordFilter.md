
# KeywordFilter

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **title** | **kotlin.String** | The name of the filter, 1 to 50 characters, which a folded post shows. |  |
| **action** | [**KeywordFilterAction**](KeywordFilterAction.md) |  |  |
| **contexts** | [**kotlin.collections.List&lt;KeywordFilterContext&gt;**](KeywordFilterContext.md) | Where the filter applies, at least one. |  |
| **keywords** | [**kotlin.collections.List&lt;KeywordFilterKeyword&gt;**](KeywordFilterKeyword.md) | The words of the filter, in the order given; a post matches the filter when it matches any of them. |  |
| **expired** | **kotlin.Boolean** | Whether the filter has expired and applies nowhere. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the user created the filter. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the filter stops applying; omitted for a filter that never does. An expired filter stays, applying nowhere, and applies again when its expiry moves later.  |  [optional] |



