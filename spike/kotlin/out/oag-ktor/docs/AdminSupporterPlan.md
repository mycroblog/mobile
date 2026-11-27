
# AdminSupporterPlan

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **name** | **kotlin.String** |  |  |
| **description** | **kotlin.String** |  |  |
| **rank** | **kotlin.Int** | A higher rank is a higher plan, which includes everything of the lower ones. |  |
| **visibility** | [**SupporterPlanVisibility**](SupporterPlanVisibility.md) |  |  |
| **monthlyPrice** | [**Money**](Money.md) |  |  |
| **yearlyPrice** | [**Money**](Money.md) |  |  |
| **annualDiscountPercent** | **kotlin.Int** |  |  |
| **emojiLimit** | **kotlin.Int** | How many custom emoji the members may create each month (UTC) when the role lets them. |  |
| **role** | [**AdminSupporterPlanRole**](AdminSupporterPlanRole.md) |  |  |
| **pricesReady** | **kotlin.Boolean** |  |  |
| **retired** | **kotlin.Boolean** |  |  |
| **members** | **kotlin.Int** | The subscriptions that hold the role (active, canceling, or past due). |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **retiredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  [optional] |



