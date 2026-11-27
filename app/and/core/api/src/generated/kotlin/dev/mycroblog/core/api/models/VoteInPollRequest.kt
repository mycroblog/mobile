package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.collections.LinkedHashSet
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A vote in the poll of a status.
 */
@Serializable
public data class VoteInPollRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("poll_id")
  public val pollId: UUID,
  /**
   * The indexes of the chosen options in `Poll.options`; one for a single-choice poll.
   */
  @SerialName("choices")
  public val choices: LinkedHashSet<Int>,
)
