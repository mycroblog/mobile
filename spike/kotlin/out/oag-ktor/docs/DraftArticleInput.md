
# DraftArticleInput

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | The title, as plain text; omitted or empty while there is none. |  [optional] |
| **summary** | **kotlin.String** | The summary of an article, as plain text. Omitted or blank, the server makes one from the start of the body (about 200 characters, cut at the end of a sentence or word). It is a summary, never a content warning.  |  [optional] |
| **cover** | [**StatusMediaItem**](StatusMediaItem.md) | The cover image, one of the user&#39;s own uploads, processed or not. |  [optional] |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | The hashtags, with or without &#x60;#&#x60;; checked when the draft is published. |  [optional] |



