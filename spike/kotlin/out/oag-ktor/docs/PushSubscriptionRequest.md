
# PushSubscriptionRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **endpoint** | **kotlin.String** | The push service URL (&#x60;PushSubscription.endpoint&#x60;), an &#x60;https&#x60; URL. |  |
| **propertyKeys** | [**PushSubscriptionKeys**](PushSubscriptionKeys.md) |  |  |
| **alerts** | [**kotlin.collections.List&lt;NotificationType&gt;**](NotificationType.md) | The notification types to push, each at most once; empty pushes none. A third-party client&#39;s subscription leaves out the moderator notification types.  |  |



