
# Draft

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **updatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the draft was last saved. |  |
| **text** | **kotlin.String** | The plain text. |  |
| **spoilerText** | **kotlin.String** | The content warning; empty for none. |  |
| **sensitive** | **kotlin.Boolean** |  |  |
| **visibility** | [**Visibility**](Visibility.md) |  |  |
| **media** | [**kotlin.collections.List&lt;StatusMediaItem&gt;**](StatusMediaItem.md) | The media to attach, in order. |  |
| **language** | **kotlin.String** | A BCP 47 language tag; left out for the user&#39;s default post language. |  [optional] |
| **inReplyToId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **quotedStatusId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **interactionPolicy** | [**InteractionPolicyChange**](InteractionPolicyChange.md) | Who may reply to, boost, and quote the post, of the kinds the user chose. |  [optional] |
| **poll** | [**DraftPoll**](DraftPoll.md) |  |  [optional] |
| **article** | [**DraftArticle**](DraftArticle.md) | What makes the draft that of an article; left out for the draft of a post. |  [optional] |
| **scheduledAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the draft is scheduled to be published; left out while it is not scheduled. |  [optional] |
| **lastSchedule** | [**DraftScheduleOutcome**](DraftScheduleOutcome.md) | How the newest schedule that is over ended; left out when none ended. |  [optional] |



