
# AdminReportDetail

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **category** | [**ReportCategory**](ReportCategory.md) |  |  |
| **comment** | **kotlin.String** | The reporter&#39;s comment as plain text, up to 5,000 characters. |  |
| **forwarded** | **kotlin.Boolean** | Whether the report was forwarded to the target&#39;s server: when it was filed, because the reporter asked, or later by a moderator.  |  |
| **forwardable** | **kotlin.Boolean** | Whether a moderator can forward the report to the target&#39;s server now: it was filed on this server, its target is a remote account, it points at no voice chat room and no event, and it is not forwarded yet.  |  |
| **filedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **state** | [**AdminReportState**](AdminReportState.md) |  |  |
| **stateChangedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the report entered its state (when it was filed, resolved, or reopened). |  |
| **targetAccountId** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **statusIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The IDs of the target&#39;s posts that the report points at. |  |
| **ruleIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | The IDs of the instance rules that the report cites, retired ones included; only a report of the &#x60;violation&#x60; category cites rules.  |  |
| **notes** | [**kotlin.collections.List&lt;AdminReportNote&gt;**](AdminReportNote.md) | The moderators&#39; notes on the report, oldest first. |  |
| **targetHistory** | [**AdminReportTargetHistory**](AdminReportTargetHistory.md) |  |  |
| **statuses** | [**kotlin.collections.List&lt;AdminReportStatus&gt;**](AdminReportStatus.md) | The posts that the report points at, in the order of &#x60;status_ids&#x60;, whatever their visibility and also when their author deleted them, for the moderators to review. A post that the server no longer has is left out.  |  |
| **uri** | **kotlin.String** | The &#x60;id&#x60; of the &#x60;Flag&#x60; activity that another server sent; omitted for a report made on this server.  |  [optional] |
| **outcome** | [**AdminReportOutcome**](AdminReportOutcome.md) |  |  [optional] |
| **assigneeAccountId** | [**java.util.UUID**](java.util.UUID.md) | The account of the moderator who handles the report; omitted when nobody does.  |  [optional] |
| **reporterAccountId** | [**java.util.UUID**](java.util.UUID.md) | The account that filed the report; omitted when the reader is the account reported, who never learns who reported them.  |  [optional] |
| **reporterAccount** | [**Account**](Account.md) | The reporter, often the instance actor of a remote server; omitted when the account is gone, and when the reader is the account reported.  |  [optional] |
| **targetAccount** | [**Account**](Account.md) | The account reported, even when it is suspended or blocks the reporter; omitted when the account is gone.  |  [optional] |
| **voiceRoomId** | [**java.util.UUID**](java.util.UUID.md) | The voice chat room that the report points at, whose host is the target; omitted for none.  |  [optional] |
| **eventId** | [**java.util.UUID**](java.util.UUID.md) | The event that the report points at, whose organizer is the target; omitted for none.  |  [optional] |
| **hookOrigin** | [**AdminReportHookOrigin**](AdminReportHookOrigin.md) |  |  [optional] |
| **voiceRoom** | [**AdminReportVoiceRoom**](AdminReportVoiceRoom.md) |  |  [optional] |
| **event** | [**AdminReportEvent**](AdminReportEvent.md) |  |  [optional] |



