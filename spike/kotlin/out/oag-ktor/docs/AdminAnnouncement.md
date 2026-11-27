
# AdminAnnouncement

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **title** | **kotlin.String** | The heading, in plain text. |  |
| **text** | **kotlin.String** | The text, in Markdown. |  |
| **content** | **kotlin.String** | The text as the users get it, an HTML fragment (&#x60;Announcement.content&#x60;). |  |
| **state** | [**AdminAnnouncementState**](AdminAnnouncementState.md) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When its text or window last changed. |  |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it starts to show once published; omitted to show it from its publication. |  [optional] |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it stops showing; omitted to show it until it is withdrawn. |  [optional] |
| **publishedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it was last published; omitted for a draft. |  [optional] |



