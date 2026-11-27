
# LoginProvidersOnlyImpact

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **settingOn** | **kotlin.Boolean** | The value of the setting. The limit applies while it is on and &#x60;enabled_providers&#x60; is not 0. |  |
| **enabledProviders** | **kotlin.Int** | How many login providers users can sign in with now. With none, the limit does not apply. |  |
| **usersWithoutProvider** | **kotlin.Int** | How many active local users have no link to one of those providers, or to the provider &#x60;provider_id&#x60; named. With no provider at all, it is every active local user.  |  |
| **includesViewer** | **kotlin.Boolean** | Whether the user who asks is one of them. |  |
| **startsLimit** | **kotlin.Boolean** | Whether adding a provider, or enabling the provider &#x60;provider_id&#x60; named, would start the limit at once: the setting is on, no provider can be signed in with now, and that one could be.  |  |



