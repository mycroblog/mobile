
# AdminRelay

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **protocol** | [**RelayProtocol**](RelayProtocol.md) |  |  |
| **url** | **kotlin.String** | The URL it was registered with, its inbox or its actor. |  |
| **inboxUrl** | **kotlin.String** | Where the public posts are delivered. |  |
| **receive** | **kotlin.Boolean** | Whether the public posts it relays are taken in; &#x60;false&#x60; only sends. |  |
| **state** | [**RelayState**](RelayState.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **takenLastDay** | **kotlin.Int** | The activities it brought in during the last 24 hours that were taken in. |  |
| **droppedLastDay** | **kotlin.Int** | The activities it brought in during the last 24 hours that were dropped over the hourly limit (&#x60;relay_max_activities_per_hour&#x60;).  |  |
| **domainSuspended** | **kotlin.Boolean** | Whether a domain action suspends its server: nothing goes to it or comes from it while it does, whatever &#x60;state&#x60; says.  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **actorUri** | **kotlin.String** | The relay&#39;s actor, which accepted the &#x60;Follow&#x60;; omitted unless &#x60;accepted&#x60;. |  [optional] |



