
# Poll

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **expired** | **kotlin.Boolean** | Whether the poll has ended. An ended poll takes no votes and shows its results. |  |
| **multiple** | **kotlin.Boolean** | Whether a vote may choose several options. |  |
| **votesCount** | **kotlin.Int** | The number of votes, one per chosen option. |  |
| **options** | [**kotlin.collections.List&lt;PollOption&gt;**](PollOption.md) | The options, in order; a vote names them by their index in this list. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the poll ends; omitted when it has no end. |  [optional] |
| **votersCount** | **kotlin.Int** | The number of accounts that voted; omitted when it is unknown (a multiple-choice poll of another server that does not report it).  |  [optional] |
| **voted** | **kotlin.Boolean** | Whether the authenticated user voted in the poll. |  [optional] |
| **ownVotes** | **kotlin.collections.List&lt;kotlin.Int&gt;** | The indexes of the options the authenticated user voted for, in order. |  [optional] |



