
# VoiceSignal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **session** | **kotlin.String** | The &#x60;session&#x60; of the user&#39;s join. |  |
| **to** | **kotlin.String** | The session that receives the signal. |  |
| **kind** | [**VoiceSignalKind**](VoiceSignalKind.md) |  |  |
| **sdp** | **kotlin.String** | The session description of an offer or an answer. |  [optional] |
| **candidate** | **kotlin.String** | An ICE candidate, as the JSON of an &#x60;RTCIceCandidateInit&#x60; with only &#x60;candidate&#x60;, &#x60;sdpMid&#x60;, &#x60;sdpMLineIndex&#x60;, and &#x60;usernameFragment&#x60;; &#x60;candidate&#x60; is empty (no more candidates) or a &#x60;candidate:&#x60; attribute on one line.  |  [optional] |



