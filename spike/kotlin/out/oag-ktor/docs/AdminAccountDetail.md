
# AdminAccountDetail

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **account** | [**Account**](Account.md) |  |  |
| **local** | **kotlin.Boolean** | Whether the account is on this server. |  |
| **state** | [**AdminAccountState**](AdminAccountState.md) |  |  |
| **restrictions** | [**kotlin.collections.List&lt;AdminAccountRestriction&gt;**](AdminAccountRestriction.md) | What the account&#39;s own actions take away now, in a fixed order. The domain actions on its domain are listed apart.  |  |
| **roles** | **kotlin.collections.List&lt;kotlin.String&gt;** | The keys of the user&#39;s roles, highest position first; empty for a remote account. |  |
| **actions** | [**kotlin.collections.List&lt;AdminAccountAction&gt;**](AdminAccountAction.md) | The newest 20 actions on the account, newest first. |  |
| **registrationState** | [**RegistrationState**](RegistrationState.md) | Where the registration of a local account stands; a user made with the command line is &#x60;approved&#x60;. Omitted for a remote account.  |  [optional] |
| **registration** | [**AdminAccountRegistration**](AdminAccountRegistration.md) |  |  [optional] |
| **emailVerified** | **kotlin.Boolean** | Whether the primary email address of a local account is verified; omitted when it has none.  |  [optional] |
| **emailSuppression** | [**EmailSuppression**](EmailSuppression.md) | The suppression of the primary email address of a local account; omitted when it is not suppressed. A moderator lifts it with &#x60;POST /admin/accounts/{id}/email_suppression/lift&#x60;.  |  [optional] |
| **twoFactorEnabled** | **kotlin.Boolean** | Whether the user of a local account signs in with two-factor authentication; omitted for a remote account.  |  [optional] |
| **reports** | [**kotlin.collections.List&lt;AdminReport&gt;**](AdminReport.md) | The newest 20 reports of the account, newest first; omitted unless the viewer also has the &#x60;manage_reports&#x60; permission.  |  [optional] |



