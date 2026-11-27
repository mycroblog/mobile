
# FundingMeter

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **visibility** | [**inline**](#Visibility) | &#x60;percent&#x60;: only the percentage; &#x60;amounts&#x60;: also &#x60;income&#x60;, &#x60;target&#x60;, and &#x60;target_is_costs&#x60;.  |  |
| **label** | **kotlin.String** | The month, such as &#x60;2026-11&#x60;. |  |
| **startsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  |
| **endsOn** | **kotlin.String** | A calendar date &#x60;YYYY-MM-DD&#x60; without a time or a time zone, such as the day of a payment on a card statement or an invoice.  |  |
| **percent** | **kotlin.Long** | The net income over the target in whole percent, rounded down to 1%, so that 100 is reached only when &#x60;covered&#x60; is true. It goes past 100, and is 0 while the net income is not positive.  |  |
| **covered** | **kotlin.Boolean** | Whether the net income reaches the target, that is, this month is covered. |  |
| **estimated** | **kotlin.Boolean** | Whether the target is this month&#39;s costs and an imported usage charge of a billing month that its provider has not closed is among them: the percentage may still change.  |  |
| **rail** | **kotlin.Boolean** | Whether the web client shows the meter in its right rail as well as on the about page (the instance setting &#x60;funding_meter_rail&#x60;).  |  |
| **income** | [**Money**](Money.md) | This month&#39;s net income in the target&#39;s currency; only with &#x60;amounts&#x60;. |  [optional] |
| **target** | [**Money**](Money.md) | The monthly target; only with &#x60;amounts&#x60;. |  [optional] |
| **targetIsCosts** | **kotlin.Boolean** | Whether the target is this month&#39;s costs; only with &#x60;amounts&#x60;. |  [optional] |


<a id="Visibility"></a>
## Enum: visibility
| Name | Value |
| ---- | ----- |
| visibility | percent, amounts |



