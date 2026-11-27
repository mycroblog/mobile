package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The scheduled post that a `scheduled_status_failed` tells of: its draft, why it was not
 * published, and when it was to be published.
 */
@Serializable
public data class NotificationScheduledStatusFailure(
  /**
   * The draft of the scheduled post, which stays to fix and schedule again.
   */
  @SerialName("draft_id")
  public val draftId: String,
  /**
   * Why a scheduled post was not published at its time; the draft stays, to fix and schedule
   * again. `restricted`: the account may not post (a moderation action, a suspension, or an
   * account that is not active). `reply_unavailable`: the post it replies to is gone, hidden from
   * the user, or does not take the reply. `quote_unavailable`: the post it quotes is gone, hidden
   * from the user, or may not be quoted. `media_unavailable`: an attached file is gone or failed
   * processing. `invalid`: the post does not pass the checks of a post, such as its length.
   * `rejected`: the server's automated review refused the post.
   */
  @SerialName("reason")
  public val reason: DraftFailureReason,
  /**
   * When the post was to be published.
   */
  @SerialName("publish_at")
  public val publishAt: Instant,
)
