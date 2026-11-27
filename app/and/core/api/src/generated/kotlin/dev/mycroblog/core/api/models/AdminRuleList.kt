package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rules.
 */
@Serializable
public data class AdminRuleList(
  @SerialName("items")
  public val items: List<AdminRule>,
)
