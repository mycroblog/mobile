
# AdminSupporterTips

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **enabled** | **kotlin.Boolean** |  |  |
| **accepting** | **kotlin.Boolean** |  |  |
| **oneTimePayments** | **kotlin.Boolean** |  |  |
| **amounts** | [**kotlin.collections.List&lt;Money&gt;**](Money.md) | The amounts, smallest first; empty while the tips are disabled. |  |
| **limits** | [**kotlin.collections.List&lt;AdminSupporterTipLimits&gt;**](AdminSupporterTipLimits.md) |  |  |
| **serverLimitReached** | **kotlin.Boolean** |  |  |
| **changedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the tips were last enabled, changed, or disabled; omitted for never. |  [optional] |



