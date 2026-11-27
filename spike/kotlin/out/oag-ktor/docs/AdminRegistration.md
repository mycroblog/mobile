
# AdminRegistration

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **state** | [**RegistrationState**](RegistrationState.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the registration entered its state. |  |
| **submittedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the user signed up. |  |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The user&#39;s local account; omitted when it is gone. |  [optional] |
| **username** | **kotlin.String** | The username of the user&#39;s local account; omitted when it is gone. |  [optional] |
| **email** | **kotlin.String** | The user&#39;s primary email address, shown only while the registration is pending, for its review; omitted when the user has none and once the registration is decided.  |  [optional] |
| **emailVerified** | **kotlin.Boolean** | Whether the user confirmed the email address; present together with &#x60;email&#x60;. |  [optional] |
| **decisionNote** | **kotlin.String** | The moderators&#39; note on the decision, as plain text; omitted while the registration is pending and when the decision has no note.  |  [optional] |
| **reason** | **kotlin.String** | Why the user wants to join, as plain text; omitted when they gave none. |  [optional] |
| **inviteId** | [**java.util.UUID**](java.util.UUID.md) | The invite the user signed up with; omitted for none. |  [optional] |



