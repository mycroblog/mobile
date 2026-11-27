
# NotificationsRead

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **unreadCount** | **kotlin.Long** | The number of listed notifications that are unread, as &#x60;Notification.read&#x60; tells. |  |
| **lastReadId** | [**java.util.UUID**](java.util.UUID.md) | The newest notification read up to the marker; omitted when the user has read none. |  [optional] |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The notification read on its own past the marker (&#x60;POST /notifications/{id}/read&#x60;); omitted when the marker moved.  |  [optional] |



