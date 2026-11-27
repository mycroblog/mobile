
# AdminRole

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **key** | **kotlin.String** | The name of the role, such as &#x60;admin&#x60; or &#x60;moderator&#x60;. |  |
| **position** | **kotlin.Int** | The rank of the role, from 0 to 999, and 1000 for &#x60;admin&#x60;. |  |
| **permissions** | [**kotlin.collections.List&lt;Permission&gt;**](Permission.md) | The permissions that the role grants, in the order of &#x60;Permission&#x60;. |  |
| **badge** | [**RoleBadge**](RoleBadge.md) | The badge of the role, which its holders may show on their profile; omitted when it has none. |  [optional] |
| **supporterPlan** | [**AdminRoleSupporterPlan**](AdminRoleSupporterPlan.md) | The supporter plan that grants the role, which only its subscriptions grant and revoke; omitted for a role that no plan grants. A role is the role of one plan at most.  |  [optional] |



