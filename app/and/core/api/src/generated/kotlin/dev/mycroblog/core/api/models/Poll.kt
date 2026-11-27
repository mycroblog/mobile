package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The poll of a status; omitted when the status has none. The counts of a poll of
 * another server are those its server last reported. A poll whose author hides the votes of
 * each option until it ends leaves out `votes_count` of its options until then. `voted` and
 * `own_votes` describe the authenticated user's vote and are omitted for anonymous requests.
 */
@Serializable
public data class Poll(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * When the poll ends; omitted when it has no end.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * Whether the poll has ended. An ended poll takes no votes and shows its results.
   */
  @SerialName("expired")
  public val expired: Boolean,
  /**
   * Whether a vote may choose several options.
   */
  @SerialName("multiple")
  public val multiple: Boolean,
  /**
   * The number of votes, one per chosen option.
   */
  @SerialName("votes_count")
  public val votesCount: Int,
  /**
   * The number of accounts that voted; omitted when it is unknown (a multiple-choice poll
   * of another server that does not report it).
   */
  @SerialName("voters_count")
  public val votersCount: Int? = null,
  /**
   * The options, in order; a vote names them by their index in this list.
   */
  @SerialName("options")
  public val options: List<PollOption>,
  /**
   * Whether the authenticated user voted in the poll.
   */
  @SerialName("voted")
  public val voted: Boolean? = null,
  /**
   * The indexes of the options the authenticated user voted for, in order.
   */
  @SerialName("own_votes")
  public val ownVotes: List<Int>? = null,
)
