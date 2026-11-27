
# OwnInvite

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **code** | **kotlin.String** | The code people enter on the registration page, 16 characters. |  |
| **url** | [**java.net.URI**](java.net.URI.md) | The registration page with the code filled in, to send to people. |  |
| **maxUses** | **kotlin.Int** | How many sign-ups the invite allows. |  |
| **uses** | **kotlin.Int** | How many people have signed up with the invite. |  |
| **state** | [**AdminInviteState**](AdminInviteState.md) |  |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the invite stops working. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |



