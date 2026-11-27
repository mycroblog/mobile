package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An answer to an event.
 */
@Serializable
public data class EventRSVP(
  /**
   * An answer to an event.
   */
  @SerialName("answer")
  public val answer: EventAnswer,
)
