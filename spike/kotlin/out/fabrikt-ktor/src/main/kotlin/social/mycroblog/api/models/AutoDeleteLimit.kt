package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The server maximum of the automatic deletion of old posts: while it is on, the automatic
 * deletion of every local user without the permission `keep_posts_longer` is on, deletes a post
 * at most `max_age_days` days after its publication, and keeps posts only by the exceptions that
 * the operator allows.
 */
@Serializable
public data class AutoDeleteLimit(
  /**
   * The most days after its publication that a post is kept.
   */
  @SerialName("max_age_days")
  public val maxAgeDays: Int,
  /**
   * Whether pinned posts may be kept (`keep_pinned`).
   */
  @SerialName("allow_pinned")
  public val allowPinned: Boolean,
  /**
   * How many pins keep posts, the most recently pinned first; 5, the most pins of an account,
   * keeps every pinned post.
   */
  @SerialName("kept_pins")
  public val keptPins: Int,
  /**
   * Whether the posts that the user bookmarked may be kept (`keep_bookmarked`).
   */
  @SerialName("allow_bookmarked")
  public val allowBookmarked: Boolean,
  /**
   * Whether the posts that the user favourited may be kept (`keep_favourited`).
   */
  @SerialName("allow_favourited")
  public val allowFavourited: Boolean,
  /**
   * Whether the posts with media attachments may be kept (`keep_media`).
   */
  @SerialName("allow_media")
  public val allowMedia: Boolean,
  /**
   * Whether the posts with a poll may be kept (`keep_polls`).
   */
  @SerialName("allow_polls")
  public val allowPolls: Boolean,
  /**
   * Whether articles may be kept (`keep_articles`); always given.
   */
  @SerialName("allow_articles")
  public val allowArticles: Boolean? = null,
  /**
   * Whether posts may be kept by their visibility (`keep_visibilities`).
   */
  @SerialName("allow_visibilities")
  public val allowVisibilities: Boolean,
  /**
   * Whether posts may be kept by their boosts and favourites (`keep_min_boosts`, `keep_min_favourites`).
   */
  @SerialName("allow_thresholds")
  public val allowThresholds: Boolean,
)
