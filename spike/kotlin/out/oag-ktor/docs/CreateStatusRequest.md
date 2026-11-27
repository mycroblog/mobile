
# CreateStatusRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **text** | **kotlin.String** | The plain text; the server links mentions, hashtags, and URLs. |  |
| **visibility** | [**Visibility**](Visibility.md) |  |  |
| **spoilerText** | **kotlin.String** | The content warning; omitted or empty for none. It counts toward &#x60;max_post_characters&#x60;. |  [optional] |
| **sensitive** | **kotlin.Boolean** | Whether the media are sensitive. |  [optional] |
| **language** | **kotlin.String** | A BCP 47 language tag; omitted means the user&#39;s default post language. |  [optional] |
| **inReplyToId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  [optional] |
| **media** | [**kotlin.collections.List&lt;StatusMediaItem&gt;**](StatusMediaItem.md) | The media to attach, in order. |  [optional] |
| **poll** | [**PollInput**](PollInput.md) |  |  [optional] |
| **quotedStatusId** | [**java.util.UUID**](java.util.UUID.md) | The status to quote. It must be one the user may see and may quote (&#x60;quote&#x60; of &#x60;Status.permissions&#x60; is not &#x60;denied&#x60;); a quote of a boost is a quote of the boosted status. A &#x60;local&#x60;, &#x60;private&#x60;, or &#x60;direct&#x60; status may not quote a remote status: that is a 422, and nothing is created. Quoting a remote status while the instance does not send posts out (&#x60;Instance.federation.mode&#x60; &#x60;off&#x60; or &#x60;inbound&#x60;) is the 403 &#x60;/api/v1/problems/federation-restricted&#x60;.  |  [optional] |
| **interactionPolicy** | [**InteractionPolicyChange**](InteractionPolicyChange.md) | Who may reply to, boost, and quote the new status; the kinds left out take the user&#39;s &#x60;default_interaction_policy&#x60;.  |  [optional] |



