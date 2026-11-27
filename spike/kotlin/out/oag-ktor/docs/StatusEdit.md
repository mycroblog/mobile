
# StatusEdit

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **content** | **kotlin.String** | The text of the revision as sanitized HTML; for an article, its body. |  |
| **spoilerText** | **kotlin.String** | The content warning of the revision; empty when there is none. |  |
| **sensitive** | **kotlin.Boolean** | Whether the media of the revision are sensitive. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the revision was made; the first one is when the status was posted. |  |
| **mediaAttachments** | [**kotlin.collections.List&lt;MediaAttachment&gt;**](MediaAttachment.md) | The media the revision attaches, in order. |  |
| **emojis** | [**kotlin.collections.List&lt;CustomEmoji&gt;**](CustomEmoji.md) | The custom emoji of the revision&#39;s text and content warning, as &#x60;Status.emojis&#x60;. |  |
| **article** | [**StatusEditArticle**](StatusEditArticle.md) | The title and summary of the revision of an article; omitted for a post. |  [optional] |



