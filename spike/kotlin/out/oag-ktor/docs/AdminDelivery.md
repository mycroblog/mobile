
# AdminDelivery

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **domain** | **kotlin.String** |  |  |
| **activityType** | **kotlin.String** | The type of the activity, such as &#x60;Create&#x60;, &#x60;Follow&#x60;, or &#x60;Accept&#x60;. |  |
| **activityCreatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the activity was recorded. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the inbox was added to the activity. |  |
| **sharedInbox** | **kotlin.Boolean** | Whether the inbox is a shared inbox of the server, which names no recipient. |  |
| **attempts** | [**kotlin.collections.List&lt;AdminDeliveryAttempt&gt;**](AdminDeliveryAttempt.md) | The attempts, oldest first. |  |
| **mask** | [**AdminDeliveryMask**](AdminDeliveryMask.md) |  |  [optional] |
| **sender** | [**AdminDeliverySender**](AdminDeliverySender.md) |  |  [optional] |
| **activityUri** | **kotlin.String** |  |  [optional] |
| **inboxUrl** | **kotlin.String** |  |  [optional] |



