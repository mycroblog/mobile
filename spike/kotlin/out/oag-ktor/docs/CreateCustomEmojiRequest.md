
# CreateCustomEmojiRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **shortcode** | **kotlin.String** | 2 to 30 letters (A to Z, a to z), digits, and underscores; case matters. |  |
| **mediaId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **category** | **kotlin.String** | The category in the picker, up to 64 characters; empty for none. |  [optional] |
| **aliases** | **kotlin.collections.List&lt;kotlin.String&gt;** | Other names, each following the rules of a shortcode; at most 10. |  [optional] |
| **visibleInPicker** | **kotlin.Boolean** | Whether the picker offers the emoji. |  [optional] |



