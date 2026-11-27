
# OwnCustomEmojiPage

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **items** | [**kotlin.collections.List&lt;OwnCustomEmoji&gt;**](OwnCustomEmoji.md) |  |  |
| **used** | **kotlin.Int** | The approved and pending requests to create an emoji that the user submitted this month. |  |
| **pending** | **kotlin.Int** | The part of &#x60;used&#x60; that the user&#39;s pending requests to create an emoji take. |  |
| **resetsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the count resets, the first day of the next month at 00:00 UTC. |  |
| **nextCursor** | **kotlin.String** | An opaque pagination cursor. Clients pass it back unchanged and never parse it. |  [optional] |
| **limit** | **kotlin.Int** | How many requests to create an emoji the user may submit in a month; absent for no limit. |  [optional] |



