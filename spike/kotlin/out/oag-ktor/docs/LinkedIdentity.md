
# LinkedIdentity

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **providerId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **providerKind** | [**LoginProviderKind**](LoginProviderKind.md) |  |  |
| **providerName** | **kotlin.String** | The administrators&#39; name for the provider, shown as written. |  |
| **linkedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the user linked the account. |  |
| **available** | **kotlin.Boolean** | Whether the provider is enabled: the account signs the user in and confirms the user&#39;s operations.  |  |



