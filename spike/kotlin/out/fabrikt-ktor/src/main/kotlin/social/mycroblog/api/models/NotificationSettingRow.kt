package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One row of the notification settings with its channels. `in_app` creates the notifications:
 * turned off, the row's notifications are not created, so neither Web Push nor email delivers
 * them, and clients show the row's other channels disabled; the notifications from before stay.
 * `email` sends each notification to the primary address once it is confirmed and while emails
 * reach it (`Preferences.email_suppression`). The ordinary notifications, `moderation`, and
 * `auto_delete_limit` are emailed one by one 15 minutes after they arrive, and only while they stay unread (newer than
 * the read marker and not dismissed); the moderator notifications are emailed at once. Clients
 * offer the email column only when `Instance.sends_email` is true. A row without `email` (`status`,
 * `interaction_request`, `announcement`, `scheduled_status_failed`) is never emailed, and clients show no email cell for it. Web Push stays the choice of each
 * subscription (`putPushSubscription`).
 */
@Serializable
public data class NotificationSettingRow(
  /**
   * A row of the notification settings: one or more notification types that the user turns on
   * and off together. The ordinary notifications are one row per type (`mention`, `follow`,
   * `follow_request`, `follow_accepted`, `boost`, `favourite`, `reaction`, `quote`,
   * `poll_closed`, `account_moved`, `status`), except `voice`, which is `voice_room_started` and
   * `voice_room_invited`, `event`, which is the `event_*` types (the email of an `event_reminder`
   * is sent at once, not after the notification stays unread), and `interaction_request`, which is `reply_request`, `boost_request`,
   * `quote_request`, `interaction_accepted`, and `interaction_rejected`. `status` and
   * `interaction_request` have no `email` channel. The moderator notifications are one row per type (`admin_report`,
   * `admin_sign_up`, `admin_appeal`, `admin_webhook_disabled`, `admin_custom_emoji_request`,
   * `admin_account_moved`, `admin_service_closure_due`, `admin_error_group`, `admin_hook_failing`, `admin_hook_appeal`,
   * `admin_domain_list_failing`). The instance's
   * notices to the user are `moderation` (`moderation_notice`, `report_resolved`,
   * `appeal_decided`, `relationships_severed`, `custom_emoji_request_decided`,
   * `hook_appeal_decided`),
   * `service_closure`, `supporter` (the `supporter_*` types), `announcement`, which the user
   * turns off in the app (on by default) and which has no `email` channel, and
   * `auto_delete_limit`, whose app and email channels the user chooses, both on by default, and
   * `scheduled_status_failed`, which the user turns off in the app (on by default) and which has
   * no `email` channel.
   */
  @SerialName("group")
  public val group: NotificationGroup,
  /**
   * The kind of the row: `ordinary` (what other users did; in the app on and email off by
   * default), `moderator` (to the holders of the permission it needs; both on by default),
   * or `instance` (the instance's notices to the user; always in the app, but for
   * `announcement`, `auto_delete_limit`, and `scheduled_status_failed`, which are on by default).
   */
  @SerialName("section")
  public val section: NotificationSettingRowSection,
  /**
   * The notification types of the row.
   */
  @SerialName("types")
  public val types: List<NotificationType>,
  /**
   * One channel of one row of the notification settings.
   */
  @SerialName("in_app")
  public val inApp: NotificationSettingCell,
  /**
   * One channel of one row of the notification settings.
   */
  @SerialName("email")
  public val email: NotificationSettingCell? = null,
)
