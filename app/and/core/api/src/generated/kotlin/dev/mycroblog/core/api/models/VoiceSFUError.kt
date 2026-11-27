package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The node refused the connection or a message, and closes the WebSocket after it. Clients
 * branch on `code`, never on `message`.
 */
@SerialName("error")
@Serializable
public data class VoiceSFUError(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
  /**
   * Why: `invalid_ticket` (the ticket expired, was used, or is for another node; ask for a
   * new grant), `invalid_message` (a message the node does not understand),
   * `unsupported_version` (a `v` the node does not know), or `room_unavailable` (the session
   * or the room is no longer on the node). A client treats an unknown code as
   * `room_unavailable`.
   */
  @SerialName("code")
  public val code: String,
  /**
   * A description of the error in English, for logs.
   */
  @SerialName("message")
  public val message: String,
) : VoiceSFUNodeMessage
