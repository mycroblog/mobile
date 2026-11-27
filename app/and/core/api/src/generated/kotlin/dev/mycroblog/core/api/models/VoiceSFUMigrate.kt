package dev.mycroblog.core.api.models

import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The node is stopping and the room moved to another node: the client asks for a new grant
 * (`POST /voice/rooms/{id}/grant`) and connects to its `url` with the same session. The node
 * closes the WebSocket after it.
 */
@SerialName("migrate")
@Serializable
public data class VoiceSFUMigrate(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
) : VoiceSFUNodeMessage
