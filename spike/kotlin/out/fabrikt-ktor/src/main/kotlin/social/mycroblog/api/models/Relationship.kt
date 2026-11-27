package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The viewer's relationship with an account. It never says whether the account blocks
 * the viewer: such an account is not found at all.
 *
 * A token sees the fields its scopes cover, wherever the relationship comes from, and the
 * rest are absent, which tells nothing about them: `following`, `requested`, `followed_by`,
 * `requested_by`, `show_boosts`, and `notify` need `read:follows` or `write:follows`;
 * `blocking` needs `read:blocks` or `write:blocks`; `muting` and `muting_notifications` need
 * `read:mutes` or `write:mutes`; `note` needs `read:accounts` or `write:accounts`.
 */
@Serializable
public data class Relationship(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Whether the viewer actively follows the account.
   */
  @SerialName("following")
  public val following: Boolean? = null,
  /**
   * Whether the viewer's follow request awaits the account's approval.
   */
  @SerialName("requested")
  public val requested: Boolean? = null,
  /**
   * Whether the account actively follows the viewer.
   */
  @SerialName("followed_by")
  public val followedBy: Boolean? = null,
  /**
   * Whether the account's follow request awaits the viewer's approval.
   */
  @SerialName("requested_by")
  public val requestedBy: Boolean? = null,
  /**
   * Whether the viewer's follow shows the account's boosts in the home timeline (true without a follow).
   */
  @SerialName("show_boosts")
  public val showBoosts: Boolean? = null,
  /**
   * Whether the viewer subscribes to the account's new posts (`subscribeAccount`), with or
   * without a follow. A subscription asked for with a follow request to an account on another
   * server is false until that server accepts the follow.
   */
  @SerialName("notify")
  public val notify: Boolean? = null,
  /**
   * Whether the viewer blocks the account.
   */
  @SerialName("blocking")
  public val blocking: Boolean? = null,
  /**
   * Whether the viewer mutes the account (a mute that has not expired).
   */
  @SerialName("muting")
  public val muting: Boolean? = null,
  /**
   * Whether the viewer's mute also hides the account's notifications.
   */
  @SerialName("muting_notifications")
  public val mutingNotifications: Boolean? = null,
  /**
   * The viewer's private note on the account (`setAccountNote`), empty without one. Only the
   * viewer reads it.
   */
  @SerialName("note")
  public val note: String? = null,
)
