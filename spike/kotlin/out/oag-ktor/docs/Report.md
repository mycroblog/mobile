
# Report

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **category** | [**ReportCategory**](ReportCategory.md) |  |  |
| **comment** | **kotlin.String** | The comment as plain text. |  |
| **forwarded** | **kotlin.Boolean** | Whether the report is to be forwarded to the target&#39;s server. |  |
| **filedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **statusIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) |  |  |
| **ruleIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) |  |  |
| **state** | [**ReportState**](ReportState.md) |  |  |
| **targetAccount** | [**Account**](Account.md) | The account reported, as the user would see it through &#x60;getAccount&#x60;; omitted when that answers 404, because the account is suspended, deactivated, deleted, or blocks the user.  |  [optional] |
| **voiceRoomId** | [**java.util.UUID**](java.util.UUID.md) | The voice chat room reported; omitted for none. |  [optional] |
| **eventId** | [**java.util.UUID**](java.util.UUID.md) | The event reported; omitted for none. |  [optional] |



