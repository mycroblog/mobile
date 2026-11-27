
# MoveOwnAccountRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **targetAccountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **password** | **kotlin.String** | The user&#39;s current password. |  [optional] |
| **passkey** | [**PasskeyAssertion**](PasskeyAssertion.md) |  |  [optional] |
| **externalConfirmation** | **kotlin.String** | The &#x60;external_confirmation&#x60; token of a re-authentication with a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) for this operation, in place of the password.  |  [optional] |



