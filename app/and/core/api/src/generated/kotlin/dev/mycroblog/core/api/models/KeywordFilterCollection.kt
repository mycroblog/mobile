package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user's keyword filters, oldest first, the expired ones too.
 */
@Serializable
public data class KeywordFilterCollection(
  @SerialName("items")
  public val items: List<KeywordFilter>,
)
