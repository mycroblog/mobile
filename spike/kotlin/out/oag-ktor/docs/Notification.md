
# Notification

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **type** | [**NotificationType**](NotificationType.md) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When it happened. |  |
| **read** | **kotlin.Boolean** | Whether the user has read it: it is older than or at the marker&#39;s &#x60;last_read_id&#x60;, or it was marked read on its own (&#x60;POST /notifications/{id}/read&#x60;).  |  |
| **account** | [**Account**](Account.md) |  |  [optional] |
| **status** | [**Status**](Status.md) | The post that the notification is about, as the user sees it; omitted when the user may not see it, for a &#x60;direct&#x60; post without &#x60;read:direct&#x60;, and for a post of the board of an event without &#x60;read:events&#x60;.  |  [optional] |
| **report** | [**Report**](Report.md) | The user&#39;s report that a &#x60;report_resolved&#x60; tells of; omitted without &#x60;read:reports&#x60;. |  [optional] |
| **moderationAction** | [**ModerationAction**](ModerationAction.md) | The action on the user&#39;s account that a &#x60;moderation_notice&#x60; tells of, or whose appeal an &#x60;appeal_decided&#x60; tells of, with the appeal; first-party clients only.  |  [optional] |
| **reportId** | [**java.util.UUID**](java.util.UUID.md) | The report that an &#x60;admin_report&#x60; is about. |  [optional] |
| **appealId** | [**java.util.UUID**](java.util.UUID.md) | The appeal that an &#x60;admin_appeal&#x60; is about. |  [optional] |
| **hookAppealId** | [**java.util.UUID**](java.util.UUID.md) | The appeal of a refused post that an &#x60;admin_hook_appeal&#x60; is about. |  [optional] |
| **hookAppeal** | [**OwnHookAppeal**](OwnHookAppeal.md) | The user&#39;s appeal of a refused post that a &#x60;hook_appeal_decided&#x60; tells of, with its state; first-party clients only, and absent once the appeal is no longer kept.  |  [optional] |
| **customEmojiRequest** | [**CustomEmojiRequest**](CustomEmojiRequest.md) | The custom emoji request that an &#x60;admin_custom_emoji_request&#x60; or a &#x60;custom_emoji_request_decided&#x60; is about, as it is now; absent when it no longer exists, and without &#x60;read:custom_emojis&#x60;.  |  [optional] |
| **supporterSubscription** | [**SupporterSubscription**](SupporterSubscription.md) | The user&#39;s supporter subscription that a &#x60;supporter_payment_failed&#x60;, a &#x60;supporter_subscription_ended&#x60;, or a &#x60;supporter_plan_retired&#x60; is about, as it is now; absent when it no longer exists, and without &#x60;read:supporters&#x60;.  |  [optional] |
| **relationshipSeverance** | [**RelationshipSeverance**](RelationshipSeverance.md) | The follows that a &#x60;relationships_severed&#x60; tells of; omitted without &#x60;read:follows&#x60;. |  [optional] |
| **accountMove** | [**AccountMove**](AccountMove.md) |  |  [optional] |
| **serviceClosure** | [**ServiceClosureNotice**](ServiceClosureNotice.md) |  |  [optional] |
| **reaction** | [**ReactionEmoji**](ReactionEmoji.md) | The emoji of a &#x60;reaction&#x60;; omitted for the other types. |  [optional] |
| **voiceRoomId** | [**java.util.UUID**](java.util.UUID.md) | The voice chat room that a &#x60;voice_room_started&#x60; or a &#x60;voice_room_invited&#x60; is about. |  [optional] |
| **voiceRoom** | [**VoiceRoom**](VoiceRoom.md) | The voice chat room that a &#x60;voice_room_started&#x60; or a &#x60;voice_room_invited&#x60; is about, while it is open and the user sees it; omitted otherwise, and without &#x60;read:voice&#x60;.  |  [optional] |
| **interactionRequestId** | [**java.util.UUID**](java.util.UUID.md) | The interaction request that a &#x60;reply_request&#x60;, a &#x60;boost_request&#x60;, a &#x60;quote_request&#x60;, an &#x60;interaction_accepted&#x60;, or an &#x60;interaction_rejected&#x60; is about, which the interaction requests answer by.  |  [optional] |
| **eventId** | [**java.util.UUID**](java.util.UUID.md) | The event that an &#x60;event_*&#x60; notification is about. |  [optional] |
| **event** | [**Event**](Event.md) | The event that an &#x60;event_*&#x60; notification is about, as the user sees it now; omitted when the user no longer sees it, for the other types, and without &#x60;read:events&#x60;.  |  [optional] |
| **errorGroup** | [**NotificationErrorGroup**](NotificationErrorGroup.md) | What an &#x60;admin_error_group&#x60; tells of; omitted for the other types. |  [optional] |
| **announcement** | [**Announcement**](Announcement.md) | The announcement that an &#x60;announcement&#x60; tells of, as the user sees it; omitted for the other types. |  [optional] |
| **autoDeleteLimit** | [**NotificationAutoDeleteLimit**](NotificationAutoDeleteLimit.md) | What an &#x60;auto_delete_limit&#x60; tells of; omitted for the other types. |  [optional] |
| **scheduledStatusFailure** | [**NotificationScheduledStatusFailure**](NotificationScheduledStatusFailure.md) | The scheduled post that a &#x60;scheduled_status_failed&#x60; tells of, while its draft exists; omitted otherwise and for the other types.  |  [optional] |



