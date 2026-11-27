package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A line of an imported list whose block was not added.
 */
@Serializable
public data class SignupBlockImportSkipped(
  /**
   * The line of the list, from 1.
   */
  @SerialName("line")
  public val line: Int,
  /**
   * The value as written on the line.
   */
  @SerialName("value")
  public val `value`: String,
  /**
   * `invalid`: not a valid value for the kind. `duplicate`: an earlier line has the same
   * value in canonical form. `already_blocked`: a block in effect of the same kind and
   * value with a severity at least as strong covers it.
   */
  @SerialName("reason")
  public val reason: SignupBlockImportSkippedReason,
)
