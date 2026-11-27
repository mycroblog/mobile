
# OAuthApp

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **clientId** | **kotlin.String** | The public identifier that the app sends as &#x60;client_id&#x60;. |  |
| **name** | **kotlin.String** | The name that the consent screen shows. |  |
| **redirectUris** | **kotlin.collections.List&lt;kotlin.String&gt;** | The URIs the app may redirect to, sorted; matched exactly, except that a loopback URI matches any port.  |  |
| **scope** | **kotlin.String** | The most the app may request, space-separated scopes. |  |
| **clientType** | [**OAuthAppClientType**](OAuthAppClientType.md) |  |  |
| **disabled** | **kotlin.Boolean** | Whether an administrator disabled the app: it cannot be authorized, get tokens, or use them until the administrator enables it again.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the app was registered. |  |
| **website** | **kotlin.String** | The app&#39;s home page; absent when it has none. |  [optional] |



