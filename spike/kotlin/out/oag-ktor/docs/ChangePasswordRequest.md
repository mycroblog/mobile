
# ChangePasswordRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **newPassword** | **kotlin.String** | The new password, 8 to 128 characters. |  |
| **currentPassword** | **kotlin.String** | The user&#39;s current password, which confirms the change. |  [optional] |
| **passkey** | [**PasskeyAssertion**](PasskeyAssertion.md) |  |  [optional] |
| **externalConfirmation** | **kotlin.String** | The &#x60;external_confirmation&#x60; token of a re-authentication with a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) for this operation, in place of the password.  |  [optional] |



