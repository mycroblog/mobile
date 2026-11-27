package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The candidate dates of an event's date poll, the earliest start first; up to 20.
 */
@Serializable
public data class EventCandidateList(
  @SerialName("items")
  public val items: List<EventCandidate>,
)
