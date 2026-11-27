
# CreateSignupBlockRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**AdminSignupBlockKind**](AdminSignupBlockKind.md) |  |  |
| **&#x60;value&#x60;** | **kotlin.String** | A domain such as &#x60;spam.example&#x60; or &#x60;例え.jp&#x60; (&#x60;email_domain&#x60;), an email address or the hex SHA-256 of a canonical one (&#x60;email_hash&#x60;), or a CIDR prefix such as &#x60;192.0.2.0/24&#x60; or a single IP address (&#x60;ip_range&#x60;).  |  |
| **severity** | [**AdminSignupBlockSeverity**](AdminSignupBlockSeverity.md) |  |  |
| **comment** | **kotlin.String** |  |  [optional] |
| **expiresIn** | **kotlin.Int** | The seconds the block lasts, at most ten years (315360000); omitted, it lasts until it is lifted.  |  [optional] |



