
# AuthorizedApp

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of the client, which &#x60;DELETE /authorized_apps/{id}&#x60; takes. |  |
| **clientId** | **kotlin.String** | The client&#39;s public identifier. For an app registered by its Client ID Metadata Document it is the document&#39;s &#x60;https&#x60; URL, whose host shows where the app comes from.  |  |
| **name** | **kotlin.String** | The app&#39;s name, as the app gives it. |  |
| **registration** | [**inline**](#Registration) | How the app was registered: &#x60;first_party&#x60; for the server&#39;s own client, &#x60;user&#x60; for an app a user registered, &#x60;metadata_document&#x60; for a Client ID Metadata Document, and &#x60;dynamic&#x60; for dynamic client registration (self-declared).  |  |
| **firstParty** | **kotlin.Boolean** | Whether the app is one of the server&#39;s own clients. |  |
| **scope** | **kotlin.String** | The scopes the user granted, space separated, as the union of the app&#39;s working grants. |  |
| **authorizedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the user first authorized the app (the oldest of its working grants). |  |
| **website** | **kotlin.String** | The app&#39;s home page, as the app gives it. Absent when it gave none. |  [optional] |
| **registeredBy** | **kotlin.String** | The username of the user who registered the app (&#x60;user&#x60; only). Absent otherwise. |  [optional] |
| **lastUsedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the app last made a request with a grant of the user, exact to the hour. Absent when no use was recorded.  |  [optional] |


<a id="Registration"></a>
## Enum: registration
| Name | Value |
| ---- | ----- |
| registration | first_party, user, metadata_document, dynamic |



