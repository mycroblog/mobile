
# CredentialAccount

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **username** | **kotlin.String** | The username, without the domain; empty for a remote account without a confirmed handle. |  |
| **acct** | **kotlin.String** | The username for local accounts, &#x60;username@domain&#x60; for remote ones; empty for a remote account without a confirmed handle.  |  |
| **domain** | **kotlin.String** | The domain of the account: the domain of its handle, which for local accounts is the instance&#39;s account domain, or the host of its &#x60;uri&#x60; when a remote account has no confirmed handle.  |  |
| **uri** | [**java.net.URI**](java.net.URI.md) | The ActivityPub id of the account&#39;s actor, on the server it belongs to. |  |
| **displayName** | **kotlin.String** | The display name; empty when the account has none. |  |
| **note** | **kotlin.String** | The profile text as sanitized HTML; empty when there is none. |  |
| **url** | [**java.net.URI**](java.net.URI.md) | The account&#39;s profile page: on this instance for a local account, and for a remote one the page its actor declares (&#x60;url&#x60;), or its &#x60;uri&#x60; when it declares none.  |  |
| **locked** | **kotlin.Boolean** | Whether follow requests need the account&#39;s approval. |  |
| **bot** | **kotlin.Boolean** | Whether the account is automated. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account was created. |  |
| **fields** | [**kotlin.collections.List&lt;ProfileField&gt;**](ProfileField.md) | The profile&#39;s metadata fields, in their order; empty when there are none. |  |
| **emojis** | [**kotlin.collections.List&lt;CustomEmoji&gt;**](CustomEmoji.md) | The custom emoji of the display name, the note, and the fields&#39; names and values. Clients show each &#x60;:shortcode:&#x60; of those texts that is in this list as its image, and the rest as text.  |  |
| **badges** | [**kotlin.collections.List&lt;AccountBadge&gt;**](AccountBadge.md) | The role badges that the account shows on its profile, highest role first. Filled on the account&#39;s own endpoints (&#x60;getAccount&#x60;, &#x60;lookupAccount&#x60;, &#x60;verifyAccountCredentials&#x60;) only, and empty where accounts are listed or embedded (posts, notifications, lists of accounts) and for remote accounts. Badges are never federated.  |  |
| **discoverable** | **kotlin.Boolean** | Whether the account is suggested to others and found by the middle of its names in the account search; when &#x60;false&#x60;, the search finds it only by its whole username. New accounts are discoverable.  |  |
| **indexable** | **kotlin.Boolean** | Whether the account&#39;s profile and public posts may be indexed for search: by this instance&#39;s search and, when the instance&#39;s &#x60;search_indexing&#x60; is &#x60;allow&#x60;, by search engines. New accounts take the instance&#39;s default.  |  |
| **aiOptOut** | **kotlin.Boolean** | Whether the account&#39;s pages state that AI may neither train on nor use them, whatever the instance&#39;s &#x60;ai_usage&#x60;. The statement is made by the pages of this instance only; other servers and the media files do not carry it.  |  |
| **feedEnabled** | **kotlin.Boolean** | Whether the owner lets the Atom feed of the account&#39;s public posts be served at the profile&#39;s &#x60;url&#x60; followed by &#x60;.atom&#x60;, and its public posts appear in the Atom feeds of hashtags. It is the owner&#39;s own choice once made, and &#x60;indexable&#x60; until then (&#x60;feed_follows_indexable&#x60;). A restricted or moved account has no feed whatever it says.  |  |
| **feedFollowsIndexable** | **kotlin.Boolean** | Whether the owner has not chosen about the feed yet, so that &#x60;feed_enabled&#x60; follows &#x60;indexable&#x60;. Setting &#x60;feed_enabled&#x60; makes the choice, and a later change of &#x60;indexable&#x60; leaves it as it is.  |  |
| **followsVisibility** | [**FollowsVisibility**](FollowsVisibility.md) |  |  |
| **source** | [**AccountSource**](AccountSource.md) |  |  |
| **posting** | [**PostingLimits**](PostingLimits.md) |  |  |
| **statusesCount** | **kotlin.Long** | The number of the account&#39;s own posts that are not deleted, of every visibility. Only the owner sees it; other accounts do not carry it. A client that creates a post adds one to it, so a count of 1 after the create is the account&#39;s first post.  |  |
| **followRequestsCount** | **kotlin.Long** | The number of follow requests that await the account&#39;s approval. Only the owner sees it. Accepting or rejecting a request takes it out of the count. Like &#x60;followers_count&#x60;, it also counts the requests of deactivated accounts, which &#x60;GET /follow_requests&#x60; leaves out.  |  |
| **permissions** | [**kotlin.collections.List&lt;Permission&gt;**](Permission.md) | The permissions that the user&#39;s roles grant, which the admin operations need; empty for a user without roles. Clients show the admin screens by them; the server checks the permission of each admin operation regardless.  |  |
| **avatar** | [**MediaVariants**](MediaVariants.md) | The avatar image, square; omitted when the account has none, or its image is not processed yet or could not be processed. Clients show a placeholder instead. For a remote account, every size is its media proxy URL.  |  [optional] |
| **header** | [**MediaVariants**](MediaVariants.md) | The header image; omitted when there is none, as &#x60;avatar&#x60; is. |  [optional] |
| **followersCount** | **kotlin.Int** | The number of accounts that actively follow the account, as far as the instance knows. Present on the account&#39;s own endpoints (&#x60;getAccount&#x60;, &#x60;lookupAccount&#x60;, &#x60;verifyAccountCredentials&#x60;) and left out where accounts are listed or embedded.  |  [optional] |
| **followingCount** | **kotlin.Int** | The number of accounts that the account actively follows; present where &#x60;followers_count&#x60; is. |  [optional] |
| **moved** | [**Account**](Account.md) | The account that the account moved to, when it moved: clients show that it moved, with a link to this one. Present on the account&#39;s own endpoints, where &#x60;followers_count&#x60; is, and without a &#x60;moved&#x60; of its own (moves are not followed in chains).  |  [optional] |
| **unavailable** | **kotlin.Boolean** | True for an account that the user may not see but that a field must name (an event&#39;s &#x60;organizer&#x60;): its &#x60;id&#x60;, &#x60;uri&#x60;, &#x60;domain&#x60;, and &#x60;created_at&#x60; are its own, &#x60;url&#x60; is its &#x60;uri&#x60;, &#x60;locked&#x60; and &#x60;bot&#x60; are false, and its texts and lists are empty. Omitted otherwise.  |  [optional] |
| **voiceRoom** | [**VoiceRoomEntry**](VoiceRoomEntry.md) | The open voice chat room where the account is the host or a speaker, when the user sees it. Present only on &#x60;GET /accounts/{id}&#x60;.  |  [optional] |
| **lastMovedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account last moved to another account, also when that move was canceled; omitted when it never moved.  |  [optional] |
| **canMoveAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | From when the account can move again, 30 days after its last move; omitted when it can move now.  |  [optional] |



