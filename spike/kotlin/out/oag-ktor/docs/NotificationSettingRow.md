
# NotificationSettingRow

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **group** | [**NotificationGroup**](NotificationGroup.md) |  |  |
| **section** | [**inline**](#Section) | The kind of the row: &#x60;ordinary&#x60; (what other users did; in the app on and email off by default), &#x60;moderator&#x60; (to the holders of the permission it needs; both on by default), or &#x60;instance&#x60; (the instance&#39;s notices to the user; always in the app, but for &#x60;announcement&#x60;, &#x60;auto_delete_limit&#x60;, and &#x60;scheduled_status_failed&#x60;, which are on by default).  |  |
| **types** | [**kotlin.collections.List&lt;NotificationType&gt;**](NotificationType.md) | The notification types of the row. |  |
| **inApp** | [**NotificationSettingCell**](NotificationSettingCell.md) |  |  |
| **email** | [**NotificationSettingCell**](NotificationSettingCell.md) |  |  [optional] |


<a id="Section"></a>
## Enum: section
| Name | Value |
| ---- | ----- |
| section | ordinary, moderator, instance |



