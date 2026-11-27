package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A decision hook with its settings and the decisions of the period.
 */
@Serializable
public data class AdminHook(
  /**
   * A decision hook, where the server asks an external review before it accepts something:
   * `inbound.status` (posts from other servers), `inbound.follow` (follows from other servers),
   * `signup.check` (sign-ups), `media.check` (uploaded media), `local.status` (local posts),
   * `inbound.actor` (profiles of accounts on other servers), `local.profile` (local profiles).
   */
  @SerialName("hook")
  public val hook: HookName,
  /**
   * Whether the hook has a plugin. A hook without one calls nothing, whatever its mode.
   */
  @SerialName("configured")
  public val configured: Boolean,
  /**
   * How far a decision hook acts: `off` calls nothing, `observe` records the verdict and acts on
   * nothing, `enforce` acts on the verdict.
   */
  @SerialName("mode")
  public val mode: HookMode,
  /**
   * What a hook that enforces does when its plugin fails: `pass` lets the subject through, `retry`
   * fails the job so that it runs again, and `require_approval` holds the sign-up for approval.
   */
  @SerialName("on_failure")
  public val onFailure: HookOnFailure? = null,
  /**
   * The decisions per mode and outcome.
   */
  @SerialName("outcomes")
  public val outcomes: List<AdminHookOutcome>,
)
