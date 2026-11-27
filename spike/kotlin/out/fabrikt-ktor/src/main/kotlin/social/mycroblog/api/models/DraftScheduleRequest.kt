package social.mycroblog.api.models

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * When to publish a draft.
 */
@Serializable
public data class DraftScheduleRequest(
  /**
   * The time to publish the draft: at least `scheduled_post_min_lead_minutes` and at most
   * `scheduled_post_max_lead_days` from now (`limits` of `GET /instance`). The post is
   * published at that time or soon after.
   */
  @SerialName("publish_at")
  public val publishAt: Instant,
)
