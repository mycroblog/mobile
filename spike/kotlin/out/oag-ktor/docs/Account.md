
# Account

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
| **avatar** | [**MediaVariants**](MediaVariants.md) | The avatar image, square; omitted when the account has none, or its image is not processed yet or could not be processed. Clients show a placeholder instead. For a remote account, every size is its media proxy URL.  |  [optional] |
| **header** | [**MediaVariants**](MediaVariants.md) | The header image; omitted when there is none, as &#x60;avatar&#x60; is. |  [optional] |
| **followersCount** | **kotlin.Int** | The number of accounts that actively follow the account, as far as the instance knows. Present on the account&#39;s own endpoints (&#x60;getAccount&#x60;, &#x60;lookupAccount&#x60;, &#x60;verifyAccountCredentials&#x60;) and left out where accounts are listed or embedded.  |  [optional] |
| **followingCount** | **kotlin.Int** | The number of accounts that the account actively follows; present where &#x60;followers_count&#x60; is. |  [optional] |
| **moved** | [**Account**](Account.md) | The account that the account moved to, when it moved: clients show that it moved, with a link to this one. Present on the account&#39;s own endpoints, where &#x60;followers_count&#x60; is, and without a &#x60;moved&#x60; of its own (moves are not followed in chains).  |  [optional] |
| **unavailable** | **kotlin.Boolean** | True for an account that the user may not see but that a field must name (an event&#39;s &#x60;organizer&#x60;): its &#x60;id&#x60;, &#x60;uri&#x60;, &#x60;domain&#x60;, and &#x60;created_at&#x60; are its own, &#x60;url&#x60; is its &#x60;uri&#x60;, &#x60;locked&#x60; and &#x60;bot&#x60; are false, and its texts and lists are empty. Omitted otherwise.  |  [optional] |
| **voiceRoom** | [**VoiceRoomEntry**](VoiceRoomEntry.md) | The open voice chat room where the account is the host or a speaker, when the user sees it. Present only on &#x60;GET /accounts/{id}&#x60;.  |  [optional] |



