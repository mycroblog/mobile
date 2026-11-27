package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The client's answer to the node's latest `offer`.
 */
@SerialName("answer")
@Serializable
public data class VoiceSFUAnswer(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
  /**
   * The session description of the answer.
   */
  @SerialName("sdp")
  public val sdp: String,
) : VoiceSFUClientMessage
