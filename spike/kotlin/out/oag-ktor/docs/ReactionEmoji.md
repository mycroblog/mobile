
# ReactionEmoji

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The emoji as &#x60;listStatusReactions&#x60; takes it: the Unicode emoji, the shortcode of a custom emoji of this server, or &#x60;shortcode@domain&#x60; for a custom emoji of another server.  |  |
| **shortcode** | **kotlin.String** | The shortcode of a custom emoji, without colons; omitted for a Unicode emoji. |  [optional] |
| **domain** | **kotlin.String** | The server of a custom emoji of another server; omitted otherwise. |  [optional] |
| **emoji** | [**CustomEmoji**](CustomEmoji.md) | The custom emoji with its image; omitted for a Unicode emoji and when the image cannot be shown (as &#x60;CustomEmoji&#x60; leaves such an emoji out).  |  [optional] |



