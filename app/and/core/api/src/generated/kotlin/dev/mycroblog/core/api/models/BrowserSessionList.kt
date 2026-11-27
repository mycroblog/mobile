package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The browsers signed in to the authenticated user's account, the one that asks first.
 */
@Serializable
public data class BrowserSessionList(
  @SerialName("items")
  public val items: List<BrowserSession>,
)
