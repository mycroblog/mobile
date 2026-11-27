
# DraftRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **visibility** | [**Visibility**](Visibility.md) |  |  |
| **text** | **kotlin.String** | The plain text; omitted or empty while there is none. |  [optional] |
| **spoilerText** | **kotlin.String** | The content warning; omitted or empty for none. It counts toward &#x60;max_post_characters&#x60;. |  [optional] |
| **sensitive** | **kotlin.Boolean** | Whether the media are sensitive. |  [optional] |
| **language** | **kotlin.String** | A BCP 47 language tag; omitted means the user&#39;s default post language. |  [optional] |
| **inReplyToId** | [**java.util.UUID**](java.util.UUID.md) | The post to reply to, checked when the draft is scheduled and published. |  [optional] |
| **quotedStatusId** | [**java.util.UUID**](java.util.UUID.md) | The post to quote, checked when the draft is scheduled and published. |  [optional] |
| **interactionPolicy** | [**InteractionPolicyChange**](InteractionPolicyChange.md) | Who may reply to, boost, and quote the post; the kinds left out take the user&#39;s &#x60;default_interaction_policy&#x60; when the draft is published.  |  [optional] |
| **media** | [**kotlin.collections.List&lt;StatusMediaItem&gt;**](StatusMediaItem.md) | The media to attach, in order: the user&#39;s own uploads, processed or not. A draft with a &#x60;poll&#x60; attaches none.  |  [optional] |
| **poll** | [**DraftPoll**](DraftPoll.md) |  |  [optional] |
| **article** | [**DraftArticleInput**](DraftArticleInput.md) |  |  [optional] |



