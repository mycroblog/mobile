package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the authenticated user's notification policy does with the notifications of other
 * accounts, by condition on their source. A notification that meets several conditions takes
 * the strictest action (`drop` before `filter` before `accept`); one that meets none is
 * accepted. Every condition accepts until the user chooses. The policy applies to `follow`,
 * `follow_request`, `mention`, `boost`, `favourite`, `reaction`, `quote`, and
 * `voice_room_invited`; the notifications the user asked for, those of the moderators, and the
 * notices of the server (moderation, reports, appeals, the service closure, the supporters, the
 * announcements) are never filtered or dropped. A source whose request the user accepted meets
 * no condition, and a source the user follows meets none but `limited_account`. A change
 * applies to the notifications created after it.
 */
@Serializable
public data class NotificationPolicy(
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("not_following")
  public val notFollowing: NotificationPolicyAction,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("not_follower")
  public val notFollower: NotificationPolicyAction,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("new_account")
  public val newAccount: NotificationPolicyAction,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("unsolicited_private_mention")
  public val unsolicitedPrivateMention: NotificationPolicyAction,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("limited_account")
  public val limitedAccount: NotificationPolicyAction,
)
