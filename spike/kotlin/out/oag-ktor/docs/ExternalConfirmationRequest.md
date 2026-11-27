
# ExternalConfirmationRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **providerId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **operation** | [**ConfirmedOperation**](ConfirmedOperation.md) |  |  |
| **returnTo** | **kotlin.String** | The path on this server, such as &#x60;/settings/linked-accounts&#x60;, that the login provider sends the browser back to; the settings of the linked accounts when it is missing or not such a path.  |  [optional] |



