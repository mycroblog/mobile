package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The answer of a user to a candidate date.
 */
@Serializable
public data class EventBallot(
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
  /**
   * An answer to a candidate date of an event's date poll.
   */
  @SerialName("vote")
  public val vote: EventVote,
)
