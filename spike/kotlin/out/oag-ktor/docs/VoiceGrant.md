
# VoiceGrant

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**inline**](#Kind) |  |  |
| **iceServers** | [**kotlin.collections.List&lt;VoiceIceServer&gt;**](VoiceIceServer.md) | The STUN and TURN servers of the connections. |  |
| **relayOnly** | **kotlin.Boolean** | Whether the client connects through TURN only (&#x60;iceTransportPolicy&#x60; &#x60;relay&#x60;). |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **url** | **kotlin.String** | The &#x60;wss:&#x60; URL of the media node for &#x60;sfu&#x60;; omitted for &#x60;p2p&#x60;. |  [optional] |
| **token** | **kotlin.String** | The single-use ticket of the media node for &#x60;sfu&#x60;; omitted for &#x60;p2p&#x60;. |  [optional] |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | p2p, sfu |



