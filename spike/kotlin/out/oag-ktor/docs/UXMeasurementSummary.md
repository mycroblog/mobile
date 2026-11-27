
# UXMeasurementSummary

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **metric** | [**UXMetric**](UXMetric.md) |  |  |
| **name** | **kotlin.String** | The route ID, interaction kind, action ID, or call transport; &#x60;other&#x60; for the names past the first 200 of the metric that a process of the server kept.  |  |
| **samples** | **kotlin.Long** |  |  |
| **buckets** | [**kotlin.collections.List&lt;UXMeasurementBucket&gt;**](UXMeasurementBucket.md) | The samples by bucket, from the lowest bound up. |  |
| **p75** | **kotlin.Double** | The upper bound of the bucket that holds the 75th percentile: at least three quarters of the samples are at most this value. Absent when the 75th percentile is in the last bucket.  |  [optional] |



