package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The drafts of a subscription, the lifts first, each by domain and kind.
 */
@Serializable
public data class AdminDomainListDraftList(
  @SerialName("items")
  public val items: List<AdminDomainListDraft>,
)
