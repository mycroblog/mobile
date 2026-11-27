package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The allowed domains, by domain.
 */
@Serializable
public data class DomainAllowList(
  @SerialName("items")
  public val items: List<DomainAllow>,
)
