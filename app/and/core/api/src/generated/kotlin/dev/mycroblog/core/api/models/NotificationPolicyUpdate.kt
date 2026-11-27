package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Changes of the authenticated user's notification policy; an omitted condition keeps its action.
 */
@Serializable
public data class NotificationPolicyUpdate(
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("not_following")
  public val notFollowing: NotificationPolicyAction? = null,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("not_follower")
  public val notFollower: NotificationPolicyAction? = null,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("new_account")
  public val newAccount: NotificationPolicyAction? = null,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("unsolicited_private_mention")
  public val unsolicitedPrivateMention: NotificationPolicyAction? = null,
  /**
   * What the notification policy does with the notifications whose source meets a condition.
   * `accept` lists them and delivers them as the notification settings say; `filter` keeps them
   * out of the list and the unread count, in the request of their source, and delivers them
   * nowhere (no Web Push, email, sound, or streaming event); `drop` creates no notification.
   */
  @SerialName("limited_account")
  public val limitedAccount: NotificationPolicyAction? = null,
)
