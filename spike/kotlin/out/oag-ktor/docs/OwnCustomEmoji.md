
# OwnCustomEmoji

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **shortcode** | **kotlin.String** | The shortcode, without the colons around it. |  |
| **state** | [**CustomEmojiState**](CustomEmojiState.md) |  |  |
| **visibleInPicker** | **kotlin.Boolean** | Whether the picker offers the emoji. |  |
| **aliases** | **kotlin.collections.List&lt;kotlin.String&gt;** | The other names that the picker finds the emoji by. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the emoji was made. |  |
| **updatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When its current image was set. |  |
| **url** | [**java.net.URI**](java.net.URI.md) | The URL of the image, animated when the emoji is; absent when it cannot be shown. |  [optional] |
| **staticUrl** | [**java.net.URI**](java.net.URI.md) | The URL of a still image of the emoji; absent with &#x60;url&#x60;. |  [optional] |
| **category** | **kotlin.String** | The category of the emoji in the picker; absent for none. |  [optional] |



