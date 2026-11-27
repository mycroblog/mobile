package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A poll of a new status. It ends `expires_in` seconds after the status is
 * posted; the author and the users who voted are notified then.
 */
@Serializable
public data class PollInput(
  /**
   * The options, in order, as plain text; each one different from the others.
   */
  @SerialName("options")
  public val options: List<String>,
  /**
   * How long the poll takes votes, in seconds (5 minutes to 30 days).
   */
  @SerialName("expires_in")
  public val expiresIn: Int,
  /**
   * Whether a vote may choose several options.
   */
  @SerialName("multiple")
  public val multiple: Boolean? = null,
  /**
   * Whether the votes of each option are hidden until the poll ends.
   */
  @SerialName("hide_totals")
  public val hideTotals: Boolean? = null,
)
