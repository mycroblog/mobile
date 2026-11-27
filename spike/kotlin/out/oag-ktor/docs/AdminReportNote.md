
# AdminReportNote

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **body** | **kotlin.String** | The note as plain text, up to 2,000 characters. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **authorAccountId** | [**java.util.UUID**](java.util.UUID.md) | The account of the moderator who wrote the note; omitted for a note from the command line and when the moderator has no account.  |  [optional] |



