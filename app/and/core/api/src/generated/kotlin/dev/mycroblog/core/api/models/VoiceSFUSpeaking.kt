package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Who makes sound now, from the audio levels that the node receives; it arrives at most every
 * 250 milliseconds.
 */
@SerialName("speaking")
@Serializable
public data class VoiceSFUSpeaking(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
  /**
   * The account IDs of the speakers who make sound; empty when nobody does.
   */
  @SerialName("participants")
  public val participants: List<@Contextual UUID>,
) : VoiceSFUNodeMessage
