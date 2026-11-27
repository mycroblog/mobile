
# AdminCounts

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **openReports** | **kotlin.Long** | The reports that are open. Needs &#x60;manage_reports&#x60;. |  [optional] |
| **pendingRegistrations** | **kotlin.Long** | The registrations that await approval. Needs &#x60;manage_accounts&#x60;. |  [optional] |
| **pendingAppeals** | **kotlin.Long** | The appeals of moderation actions that await a decision. Needs &#x60;manage_accounts&#x60;. |  [optional] |
| **pendingCustomEmojiRequests** | **kotlin.Long** | The custom emoji requests that await a decision. Needs &#x60;manage_custom_emojis&#x60;. |  [optional] |
| **openErrorGroups** | **kotlin.Long** | The error groups that are open: new, regressed after a resolve, or unmuted. Needs &#x60;manage_errors&#x60;.  |  [optional] |
| **oldestOpenReportFiledAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the open report that has waited longest was filed; omitted when no report is open. Needs &#x60;manage_reports&#x60;.  |  [optional] |



