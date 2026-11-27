
# VoiceRoomSettings

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | 1 to 100 characters on one line. |  |
| **icon** | **kotlin.String** | The emoji shown before the name: one Unicode emoji, or the shortcode of an enabled custom emoji of this server, with or without colons. Omitted or empty for none. Any other value, such as a custom emoji of another server, is refused with &#x60;422&#x60;.  |  [optional] |
| **description** | **kotlin.String** | Up to 500 characters; line breaks are kept. |  [optional] |
| **hashtags** | **kotlin.collections.List&lt;kotlin.String&gt;** | Up to 5 hashtags, with or without the &#x60;#&#x60;, each one a hashtag as in a post. |  [optional] |
| **visibility** | [**VoiceRoomVisibility**](VoiceRoomVisibility.md) | Who sees the room; &#x60;local&#x60; when omitted. |  [optional] |
| **speakers** | [**VoiceRoomSpeakers**](VoiceRoomSpeakers.md) | Who may become a speaker without asking; &#x60;manual&#x60; when omitted. |  [optional] |
| **maxParticipants** | **kotlin.Int** | How many people the room holds, the host included, from 2 to the server&#39;s maximum (&#x60;voice_max_participants&#x60; of &#x60;Instance&#x60;); the server&#39;s maximum when omitted. Any other number is a 422.  |  [optional] |
| **post** | [**VoiceStartPost**](VoiceStartPost.md) |  |  [optional] |



