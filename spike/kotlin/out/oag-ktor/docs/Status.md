
# Status

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **uri** | [**java.net.URI**](java.net.URI.md) | The ActivityPub ID of the status. |  |
| **account** | [**Account**](Account.md) |  |  |
| **content** | **kotlin.String** | The text as sanitized HTML, with mentions, hashtags, and URLs linked. The HTML of a remote status is what the server&#39;s sanitizer kept of the received document. For an article, its rendered body, or empty in lists (see above); the images in the body of an article from another server load through the server&#39;s media proxy.  |  |
| **spoilerText** | **kotlin.String** | The content warning; empty when there is none. |  |
| **sensitive** | **kotlin.Boolean** | Whether the media are sensitive. |  |
| **visibility** | [**Visibility**](Visibility.md) |  |  |
| **language** | **kotlin.String** | The BCP 47 language tag of the text; &#x60;und&#x60; when it is unknown. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the status was posted. |  |
| **mediaAttachments** | [**kotlin.collections.List&lt;MediaAttachment&gt;**](MediaAttachment.md) | The attached media, in order. |  |
| **mentions** | [**kotlin.collections.List&lt;StatusMention&gt;**](StatusMention.md) | The accounts the text mentions. |  |
| **tags** | [**kotlin.collections.List&lt;StatusTag&gt;**](StatusTag.md) | The hashtags of the text. |  |
| **filtered** | [**kotlin.collections.List&lt;FilterResult&gt;**](FilterResult.md) | The user&#39;s keyword filters with the action &#x60;warn&#x60; that the post matched where it is shown, with the words each matched; empty when none did, and always for an anonymous request. Clients fold a post with any behind the titles of its filters, with a way to show it. A post that matches a filter with the action &#x60;hide&#x60; is left out instead, except the post that &#x60;GET /api/v1/statuses/{id}&#x60; shows, which is folded. In a timeline, a boost carries the filters that the boosted post matched. A quoted post carries its own: a quote that matches folds only the quote, even for &#x60;hide&#x60;. The user&#39;s own posts and boosts, and what they quote, never match.  |  |
| **emojis** | [**kotlin.collections.List&lt;CustomEmoji&gt;**](CustomEmoji.md) | The custom emoji of the text, the content warning, and the poll&#39;s choices. Clients show each &#x60;:shortcode:&#x60; of those texts that is in this list as its image, and the rest as text. Empty for a boost, whose emoji are those of &#x60;boost&#x60;.  |  |
| **repliesCount** | **kotlin.Int** | The number of replies. |  |
| **boostsCount** | **kotlin.Int** | The number of boosts. |  |
| **favouritesCount** | **kotlin.Int** | The number of favourites. |  |
| **reactions** | [**kotlin.collections.List&lt;StatusReaction&gt;**](StatusReaction.md) | The emoji reactions to the status, counted per emoji: the 20 emoji with the most reactions, the most first. The others are seen in &#x60;listStatusReactions&#x60;. Favourites are counted apart, in &#x60;favourites_count&#x60;.  |  |
| **url** | [**java.net.URI**](java.net.URI.md) | The HTML page of the status, on the server it belongs to for a remote status; omitted when it has none.  |  [optional] |
| **article** | [**StatusArticle**](StatusArticle.md) |  |  [optional] |
| **inReplyToId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **inReplyToAccountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **boost** | [**Status**](Status.md) |  |  [optional] |
| **editedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the status was last edited; omitted when it never was. |  [optional] |
| **omittedMediaCount** | **kotlin.Int** | The number of media attachments of a remote status that the server left out: it keeps the first four attachments of a remote post. Clients show that there are more and link to &#x60;url&#x60; (or &#x60;uri&#x60;). Omitted when none were left out.  |  [optional] |
| **followedTags** | **kotlin.collections.List&lt;kotlin.String&gt;** | On the home timeline and its stream, the names (without &#x60;#&#x60;) of the hashtags that the user follows and the post carries, when the post is there only for them: its author is neither the user nor an account the user follows. Omitted everywhere else.  |  [optional] |
| **favourited** | **kotlin.Boolean** | Whether the authenticated user favourited the status. |  [optional] |
| **boosted** | **kotlin.Boolean** | Whether the authenticated user boosts the status. |  [optional] |
| **bookmarked** | **kotlin.Boolean** | Whether the authenticated user bookmarked the status. |  [optional] |
| **muted** | **kotlin.Boolean** | Whether the authenticated user mutes the thread (conversation) of the status, which keeps the user from being notified of what happens in it (&#x60;muteStatusThread&#x60;).  |  [optional] |
| **pinned** | **kotlin.Boolean** | Whether the status is pinned to the author&#39;s profile; given only to the author. |  [optional] |
| **approval** | [**InteractionApprovalState**](InteractionApprovalState.md) | The approval of this reply by the author of the status it replies to, while it is not given; given only to the author of the reply, and omitted for a reply that needs no approval or has it. A &#x60;pending&#x60; reply is shown to its author alone.  |  [optional] |
| **boostApproval** | [**InteractionApprovalState**](InteractionApprovalState.md) | The approval of the authenticated user&#39;s boost of the status by its author, while it is not given; omitted when the user did not ask for one or it is given (then &#x60;boosted&#x60; is true).  |  [optional] |
| **poll** | [**Poll**](Poll.md) |  |  [optional] |
| **card** | [**LinkCard**](LinkCard.md) |  |  [optional] |
| **quote** | [**StatusQuote**](StatusQuote.md) | The status that this status quotes; omitted when it quotes none, and in a &#x60;quoted_status&#x60;.  |  [optional] |
| **eventBoard** | [**StatusEventBoard**](StatusEventBoard.md) | The board of an event that the status is posted on; omitted for any other status. |  [optional] |
| **permissions** | [**InteractionPermissions**](InteractionPermissions.md) | What the authenticated user may do with the status: reply to it, boost it, and quote it. Omitted for anonymous requests. A post of the board of an event (&#x60;event_board&#x60;) is never boosted or quoted, and is replied to (on its board) only by the users who may post there.  |  [optional] |
| **interactionPolicy** | [**InteractionPolicy**](InteractionPolicy.md) | Who may reply to, boost, and quote the status; given only to its author. |  [optional] |



