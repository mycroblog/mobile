
# AdminRule

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **position** | **kotlin.Int** | The sort key of the rule; the rules in effect are listed by it. Retiring a rule leaves a gap until the next move.  |  |
| **text** | **kotlin.String** | The rule, on one line, as plain text. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the current revision was recorded. |  |
| **hint** | **kotlin.String** | An explanation of the rule, as plain text; omitted when there is none. |  [optional] |
| **retiredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the rule was retired; omitted for a rule in effect. |  [optional] |



