package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Who may interact with a status in one way. Interactions of `automatic` are approved at once;
 * those of `manual`, a wider audience, wait for the author's approval. Without `manual`, the
 * others are refused.
 */
@Serializable
public data class InteractionRule(
  /**
   * Whom an interaction policy admits, narrowest first: `nobody` the author alone, `mentioned`
   * the accounts the status mentions, `followers` the author's followers and the accounts the
   * status mentions, `everyone` anyone who may see the status. The author always may interact.
   */
  @SerialName("automatic")
  public val automatic: InteractionAudience,
  /**
   * Whom the author approves by hand; wider than `automatic`. Left out for none.
   */
  @SerialName("manual")
  public val manual: InteractionAudience? = null,
)
