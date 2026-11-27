
# CreateAccountActionRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**AdminAccountActionKind**](AdminAccountActionKind.md) |  |  |
| **reasonCategory** | [**AdminAccountActionCategory**](AdminAccountActionCategory.md) |  |  [optional] |
| **publicReason** | **kotlin.String** | The reason shown to the account. |  [optional] |
| **privateNote** | **kotlin.String** | A note for the moderators only. |  [optional] |
| **reportId** | [**java.util.UUID**](java.util.UUID.md) | The report of the account that the action follows from. The action resolves an open report as &#x60;actioned&#x60; in the same operation; a resolved report stays as it is.  |  [optional] |
| **notifyTarget** | **kotlin.Boolean** | Whether a local account is told of the action; ignored for a remote one. |  [optional] |
| **expiresIn** | **kotlin.Int** | The seconds the action lasts, at most ten years (315360000); omitted, it lasts until it is lifted. A &#x60;ban&#x60; and the actions on posts take none.  |  [optional] |
| **statusIds** | [**kotlin.collections.Set&lt;java.util.UUID&gt;**](java.util.UUID.md) | The posts of the account that &#x60;delete_statuses&#x60; or &#x60;mark_statuses_sensitive&#x60; acts on; required for them and taken by no other kind.  |  [optional] |
| **blockEmail** | **kotlin.Boolean** | Whether a &#x60;ban&#x60; also blocks sign-ups with the email address of the account&#39;s user (a &#x60;reject&#x60; sign-up block of kind &#x60;email_hash&#x60;); only a &#x60;ban&#x60; of a local account takes it, and a user without an email address is a 422. Omitted, nothing is blocked.  |  [optional] |



