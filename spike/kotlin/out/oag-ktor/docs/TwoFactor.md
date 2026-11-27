
# TwoFactor

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **enabled** | **kotlin.Boolean** | Whether signing in asks for a code after the password. |  |
| **enrolledAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the authenticator app was enrolled; omitted when not enabled. |  [optional] |
| **recoveryCodesRemaining** | **kotlin.Int** | How many recovery codes are unused; omitted when not enabled. |  [optional] |



