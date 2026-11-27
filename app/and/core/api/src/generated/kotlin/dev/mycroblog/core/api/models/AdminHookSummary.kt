package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Every decision hook with its settings, and its decisions of the last 7 days.
 */
@Serializable
public data class AdminHookSummary(
  /**
   * The start of the period that the counts cover.
   */
  @SerialName("since")
  public val since: Instant,
  @SerialName("hooks")
  public val hooks: List<AdminHook>,
)
