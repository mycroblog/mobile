
# UpdateRoleRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **position** | **kotlin.Int** | The new rank of the role, from 0 to 999. |  [optional] |
| **permissions** | [**kotlin.collections.List&lt;Permission&gt;**](Permission.md) | The new permissions of the role, each once; empty removes them all. |  [optional] |
| **badge** | [**RoleBadge**](RoleBadge.md) | The new badge of the role. Its holders keep their choice to show it.  |  [optional] |
| **removeBadge** | **kotlin.Boolean** | &#x60;true&#x60; removes the badge of the role, and every holder&#39;s choice to show it; a new badge starts hidden. Sending it with &#x60;badge&#x60; is a 422 whose &#x60;detail&#x60; names &#x60;remove_badge&#x60;.  |  [optional] |



