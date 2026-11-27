
# DraftArticle

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | The title, as plain text; empty while there is none. |  |
| **summary** | **kotlin.String** | The summary, as plain text; empty makes one from the body when the draft is published. |  |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | The hashtags, as the user gave them. |  |
| **images** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The IDs of the user&#39;s own uploads that the body shows as images (&#x60;![alt](media:ID)&#x60;), each once, ordered by ID; the server finds them in the body at each save, and keeps the uploads while the draft names them.  |  |
| **cover** | [**StatusMediaItem**](StatusMediaItem.md) | The cover image; omitted when there is none. |  [optional] |



