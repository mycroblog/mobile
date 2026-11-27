package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One option of a poll.
 */
@Serializable
public data class PollOption(
  /**
   * The option as plain text.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The number of votes for the option; omitted while the poll hides the votes of each
   * option until it ends.
   */
  @SerialName("votes_count")
  public val votesCount: Int? = null,
)
