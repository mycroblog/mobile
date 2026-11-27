package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of an interaction policy: the kinds left out keep their rules. Interactions approved
 * before stay.
 */
@Serializable
public data class InteractionPolicyChange(
  /**
   * Who may interact with a status in one way. Interactions of `automatic` are approved at once;
   * those of `manual`, a wider audience, wait for the author's approval. Without `manual`, the
   * others are refused.
   */
  @SerialName("reply")
  public val reply: InteractionRule? = null,
  /**
   * Who may boost the status.
   */
  @SerialName("announce")
  public val announce: InteractionRule? = null,
  /**
   * Who may interact with a status in one way. Interactions of `automatic` are approved at once;
   * those of `manual`, a wider audience, wait for the author's approval. Without `manual`, the
   * others are refused.
   */
  @SerialName("quote")
  public val quote: InteractionRule? = null,
)
