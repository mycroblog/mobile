package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's automatic deletion of their own old posts. A post is
 * deleted once it was published more than `min_age_days` days ago, unless one of the
 * exceptions (the `keep_` properties) keeps it.
 */
@Serializable
public data class AutoDeletePolicy(
  /**
   * Whether old posts are deleted.
   */
  @SerialName("enabled")
  public val enabled: Boolean,
  /**
   * How many days after its publication a post is deleted.
   */
  @SerialName("min_age_days")
  public val minAgeDays: Int,
  /**
   * Keep the posts pinned to the profile.
   */
  @SerialName("keep_pinned")
  public val keepPinned: Boolean,
  /**
   * Keep the posts that the user bookmarked.
   */
  @SerialName("keep_bookmarked")
  public val keepBookmarked: Boolean,
  /**
   * Keep the posts that the user favourited.
   */
  @SerialName("keep_favourited")
  public val keepFavourited: Boolean,
  /**
   * Keep the posts with media attachments.
   */
  @SerialName("keep_media")
  public val keepMedia: Boolean,
  /**
   * Keep the posts with a poll.
   */
  @SerialName("keep_polls")
  public val keepPolls: Boolean,
  /**
   * Keep the articles. Always given in an answer; omitted in a request, the saved value stays
   * (on for a user who never saved a policy).
   */
  @SerialName("keep_articles")
  public val keepArticles: Boolean? = null,
  /**
   * Keep the posts with these visibilities, from the widest audience to the narrowest; empty keeps none for their visibility.
   */
  @SerialName("keep_visibilities")
  public val keepVisibilities: List<Visibility>,
  /**
   * Keep the posts boosted at least this many times; 0 keeps none for their boosts.
   */
  @SerialName("keep_min_boosts")
  public val keepMinBoosts: Int,
  /**
   * Keep the posts favourited at least this many times; 0 keeps none for their favourites.
   */
  @SerialName("keep_min_favourites")
  public val keepMinFavourites: Int,
)
