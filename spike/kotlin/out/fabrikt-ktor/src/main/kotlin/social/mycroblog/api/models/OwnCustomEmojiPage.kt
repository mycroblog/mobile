package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of the custom emoji that the user made, with what the user used of their monthly limit: `used` counts the requests to create an emoji that the user submitted this UTC
 * calendar month and that are approved or pending (deleting an emoji does not lower it),
 * `pending` is the part of `used` that the pending requests take, `limit` is how many they may
 * submit in a month, absent for no limit, and `resets_at` is when the count resets.
 */
@Serializable
public data class OwnCustomEmojiPage(
  /**
   * An opaque pagination cursor. Clients pass it back unchanged and never parse it.
   */
  @SerialName("next_cursor")
  public val nextCursor: String? = null,
  @SerialName("items")
  public val items: List<OwnCustomEmoji>,
  /**
   * The approved and pending requests to create an emoji that the user submitted this month.
   */
  @SerialName("used")
  public val used: Int,
  /**
   * The part of `used` that the user's pending requests to create an emoji take.
   */
  @SerialName("pending")
  public val pending: Int,
  /**
   * How many requests to create an emoji the user may submit in a month; absent for no limit.
   */
  @SerialName("limit")
  public val limit: Int? = null,
  /**
   * When the count resets, the first day of the next month at 00:00 UTC.
   */
  @SerialName("resets_at")
  public val resetsAt: Instant,
)
