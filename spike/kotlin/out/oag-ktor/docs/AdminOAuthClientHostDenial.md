
# AdminOAuthClientHostDenial

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **host** | **kotlin.String** | The host name in lowercase, without a port. |  |
| **deniedByDeleted** | **kotlin.Boolean** | Whether an administrator denied the host whose account no longer exists. |  |
| **deniedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **deniedBy** | [**Account**](Account.md) | The account of the administrator; omitted for a denial from the command line and when that account is deleted (&#x60;denied_by_deleted&#x60;).  |  [optional] |



