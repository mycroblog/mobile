
# VoiceRoomParticipant

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **account** | [**Account**](Account.md) |  |  |
| **role** | [**VoiceRole**](VoiceRole.md) |  |  |
| **hosting** | **kotlin.Boolean** | Whether the participant is the host. |  |
| **cohosting** | **kotlin.Boolean** | Whether the participant is a co-host. The host is never a co-host. |  |
| **handRaised** | **kotlin.Boolean** | Whether the participant raised a hand to speak. |  |
| **blocked** | **kotlin.Boolean** | Whether the user blocks the participant or the participant blocks the user. |  |
| **session** | **kotlin.String** | The live session of the participant, in the &#x60;state&#x60; events of a room stream only; the sessions connect to each other with signals.  |  [optional] |



