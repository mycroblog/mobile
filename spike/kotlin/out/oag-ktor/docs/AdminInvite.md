
# AdminInvite

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **code** | **kotlin.String** | The code people enter on the registration page, 16 characters. |  |
| **url** | [**java.net.URI**](java.net.URI.md) | The registration page with the code filled in, to send to people. |  |
| **uses** | **kotlin.Int** | How many people have signed up with the invite. |  |
| **state** | [**AdminInviteState**](AdminInviteState.md) |  |  |
| **creatorKind** | [**InviteCreatorKind**](InviteCreatorKind.md) |  |  |
| **createdByDeleted** | **kotlin.Boolean** | Whether a user created the invite whose account no longer exists. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **maxUses** | **kotlin.Int** | How many sign-ups the invite allows; omitted when there is no limit. |  [optional] |
| **createdBy** | [**Account**](Account.md) | The account of the user who created the invite. Absent for an invite created with the CLI and one whose creator&#39;s account is deleted (&#x60;created_by_deleted&#x60;).  |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the invite stops working; omitted when it never does. |  [optional] |



