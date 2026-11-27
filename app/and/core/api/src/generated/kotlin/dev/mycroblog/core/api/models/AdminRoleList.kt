package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Roles, highest position first.
 */
@Serializable
public data class AdminRoleList(
  @SerialName("items")
  public val items: List<AdminRole>,
)
