
# AdminAuditEntry

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **actorKind** | [**AdminAuditActorKind**](AdminAuditActorKind.md) |  |  |
| **action** | [**AdminAuditAction**](AdminAuditAction.md) |  |  |
| **targetKind** | [**AdminAuditTargetKind**](AdminAuditTargetKind.md) |  |  |
| **targetId** | **kotlin.String** | The target: the ID of an account, a status, a report, an appeal, a user, a role, a rule, a sign-up block, a custom emoji or a request for one, a webhook, an announcement, an invite, a relay, a cost item, a supporter plan or subscription, an OAuth client, a voice room, or a login provider; the domain of a domain action or a link card rule; the host of a denied OAuth client; the source of a billing import; or the key of a setting or a text.  |  |
| **targetLabel** | **kotlin.String** | The target&#39;s display name when the operation happened, such as &#x60;@alice&#x60;, &#x60;@bob@example.com&#x60;, a domain, a setting&#39;s key, a rule&#39;s text, or a sign-up block&#39;s kind and value (&#x60;email_domain spam.example&#x60;); up to 512 characters.  |  |
| **targetExists** | **kotlin.Boolean** | Whether the target is still there to look at. It is &#x60;false&#x60; for an account, a report, an appeal, or a sign-up block that is gone (erased past its retention), for a role, a login provider, a custom emoji, a custom emoji request, a webhook, or a relay that was deleted, and for a link card domain whose rule was removed, which only &#x60;target_label&#x60; names now. It is &#x60;true&#x60; for the other kinds of targets, which are never removed.  |  |
| **occurredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **actorAccountId** | [**java.util.UUID**](java.util.UUID.md) | The local account of the user who performed the operation; omitted unless &#x60;actor_kind&#x60; is &#x60;user&#x60;, and when the user has no account.  |  [optional] |
| **actorAccount** | [**Account**](Account.md) | The account of &#x60;actor_account_id&#x60;; omitted when it is gone. |  [optional] |
| **sourceEventId** | **kotlin.String** | An opaque ID that relates the entries of one operation; omitted when the operation has no record of its own.  |  [optional] |



