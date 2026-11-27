package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Asks the node to restart ICE, after the client's network changed; the node answers with an
 * `offer` that restarts it.
 */
@SerialName("restart")
@Serializable
public data class VoiceSFURestart(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
) : VoiceSFUClientMessage
