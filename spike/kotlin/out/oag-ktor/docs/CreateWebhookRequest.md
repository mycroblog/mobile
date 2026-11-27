
# CreateWebhookRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **url** | **kotlin.String** | An absolute &#x60;https&#x60; (or &#x60;http&#x60;) URL of at most 2048 bytes, without user info or a fragment. A destination on loopback or a private network is reached only when the operator allows it (&#x60;webhook_allowed_destinations&#x60;).  |  |
| **events** | [**kotlin.collections.List&lt;WebhookEventType&gt;**](WebhookEventType.md) | The events to subscribe to, at least one. |  |
| **description** | **kotlin.String** | What the webhook is for, up to 200 characters. |  [optional] |



