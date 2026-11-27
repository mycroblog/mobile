
# AdminLoginProvider

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**LoginProviderKind**](LoginProviderKind.md) |  |  |
| **displayName** | **kotlin.String** | The name of the provider on the administration screens, and on the sign-in button of an &#x60;oidc&#x60; provider as it is written.  |  |
| **clientId** | **kotlin.String** |  |  |
| **scopes** | **kotlin.collections.List&lt;kotlin.String&gt;** | The scopes asked for besides the ones the kind needs, sorted. |  |
| **allowSignup** | **kotlin.Boolean** | Whether new users may sign up with the provider. |  |
| **allowLink** | **kotlin.Boolean** | Whether users may link their account to the provider. |  |
| **claims** | [**kotlin.collections.List&lt;LoginProviderClaimCondition&gt;**](LoginProviderClaimCondition.md) | The conditions of an &#x60;oidc&#x60; provider, by claim; empty lets every user in. |  |
| **organizations** | **kotlin.collections.List&lt;kotlin.String&gt;** | The GitHub Organizations, in lower case and sorted, one of which a user of a &#x60;github&#x60; provider must belong to; empty lets every GitHub user in.  |  |
| **guilds** | **kotlin.collections.List&lt;kotlin.String&gt;** | The IDs of the Discord servers (guilds), sorted, one of which a user of a &#x60;discord&#x60; provider must belong to; empty lets every Discord user in.  |  |
| **state** | [**AdminLoginProviderState**](AdminLoginProviderState.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **callbackUrl** | **kotlin.String** | The redirect URI to register at the provider: &#x60;https://&lt;host&gt;/auth/external/&lt;id&gt;/callback&#x60;.  |  |
| **linkedUsers** | **kotlin.Int** | The number of users whose accounts are linked to the provider; disabling or deleting it keeps them from signing in with it.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the settings last changed. |  |
| **issuer** | **kotlin.String** | The OpenID Connect issuer of an &#x60;oidc&#x60; provider, or the server&#39;s URL (&#x60;https://&lt;host&gt;&#x60;) of a &#x60;mastodon&#x60; one; omitted for &#x60;github&#x60; and &#x60;discord&#x60;.  |  [optional] |



