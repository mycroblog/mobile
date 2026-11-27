
# PushSubscription

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **endpoint** | [**java.net.URI**](java.net.URI.md) | The push service URL of the browser&#39;s subscription. |  |
| **alerts** | [**kotlin.collections.List&lt;NotificationType&gt;**](NotificationType.md) | The notification types that are pushed, in a stable order. |  |
| **serverKey** | **kotlin.String** | The instance&#39;s VAPID public key now. When it differs from the key the browser subscribed with, the subscription receives nothing; subscribe again.  |  |



