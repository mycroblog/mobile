
# DeactivateOwnAccountRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **password** | **kotlin.String** | The user&#39;s current password. |  [optional] |
| **twoFactorCode** | **kotlin.String** | A code of the user&#39;s authenticator app, or a recovery code. |  [optional] |
| **passkey** | [**PasskeyAssertion**](PasskeyAssertion.md) |  |  [optional] |
| **externalConfirmation** | **kotlin.String** | The &#x60;external_confirmation&#x60; token of a re-authentication with a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) for this operation, in place of the password.  |  [optional] |



