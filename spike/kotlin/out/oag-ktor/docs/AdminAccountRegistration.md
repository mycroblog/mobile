
# AdminAccountRegistration

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID that &#x60;approveRegistration&#x60; and &#x60;rejectRegistration&#x60; take. |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the registration entered its &#x60;registration_state&#x60;. |  |
| **reason** | **kotlin.String** | Why the applicant wants to join; omitted when not asked or not given. |  [optional] |
| **decisionNote** | **kotlin.String** | The moderators&#39; note on the decision, as plain text; omitted while the registration is pending and when the decision has no note.  |  [optional] |
| **inviteId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **invite** | [**AdminAccountInvite**](AdminAccountInvite.md) |  |  [optional] |



