package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A line of a CSV file that is not an entry.
 */
@Serializable
public data class DataImportSkipped(
  /**
   * The line of the file, from 1.
   */
  @SerialName("line")
  public val line: Int,
  /**
   * The line as written.
   */
  @SerialName("value")
  public val `value`: String,
  /**
   * `invalid`: the line names no account address (for bookmarks, no post URL; for
   * domain mutes, no domain).
   * `duplicate`: an earlier line names the same account, post, or domain.
   */
  @SerialName("reason")
  public val reason: DataImportSkippedReason,
)
