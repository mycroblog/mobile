
# AdminSupporterPlanCreateRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **role** | **kotlin.String** | The key of the role. |  |
| **name** | **kotlin.String** | 1 to 50 characters on one line. |  |
| **monthlyPrice** | [**Money**](Money.md) |  |  |
| **annualDiscountPercent** | **kotlin.Int** | 0 to 90. |  |
| **emojiLimit** | **kotlin.Int** | How many custom emoji the members may create each month (UTC), 0 to 10,000. |  |
| **rank** | **kotlin.Int** | 0 to 1,000,000, unique among the plans that are not retired. |  |
| **visibility** | [**SupporterPlanVisibility**](SupporterPlanVisibility.md) |  |  |
| **description** | **kotlin.String** | Up to 1,000 characters of plain text. |  [optional] |



