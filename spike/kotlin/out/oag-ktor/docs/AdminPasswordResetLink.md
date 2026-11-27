
# AdminPasswordResetLink

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **url** | [**java.net.URI**](java.net.URI.md) | The link that sets a new password; it works once. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the link stops working. |  |
| **loginProvidersOnly** | **kotlin.Boolean** | Whether registration and sign-in are limited to the login providers now. While they are, the link sets no password; it works only if the limit ends before &#x60;expires_at&#x60;.  |  |



