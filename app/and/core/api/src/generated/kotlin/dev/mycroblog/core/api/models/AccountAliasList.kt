package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The aliases of the user, oldest first; at most 5.
 */
@Serializable
public data class AccountAliasList(
  @SerialName("items")
  public val items: List<AccountAlias>,
)
