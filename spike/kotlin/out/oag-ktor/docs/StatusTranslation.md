
# StatusTranslation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **language** | **kotlin.String** | The language of this answer, as a BCP 47 tag. |  |
| **sourceLanguage** | **kotlin.String** | The language translated from, which a provider may have detected; &#x60;und&#x60; when it is not known. |  |
| **content** | **kotlin.String** | The text as sanitized HTML, with the mentions, hashtags, links, custom emoji, and code of the post unchanged. |  |
| **spoilerText** | **kotlin.String** | The content warning, as plain text; empty when the post has none. |  |
| **mediaAttachments** | [**kotlin.collections.List&lt;StatusTranslationAttachment&gt;**](StatusTranslationAttachment.md) | The alt text of the media that has any, in order. |  |
| **pollOptions** | **kotlin.collections.List&lt;kotlin.String&gt;** | The titles of the poll&#39;s options, in the order of the options; empty when the post has no poll. |  |
| **provider** | [**TranslationProvider**](TranslationProvider.md) |  |  [optional] |



