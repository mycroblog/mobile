
# EventBoardPostCreate

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **text** | **kotlin.String** | The plain text; the server links mentions, hashtags, and URLs. |  |
| **spoilerText** | **kotlin.String** | The content warning; omitted or empty for none. It counts toward &#x60;max_post_characters&#x60;. |  [optional] |
| **sensitive** | **kotlin.Boolean** | Whether the media are sensitive. |  [optional] |
| **language** | **kotlin.String** | A BCP 47 language tag; omitted means the user&#39;s default post language. |  [optional] |
| **inReplyToId** | [**java.util.UUID**](java.util.UUID.md) | A post of the same board to reply to; any other status is a 422. |  [optional] |
| **media** | [**kotlin.collections.List&lt;StatusMediaItem&gt;**](StatusMediaItem.md) | The media to attach, in order. |  [optional] |
| **poll** | [**PollInput**](PollInput.md) |  |  [optional] |



