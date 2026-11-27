
# AdminTranslationOverview

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **month** | **kotlin.String** | The month that the numbers cover, as &#x60;YYYY-MM&#x60; in UTC. |  |
| **characters** | **kotlin.Long** | The characters of posts sent to providers this month, without the markers that stand in for mentions, links, and the like. A call to a provider that fails counts too. This is what the limits count, and it can differ from what a provider bills.  |  |
| **cacheHits** | **kotlin.Long** | The requests answered this month from a translation the server kept. |  |
| **cacheMisses** | **kotlin.Long** | The requests that went to a provider this month. |  |
| **providers** | [**kotlin.collections.List&lt;TranslationProvider&gt;**](TranslationProvider.md) | Every provider that this server has, enabled or not. API keys are not part of this. |  |
| **cacheHitRate** | **kotlin.Double** | The share of those requests answered from a kept translation, from 0 to 1; omitted before there is any.  |  [optional] |



