
# EditStatusRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **text** | **kotlin.String** | The plain text; the server links mentions, hashtags, and URLs. |  |
| **spoilerText** | **kotlin.String** | The content warning; omitted or empty for none. It counts toward &#x60;max_post_characters&#x60;. |  [optional] |
| **sensitive** | **kotlin.Boolean** | Whether the media are sensitive. |  [optional] |
| **language** | **kotlin.String** | A BCP 47 language tag; omitted means the user&#39;s default post language. |  [optional] |
| **media** | [**kotlin.collections.List&lt;StatusMediaItem&gt;**](StatusMediaItem.md) | The media to attach, in order. |  [optional] |
| **removeQuote** | **kotlin.Boolean** | Takes the status&#39;s quote away for good. A status keeps the status it quotes otherwise: an edit cannot change it.  |  [optional] |



