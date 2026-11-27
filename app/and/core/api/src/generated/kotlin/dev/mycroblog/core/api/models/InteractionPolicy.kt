package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Who may reply to, boost, and quote a status. Whatever the policy, a followers-only status is
 * boosted and quoted only by its author, a direct one is neither boosted nor quoted, and a reply
 * from an account the status mentions is always admitted.
 */
@Serializable
public data class InteractionPolicy(
  /**
   * Who may interact with a status in one way. Interactions of `automatic` are approved at once;
   * those of `manual`, a wider audience, wait for the author's approval. Without `manual`, the
   * others are refused.
   */
  @SerialName("reply")
  public val reply: InteractionRule,
  /**
   * Who may boost the status.
   */
  @SerialName("announce")
  public val announce: InteractionRule,
  /**
   * Who may interact with a status in one way. Interactions of `automatic` are approved at once;
   * those of `manual`, a wider audience, wait for the author's approval. Without `manual`, the
   * others are refused.
   */
  @SerialName("quote")
  public val quote: InteractionRule,
)
