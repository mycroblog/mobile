
# WebhookCustomEmoji

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **shortcode** | **kotlin.String** | The shortcode, without the colons around it. |  |
| **local** | **kotlin.Boolean** | Whether the emoji is of this instance. |  |
| **state** | [**CustomEmojiState**](CustomEmojiState.md) |  |  |
| **visibleInPicker** | **kotlin.Boolean** | Whether the picker offers the local emoji; always &#x60;true&#x60; for a remote one. |  |
| **aliases** | **kotlin.collections.List&lt;kotlin.String&gt;** | The other names of a local emoji that the picker finds it by. |  |
| **createdByDeleted** | **kotlin.Boolean** | Whether a user made the emoji whose account no longer exists. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the emoji was registered or first seen. |  |
| **updatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When its current image was set, its ActivityPub &#x60;updated&#x60;. |  |
| **operation** | **kotlin.String** | The operation, named as in the audit log: &#x60;custom_emoji_created&#x60;, &#x60;custom_emoji_image_replaced&#x60;, &#x60;custom_emoji_copied&#x60;, &#x60;custom_emoji_updated&#x60;, &#x60;custom_emoji_enabled&#x60;, &#x60;custom_emoji_disabled&#x60;, or &#x60;custom_emoji_deleted&#x60;.  |  |
| **domain** | **kotlin.String** | The domain of a remote emoji; absent for a local one. |  [optional] |
| **uri** | [**java.net.URI**](java.net.URI.md) | The ActivityPub &#x60;id&#x60; of the emoji: &#x60;https://{host}/ap/emojis/{id}&#x60; for a local one, and the recorded one for a remote one; absent when a remote emoji has none.  |  [optional] |
| **url** | [**java.net.URI**](java.net.URI.md) | The URL of the image, animated when the emoji is; absent when the image cannot be shown (such as a remote one from a domain whose media the instance rejects).  |  [optional] |
| **staticUrl** | [**java.net.URI**](java.net.URI.md) | The URL of a still image of the emoji; absent with &#x60;url&#x60;. |  [optional] |
| **category** | **kotlin.String** | The category of a local emoji in the picker; absent for none. |  [optional] |
| **createdBy** | [**Account**](Account.md) | The account of the user who made a local emoji: the requester of an approved request, or the administrator who registered or copied it. Absent for an emoji made with the CLI, a remote one, and one whose creator&#39;s account is deleted (&#x60;created_by_deleted&#x60;).  |  [optional] |



