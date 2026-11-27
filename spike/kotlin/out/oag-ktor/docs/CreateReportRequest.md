
# CreateReportRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **accountId** | [**java.util.UUID**](java.util.UUID.md) | The account reported, local or remote. |  |
| **category** | [**ReportCategory**](ReportCategory.md) |  |  |
| **statusIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | Posts of the account that the user can see, up to 20. |  [optional] |
| **ruleIds** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md) | Instance rules in effect that the account broke, up to 20; only with the &#x60;violation&#x60; category.  |  [optional] |
| **comment** | **kotlin.String** | A comment for the moderators as plain text. |  [optional] |
| **voiceRoomId** | [**java.util.UUID**](java.util.UUID.md) | A voice chat room open on this server that the user can see; &#x60;account_id&#x60; is then its host. Such a report points at no posts and is never forwarded.  |  [optional] |
| **eventId** | [**java.util.UUID**](java.util.UUID.md) | An event on this server that the user can see; &#x60;account_id&#x60; is then its organizer. Such a report points at no posts and no voice chat room, and is never forwarded. The moderators see the settings of the event as the user saw them.  |  [optional] |
| **forward** | **kotlin.Boolean** | Asks to forward the report to the server of a remote account, without naming the reporter. It means nothing for a local account.  |  [optional] |



