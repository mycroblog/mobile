
# InstanceCounts

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **members** | **kotlin.Int** | The local accounts that are not deleted, the instance&#39;s own actor aside. |  |
| **dailyPosts** | **kotlin.collections.List&lt;kotlin.Int&gt;** | The public posts of local accounts that are not deleted, in each of the 7 periods of 24 hours before &#x60;counted_at&#x60;, oldest first: the last item counts the posts of the 24 hours up to &#x60;counted_at&#x60;.  |  |
| **startedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the instance was set up. |  |
| **countedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the counts were taken. |  |



