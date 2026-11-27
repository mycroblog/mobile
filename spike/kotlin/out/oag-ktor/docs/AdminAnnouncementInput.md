
# AdminAnnouncementInput

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | The heading, in plain text: 1 to 100 grapheme clusters on one line, without control characters. White space around it is trimmed.  |  |
| **text** | **kotlin.String** | The text, in Markdown. |  |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it starts to show once published; omitted to show it from its publication. |  [optional] |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it stops showing, after &#x60;starts_at&#x60;; omitted to show it until it is withdrawn. |  [optional] |



