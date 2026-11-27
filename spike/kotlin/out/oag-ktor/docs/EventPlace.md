
# EventPlace

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**EventPlaceKind**](EventPlaceKind.md) |  |  |
| **name** | **kotlin.String** | Up to 100 characters on one line. |  [optional] |
| **address** | **kotlin.String** | Up to 500 characters; line breaks are kept. |  [optional] |
| **url** | **kotlin.String** |  |  [optional] |
| **speakers** | [**VoiceRoomSpeakers**](VoiceRoomSpeakers.md) | Who may speak without asking in the room; &#x60;manual&#x60; when omitted. |  [optional] |
| **roomId** | [**java.util.UUID**](java.util.UUID.md) | For a &#x60;voice&#x60; place, the voice chat room that the start of the event&#39;s current date opened (&#x60;GET /voice/rooms/{id}&#x60;), which may have ended since; omitted before the room opens. Ignored in a request.  |  [optional] [readonly] |
| **roomUnavailable** | [**EventRoomUnavailableReason**](EventRoomUnavailableReason.md) | For a &#x60;voice&#x60; place, why the room of the event&#39;s current date could not open at the start; omitted otherwise. Ignored in a request.  |  [optional] [readonly] |



