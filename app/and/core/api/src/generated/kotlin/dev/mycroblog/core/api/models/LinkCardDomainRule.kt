package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rule of the crawler of link cards for a domain and its subdomains. A rule on a subdomain
 * never loosens a rule on its parent: every rule that covers a host applies.
 */
@Serializable
public data class LinkCardDomainRule(
  /**
   * The domain, in canonical form (lower case, IDNA A-labels); the key of the rule.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * What the crawler of link cards does not do on the domain of a rule. `no_fetch`: it fetches
   * neither pages nor images, and the cards of its pages are hidden. `no_images`: it fetches pages
   * but no images, and its cards show without their images.
   */
  @SerialName("mode")
  public val mode: LinkCardDomainMode,
  /**
   * What the administrators keep about the rule; empty for none.
   */
  @SerialName("note")
  public val note: String,
  /**
   * When the rule was last set.
   */
  @SerialName("updated_at")
  public val updatedAt: Instant,
)
