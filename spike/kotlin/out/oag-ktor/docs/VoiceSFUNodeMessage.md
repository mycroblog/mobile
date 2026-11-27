
# VoiceSFUNodeMessage

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **v** | [**VoiceSFUVersion**](VoiceSFUVersion.md) |  |  |
| **type** | [**inline**](#Type) |  |  |
| **sdp** | **kotlin.String** | The session description of the offer. |  |
| **tracks** | [**kotlin.collections.List&lt;VoiceSFUTrack&gt;**](VoiceSFUTrack.md) | Which transceiver carries the audio of which speaker. The transceiver that receives the user&#39;s microphone is not listed.  |  |
| **candidate** | **kotlin.String** | The JSON of an &#x60;RTCIceCandidateInit&#x60; with only &#x60;candidate&#x60;, &#x60;sdpMid&#x60;, &#x60;sdpMLineIndex&#x60;, and &#x60;usernameFragment&#x60;; &#x60;candidate&#x60; is empty (no more candidates) or a &#x60;candidate:&#x60; attribute on one line.  |  |
| **participants** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The account IDs of the speakers who make sound; empty when nobody does. |  |
| **code** | **kotlin.String** | Why: &#x60;invalid_ticket&#x60; (the ticket expired, was used, or is for another node; ask for a new grant), &#x60;invalid_message&#x60; (a message the node does not understand), &#x60;unsupported_version&#x60; (a &#x60;v&#x60; the node does not know), or &#x60;room_unavailable&#x60; (the session or the room is no longer on the node). A client treats an unknown code as &#x60;room_unavailable&#x60;.  |  |
| **message** | **kotlin.String** | A description of the error in English, for logs. |  |


<a id="Type"></a>
## Enum: type
| Name | Value |
| ---- | ----- |
| type | offer, candidate, speaking, migrate, error |



