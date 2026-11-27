
# CustomEmojiRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**CustomEmojiRequestKind**](CustomEmojiRequestKind.md) |  |  |
| **state** | [**CustomEmojiRequestState**](CustomEmojiRequestState.md) |  |  |
| **shortcode** | **kotlin.String** | The shortcode of the new emoji, without the colons around it, or of the emoji whose image the request replaces.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the request was made. |  |
| **emojiId** | [**java.util.UUID**](java.util.UUID.md) | The emoji of an &#x60;image&#x60; request, and the emoji that an approved &#x60;create&#x60; request made; absent otherwise.  |  [optional] |
| **url** | [**java.net.URI**](java.net.URI.md) | The URL of the requested image, animated when it is; absent once it cannot be shown (the upload of a request that ended goes with the unattached uploads).  |  [optional] |
| **staticUrl** | [**java.net.URI**](java.net.URI.md) | The URL of a still image of the requested image; absent with &#x60;url&#x60;. |  [optional] |
| **reason** | **kotlin.String** | The reason of a rejection that the administrator gave; absent for none. |  [optional] |
| **decidedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the request was approved, rejected, withdrawn, or lapsed; absent while it is pending.  |  [optional] |



