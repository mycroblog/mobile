
# AdminDomainAction

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **domain** | **kotlin.String** | The domain, in IDNA A-labels. |  |
| **kind** | [**AdminDomainActionKind**](AdminDomainActionKind.md) |  |  |
| **obfuscate** | **kotlin.Boolean** | Whether a public list of the domain actions hides part of the domain. |  |
| **state** | [**ModerationActionState**](ModerationActionState.md) |  |  |
| **issuedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **publicComment** | **kotlin.String** | The reason that may be shown publicly; omitted when there is none. |  [optional] |
| **privateNote** | **kotlin.String** | The moderators&#39; note; omitted when there is none. |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the action ends by itself; omitted for one that lasts until lifted. |  [optional] |
| **liftedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the action was lifted; omitted until it is. |  [optional] |
| **subscriptionId** | [**java.util.UUID**](java.util.UUID.md) | The subscription to a domain block list that issued the action; omitted for an action a moderator issued.  |  [optional] |



