
# PasskeyRegistrationRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | What the user calls the passkey; spaces around it are dropped. |  |
| **password** | **kotlin.String** | The user&#39;s current password. |  [optional] |
| **passkey** | [**PasskeyAssertion**](PasskeyAssertion.md) |  |  [optional] |
| **externalConfirmation** | **kotlin.String** | The &#x60;external_confirmation&#x60; token of a re-authentication with a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) for this operation, in place of the password.  |  [optional] |



