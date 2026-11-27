
# AdminSupporterProgram

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **enabled** | **kotlin.Boolean** |  |  |
| **sendsEmail** | **kotlin.Boolean** |  |  |
| **commerceDisclosure** | **kotlin.Boolean** | Whether the instance publishes its commerce disclosures (&#x60;/about/commerce&#x60;). |  |
| **liveSubscriptions** | **kotlin.Int** | The subscriptions that go on, which keep the program from being disabled. |  |
| **canEnable** | **kotlin.Boolean** | Whether the program can be enabled now. |  |
| **warnings** | [**kotlin.collections.List&lt;SupporterProgramWarning&gt;**](SupporterProgramWarning.md) |  |  |
| **provider** | **kotlin.String** | The payment provider the program was enabled with; omitted while disabled. |  [optional] |
| **configuredProvider** | **kotlin.String** | The payment provider of the deployment&#39;s configuration; omitted for none. |  [optional] |
| **changedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the program was last enabled or disabled; omitted for never. |  [optional] |



