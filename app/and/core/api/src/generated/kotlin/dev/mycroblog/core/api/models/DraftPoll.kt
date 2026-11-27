package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The poll of a draft. A draft keeps a poll still being written (fewer than two options, or
 * blank ones); publishing it checks the limits of `PollInput`. It ends `expires_in` seconds after
 * the draft is published.
 */
@Serializable
public data class DraftPoll(
  /**
   * The options, in order, as plain text.
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
