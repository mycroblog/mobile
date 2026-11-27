
# DeactivationPreview

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **deletionDueAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account would be deleted if it were deactivated now. |  |
| **refusal** | [**inline**](#Refusal) | Why the account cannot be deactivated now; absent when it can: moderation keeps it from logging in (&#x60;login_restricted&#x60;), its deletion is scheduled (&#x60;deletion_scheduled&#x60;), it is deactivated (&#x60;already_deactivated&#x60;), it moved (&#x60;moved&#x60;), its registration waits for review (&#x60;registration_pending&#x60;), a paid subscription goes on (&#x60;paid_subscription&#x60;), no other administrator can sign in (&#x60;last_administrator&#x60;), or it was reactivated less than 24 hours ago (&#x60;recently_reactivated&#x60;).  |  [optional] |
| **availableAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account can be deactivated again, for &#x60;recently_reactivated&#x60;. |  [optional] |


<a id="Refusal"></a>
## Enum: refusal
| Name | Value |
| ---- | ----- |
| refusal | login_restricted, deletion_scheduled, already_deactivated, moved, registration_pending, paid_subscription, last_administrator, recently_reactivated |



