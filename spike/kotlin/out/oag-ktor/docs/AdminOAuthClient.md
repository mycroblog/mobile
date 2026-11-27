
# AdminOAuthClient

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **clientId** | **kotlin.String** | The identifier that the client sends, or the URL of its metadata document. |  |
| **name** | **kotlin.String** | The client&#39;s name. For a client registered by its metadata document, the name that the document declares; every other name is a claim of whoever registered the client.  |  |
| **registration** | [**OAuthClientRegistration**](OAuthClientRegistration.md) |  |  |
| **confidential** | **kotlin.Boolean** | Whether the client has a secret (a confidential client) or not (a public client). |  |
| **scopes** | **kotlin.collections.List&lt;kotlin.String&gt;** | The most that the client may be granted. |  |
| **ownerDeleted** | **kotlin.Boolean** | Whether a user registered the client whose account no longer exists. |  |
| **state** | [**AdminOAuthClientState**](AdminOAuthClientState.md) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **website** | **kotlin.String** | The client&#39;s home page; omitted when it has none. |  [optional] |
| **owner** | [**Account**](Account.md) | The account of the user who registered the client; omitted for the other routes and when that account is deleted (&#x60;owner_deleted&#x60;).  |  [optional] |



