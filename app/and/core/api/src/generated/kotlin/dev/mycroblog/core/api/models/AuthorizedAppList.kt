package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The apps the authenticated user authorized, one per client, most recently used first.
 */
@Serializable
public data class AuthorizedAppList(
  @SerialName("items")
  public val items: List<AuthorizedApp>,
)
