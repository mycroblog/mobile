
# AdminAccountInvite

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **creatorKind** | [**InviteCreatorKind**](InviteCreatorKind.md) |  |  |
| **createdByDeleted** | **kotlin.Boolean** | Whether a user created the invite whose account no longer exists. |  |
| **createdBy** | [**Account**](Account.md) | The account of the user who created the invite. Absent for an invite created with the CLI and one whose creator&#39;s account is deleted (&#x60;created_by_deleted&#x60;).  |  [optional] |



