
# ModerationAction

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**AdminAccountActionKind**](AdminAccountActionKind.md) |  |  |
| **kindLabel** | **kotlin.String** | What the action does, in the user&#39;s language. |  |
| **reasonCategory** | [**AdminAccountActionCategory**](AdminAccountActionCategory.md) |  |  |
| **reasonCategoryLabel** | **kotlin.String** | The reason category, in the user&#39;s language. |  |
| **statusIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The posts that an action on posts acted on; empty for an action on the whole account.  |  |
| **state** | [**ModerationActionState**](ModerationActionState.md) |  |  |
| **issuedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **appealable** | **kotlin.Boolean** | Whether the user can appeal the action now (&#x60;POST /moderation/actions/{id}/appeal&#x60;): it has no appeal yet, &#x60;appeal_deadline&#x60; has not passed, and for a &#x60;ban&#x60; the account is not deleted.  |  |
| **publicReason** | **kotlin.String** | The moderators&#39; explanation to the user, as plain text; omitted when there is none.  |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the action ends by itself; omitted when it lasts until it is lifted. |  [optional] |
| **liftedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When a moderator lifted the action; omitted unless it is lifted. |  [optional] |
| **appealDeadline** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The last moment the action can be appealed: the instance&#39;s appeal window from the action, and for a &#x60;ban&#x60; no later than the deletion of the account.  |  [optional] |
| **appeal** | [**ModerationAppeal**](ModerationAppeal.md) | The user&#39;s appeal of the action; omitted when there is none. |  [optional] |



