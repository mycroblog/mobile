
# FederationScopeImpact

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **scope** | [**inline**](#Scope) | The current value of &#x60;federation_scope&#x60;. |  |
| **follows** | **kotlin.Int** | How many follows would end. |  |
| **localAccounts** | **kotlin.Int** | How many local accounts would lose a follow. |  |
| **domains** | **kotlin.Int** | How many servers those follows are with. |  |
| **topDomains** | [**kotlin.collections.List&lt;FederationScopeImpactDomain&gt;**](FederationScopeImpactDomain.md) | The servers with the most follows that would end, at most 10, the most first. |  |


<a id="Scope"></a>
## Enum: scope
| Name | Value |
| ---- | ----- |
| scope | open, allowlist |



