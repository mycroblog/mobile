
# AdminHook

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **hook** | [**HookName**](HookName.md) |  |  |
| **configured** | **kotlin.Boolean** | Whether the hook has a plugin. A hook without one calls nothing, whatever its mode. |  |
| **mode** | [**HookMode**](HookMode.md) |  |  |
| **outcomes** | [**kotlin.collections.List&lt;AdminHookOutcome&gt;**](AdminHookOutcome.md) | The decisions per mode and outcome. |  |
| **onFailure** | [**HookOnFailure**](HookOnFailure.md) |  |  [optional] |



