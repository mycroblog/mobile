
# CreateDomainActionRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String** | The remote domain, such as &#x60;spam.example&#x60;; a U-label spelling such as &#x60;例え.jp&#x60; is accepted. The action covers its subdomains.  |  |
| **kind** | [**AdminDomainActionKind**](AdminDomainActionKind.md) |  |  |
| **publicComment** | **kotlin.String** |  |  [optional] |
| **privateNote** | **kotlin.String** |  |  [optional] |
| **obfuscate** | **kotlin.Boolean** |  |  [optional] |
| **expiresIn** | **kotlin.Int** | The seconds the action lasts, at most ten years (315360000); omitted, it lasts until it is lifted.  |  [optional] |



