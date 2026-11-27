
# NotificationRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of the source account, which names the request. |  |
| **account** | [**Account**](Account.md) |  |  |
| **notificationsCount** | **kotlin.Long** | The number of notifications waiting in the request. |  |
| **lastNotification** | [**Notification**](Notification.md) | The newest notification of the request; omitted when it can no longer be shown, or a hide keyword filter of the notifications leaves it out.  |  [optional] |



