
# CreateOAuthAppRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The app&#39;s name, 1 to 100 characters on one line. |  |
| **redirectUris** | **kotlin.collections.List&lt;kotlin.String&gt;** | 1 to 10 redirect URIs (RFC 8252): an &#x60;https&#x60; URL, a private-use scheme in reverse domain notation (&#x60;com.example.app:/callback&#x60;), or an &#x60;http&#x60; loopback URL on &#x60;127.0.0.1&#x60; or &#x60;[::1]&#x60; (not &#x60;localhost&#x60;), none with a fragment or user info.  |  |
| **scope** | **kotlin.String** | The most the app may request, space-separated scopes: not empty, and without the first-party-only scopes (&#x60;admin&#x60;, &#x60;admin:read&#x60;, &#x60;admin:write&#x60;, &#x60;write:supporters&#x60;).  |  |
| **clientType** | [**OAuthAppClientType**](OAuthAppClientType.md) |  |  |
| **website** | **kotlin.String** | The app&#39;s home page, an absolute &#x60;https&#x60; or &#x60;http&#x60; URL of at most 2048 bytes. |  [optional] |



