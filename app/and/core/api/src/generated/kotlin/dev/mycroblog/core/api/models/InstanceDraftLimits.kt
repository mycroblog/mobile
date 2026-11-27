package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The limits of drafts and scheduled posts (the instance settings of the same names). A save or a
 * schedule past one is a 422 that names it; lowering a limit deletes no draft and cancels no
 * schedule.
 */
@Serializable
public data class InstanceDraftLimits(
  /**
   * How many drafts without a schedule a user may keep.
   */
  @SerialName("max_drafts")
  public val maxDrafts: Int,
  /**
   * How many scheduled posts a user may have waiting.
   */
  @SerialName("max_scheduled_posts")
  public val maxScheduledPosts: Int,
  /**
   * How many scheduled posts of a user may wait for one UTC day.
   */
  @SerialName("max_scheduled_posts_per_day")
  public val maxScheduledPostsPerDay: Int,
  /**
   * How many minutes ahead, at least, a post may be scheduled.
   */
  @SerialName("scheduled_post_min_lead_minutes")
  public val scheduledPostMinLeadMinutes: Int,
  /**
   * How many days ahead, at most, a post may be scheduled.
   */
  @SerialName("scheduled_post_max_lead_days")
  public val scheduledPostMaxLeadDays: Int,
)
