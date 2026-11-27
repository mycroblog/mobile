
# UpdateLoginProviderRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **displayName** | **kotlin.String** | The name on the administration screens and on the sign-in button of an &#x60;oidc&#x60; provider, 1 to 100 characters without control characters.  |  [optional] |
| **issuer** | **kotlin.String** | For &#x60;oidc&#x60;, the issuer: an &#x60;https&#x60; URL without user info, a query, or a fragment, exactly as the ID tokens name it. For &#x60;mastodon&#x60;, the server, as a domain or as &#x60;https://&lt;host&gt;&#x60;. None for &#x60;github&#x60; and &#x60;discord&#x60;.  |  [optional] |
| **clientId** | **kotlin.String** | The client ID that the provider gave, up to 512 bytes. |  [optional] |
| **scopes** | **kotlin.collections.List&lt;kotlin.String&gt;** | Scopes to ask for besides the ones the kind needs, at most 20 (&#x60;read:org&#x60; for the Organizations of &#x60;github&#x60;); none for &#x60;mastodon&#x60;. The guild condition of &#x60;discord&#x60; adds &#x60;guilds&#x60; itself.  |  [optional] |
| **allowSignup** | **kotlin.Boolean** | Whether new users may sign up with the provider. |  [optional] |
| **allowLink** | **kotlin.Boolean** | Whether users may link their account to the provider. |  [optional] |
| **claims** | [**kotlin.collections.List&lt;LoginProviderClaimCondition&gt;**](LoginProviderClaimCondition.md) | The conditions on the claims of the ID token, for &#x60;oidc&#x60; only, at most 10. |  [optional] |
| **organizations** | **kotlin.collections.List&lt;kotlin.String&gt;** | The GitHub Organizations a user must belong to one of, for &#x60;github&#x60; only, at most 50. |  [optional] |
| **guilds** | **kotlin.collections.List&lt;kotlin.String&gt;** | The IDs of the Discord servers (guilds) a user must belong to one of, for &#x60;discord&#x60; only, at most 50; each is a decimal ID of up to 20 digits.  |  [optional] |



