package social.mycroblog.api.models

import kotlin.Long
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How many decisions of one mode and outcome a hook made, and how long they took.
 */
@Serializable
public data class AdminHookOutcome(
  /**
   * The mode of the hook when it made the decision.
   */
  @SerialName("mode")
  public val mode: HookDecisionMode,
  /**
   * What a decision records: a verdict (`allow`, `drop`, `flag`, `mark_sensitive`,
   * `require_approval`, `reject`), or a kind of failure, which is not a verdict: `timeout`, `error`,
   * `invalid` (the answer is outside the hook's vocabulary), `breaker_open` (the server skipped
   * the plugin after repeated failures).
   */
  @SerialName("outcome")
  public val outcome: HookOutcome,
  @SerialName("count")
  public val count: Long,
  @SerialName("average_latency_ms")
  public val averageLatencyMs: Long,
  @SerialName("max_latency_ms")
  public val maxLatencyMs: Long,
)
