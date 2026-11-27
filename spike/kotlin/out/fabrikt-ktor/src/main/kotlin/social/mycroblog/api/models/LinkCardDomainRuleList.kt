package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rules, by domain.
 */
@Serializable
public data class LinkCardDomainRuleList(
  @SerialName("items")
  public val items: List<LinkCardDomainRule>,
)
