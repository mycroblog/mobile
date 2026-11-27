package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How the newest schedule of a draft that is over ended.
 */
@Serializable
public data class DraftScheduleOutcome(
  /**
   * The time the post was scheduled for.
   */
  @SerialName("publish_at")
  public val publishAt: Instant,
  /**
   * `canceled`: the user canceled the schedule or scheduled another time. `failed`: the post
   * could not be published at its time, for `reason`.
   */
  @SerialName("outcome")
  public val outcome: DraftScheduleOutcomeOutcome,
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
  public val reason: DraftFailureReason? = null,
  /**
   * When the schedule ended.
   */
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
