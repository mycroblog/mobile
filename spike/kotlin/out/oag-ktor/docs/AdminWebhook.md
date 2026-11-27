
# AdminWebhook

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **url** | **kotlin.String** | Where the messages are posted. |  |
| **events** | [**kotlin.collections.List&lt;WebhookEventType&gt;**](WebhookEventType.md) | The events it subscribes to, in a stable order. |  |
| **state** | [**AdminWebhookState**](AdminWebhookState.md) |  |  |
| **stateReason** | [**AdminWebhookStateReason**](AdminWebhookStateReason.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the URL, the description, or the events last changed. |  |
| **description** | **kotlin.String** | What the webhook is for; omitted for none. |  [optional] |
| **failure** | [**AdminWebhookFailure**](AdminWebhookFailure.md) |  |  [optional] |



