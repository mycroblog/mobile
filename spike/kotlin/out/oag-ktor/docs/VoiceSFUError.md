
# VoiceSFUError

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **v** | [**VoiceSFUVersion**](VoiceSFUVersion.md) |  |  |
| **type** | [**inline**](#Type) |  |  |
| **code** | **kotlin.String** | Why: &#x60;invalid_ticket&#x60; (the ticket expired, was used, or is for another node; ask for a new grant), &#x60;invalid_message&#x60; (a message the node does not understand), &#x60;unsupported_version&#x60; (a &#x60;v&#x60; the node does not know), or &#x60;room_unavailable&#x60; (the session or the room is no longer on the node). A client treats an unknown code as &#x60;room_unavailable&#x60;.  |  |
| **message** | **kotlin.String** | A description of the error in English, for logs. |  |


<a id="Type"></a>
## Enum: type
| Name | Value |
| ---- | ----- |
| type | error |



