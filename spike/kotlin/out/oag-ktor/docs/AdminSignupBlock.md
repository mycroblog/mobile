
# AdminSignupBlock

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**AdminSignupBlockKind**](AdminSignupBlockKind.md) |  |  |
| **&#x60;value&#x60;** | **kotlin.String** | What the block matches, in canonical form: a domain in IDNA A-labels (&#x60;email_domain&#x60;), the hex SHA-256 of a canonical email address (&#x60;email_hash&#x60;), or a masked CIDR prefix (&#x60;ip_range&#x60;).  |  |
| **severity** | [**AdminSignupBlockSeverity**](AdminSignupBlockSeverity.md) |  |  |
| **state** | [**ModerationActionState**](ModerationActionState.md) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **comment** | **kotlin.String** | The moderators&#39; comment; omitted when there is none. |  [optional] |
| **moderationActionId** | [**java.util.UUID**](java.util.UUID.md) | The ban the block was made from; omitted for a block added on its own. |  [optional] |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the block ends by itself; omitted for one that lasts until lifted. |  [optional] |
| **liftedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the block was lifted; omitted until it is. |  [optional] |



