
# Announcement

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **title** | **kotlin.String** | The heading, in plain text, as the administrators wrote it; render it with &#x60;dir&#x3D;\&quot;auto\&quot;&#x60;. Empty for an announcement without one.  |  |
| **content** | **kotlin.String** | The text as an HTML fragment, sanitized (paragraphs, emphasis, lists, quotes, code, headings from &#x60;h2&#x60;, and &#x60;http&#x60;, &#x60;https&#x60;, and &#x60;mailto&#x60; links).  |  |
| **publishedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it started to show to the users, its publication or the start of its window. |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When its text or window last changed. |  |
| **read** | **kotlin.Boolean** | Whether the user has marked it read. |  |
| **ended** | **kotlin.Boolean** | Whether its window has ended, which only the list with the past announcements (&#x60;listAnnouncementHistory&#x60;) has; such an announcement can no longer be marked read.  |  |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it stops showing; omitted when it shows until it is withdrawn. |  [optional] |



