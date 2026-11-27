
# Conversation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **accounts** | [**kotlin.collections.List&lt;Account&gt;**](Account.md) | The other accounts of the direct posts that the user sent or received in the conversation: their authors and the accounts they mention.  |  |
| **lastStatus** | [**Status**](Status.md) | The newest direct post of the conversation that the user may see. A post of an account that the user blocks, is blocked by, or mutes, or of a suspended account, is skipped for the one before it.  |  |
| **unread** | **kotlin.Boolean** | Whether a direct post by another account is newer than the point up to which the user read the conversation (&#x60;markConversationRead&#x60;), on any device.  |  |



