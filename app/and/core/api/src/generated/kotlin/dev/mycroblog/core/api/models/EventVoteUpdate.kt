package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An answer to a candidate date.
 */
@Serializable
public data class EventVoteUpdate(
  /**
   * An answer to a candidate date of an event's date poll.
   */
  @SerialName("vote")
  public val vote: EventVote,
)
