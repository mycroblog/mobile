
# AdminWebhookDelivery

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **messageId** | [**java.util.UUID**](java.util.UUID.md) | The &#x60;webhook-id&#x60; of the message, the same on every attempt. |  |
| **eventType** | [**WebhookMessageType**](WebhookMessageType.md) |  |  |
| **status** | [**AdminWebhookDeliveryStatus**](AdminWebhookDeliveryStatus.md) |  |  |
| **attempts** | **kotlin.Int** | How many times it was sent. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **lastAttempt** | [**AdminWebhookAttempt**](AdminWebhookAttempt.md) |  |  [optional] |
| **requestBody** | **kotlin.String** | The body of the request that every attempt sent, byte for byte as the receiver got it: a JSON document, which can carry personal data (an applicant&#39;s email address, a report&#39;s comment). It is kept as long as the delivery, 14 days. Omitted where the reader lacks the permission that subscribing to its event needs besides &#x60;manage_webhooks&#x60;.  |  [optional] |



