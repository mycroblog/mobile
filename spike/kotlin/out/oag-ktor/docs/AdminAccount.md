
# AdminAccount

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **account** | [**Account**](Account.md) |  |  |
| **local** | **kotlin.Boolean** | Whether the account is on this server. |  |
| **state** | [**AdminAccountState**](AdminAccountState.md) |  |  |
| **restrictions** | [**kotlin.collections.List&lt;AdminAccountRestriction&gt;**](AdminAccountRestriction.md) | What the account&#39;s own actions take away now, in a fixed order. The domain actions on its domain are listed apart.  |  |
| **registrationState** | [**RegistrationState**](RegistrationState.md) | Where the registration of a local account stands; a user made with the command line is &#x60;approved&#x60;. Omitted for a remote account.  |  [optional] |



