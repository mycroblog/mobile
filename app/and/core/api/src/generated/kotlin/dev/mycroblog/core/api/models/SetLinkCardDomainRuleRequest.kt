package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rule to set on a domain.
 */
@Serializable
public data class SetLinkCardDomainRuleRequest(
  /**
   * What the crawler of link cards does not do on the domain of a rule. `no_fetch`: it fetches
   * neither pages nor images, and the cards of its pages are hidden. `no_images`: it fetches pages
   * but no images, and its cards show without their images.
   */
  @SerialName("mode")
  public val mode: LinkCardDomainMode,
  /**
   * What the administrators keep about the rule; empty when omitted.
   */
  @SerialName("note")
  public val note: String? = null,
)
