
# AdminAccountAction

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The account the action is on. |  |
| **kind** | [**AdminAccountActionKind**](AdminAccountActionKind.md) |  |  |
| **reasonCategory** | [**AdminAccountActionCategory**](AdminAccountActionCategory.md) |  |  |
| **issuer** | [**inline**](#Issuer) | &#x60;moderator&#x60;: a moderator or the CLI. |  |
| **notifyTarget** | **kotlin.Boolean** | Whether the account is told of the action. |  |
| **state** | [**ModerationActionState**](ModerationActionState.md) |  |  |
| **issuedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **publicReason** | **kotlin.String** | The reason shown to the account; omitted when there is none. |  [optional] |
| **privateNote** | **kotlin.String** | The moderators&#39; note; omitted when there is none. |  [optional] |
| **reportId** | [**java.util.UUID**](java.util.UUID.md) | The report the action follows from; omitted when there is none. |  [optional] |
| **issuedByAccountId** | [**java.util.UUID**](java.util.UUID.md) | The account of the moderator who took the action; omitted for the CLI and the system.  |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the action ends by itself; omitted for one that lasts until lifted. |  [optional] |
| **liftedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the action was lifted; omitted until it is. |  [optional] |
| **liftReason** | **kotlin.String** | Why the action was lifted; omitted until it is or when no reason was given. |  [optional] |
| **statusIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The posts of the account that &#x60;delete_statuses&#x60; or &#x60;mark_statuses_sensitive&#x60; acts on, in ID order; omitted for the other kinds.  |  [optional] |


<a id="Issuer"></a>
## Enum: issuer
| Name | Value |
| ---- | ----- |
| issuer | moderator |



