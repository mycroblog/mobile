
# Relationship

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **following** | **kotlin.Boolean** | Whether the viewer actively follows the account. |  [optional] |
| **requested** | **kotlin.Boolean** | Whether the viewer&#39;s follow request awaits the account&#39;s approval. |  [optional] |
| **followedBy** | **kotlin.Boolean** | Whether the account actively follows the viewer. |  [optional] |
| **requestedBy** | **kotlin.Boolean** | Whether the account&#39;s follow request awaits the viewer&#39;s approval. |  [optional] |
| **showBoosts** | **kotlin.Boolean** | Whether the viewer&#39;s follow shows the account&#39;s boosts in the home timeline (true without a follow). |  [optional] |
| **notify** | **kotlin.Boolean** | Whether the viewer subscribes to the account&#39;s new posts (&#x60;subscribeAccount&#x60;), with or without a follow. A subscription asked for with a follow request to an account on another server is false until that server accepts the follow.  |  [optional] |
| **blocking** | **kotlin.Boolean** | Whether the viewer blocks the account. |  [optional] |
| **muting** | **kotlin.Boolean** | Whether the viewer mutes the account (a mute that has not expired). |  [optional] |
| **mutingNotifications** | **kotlin.Boolean** | Whether the viewer&#39;s mute also hides the account&#39;s notifications. |  [optional] |
| **note** | **kotlin.String** | The viewer&#39;s private note on the account (&#x60;setAccountNote&#x60;), empty without one. Only the viewer reads it.  |  [optional] |



