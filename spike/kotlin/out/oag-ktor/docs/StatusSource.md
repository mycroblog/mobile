
# StatusSource

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **text** | **kotlin.String** | The plain text; for an article, its Markdown body. |  |
| **spoilerText** | **kotlin.String** | The content warning; empty when there is none. |  |
| **language** | **kotlin.String** | The BCP 47 language tag; &#x60;und&#x60; when it is unknown. |  |
| **article** | [**ArticleSource**](ArticleSource.md) | The rest of what the author wrote of an article; omitted for a post. |  [optional] |



