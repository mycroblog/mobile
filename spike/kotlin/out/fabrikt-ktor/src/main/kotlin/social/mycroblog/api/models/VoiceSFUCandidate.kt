package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An ICE candidate of the connection, which the client and the node both send.
 */
@SerialName("candidate")
@Serializable
public data class VoiceSFUCandidate(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
  /**
   * The JSON of an `RTCIceCandidateInit` with only `candidate`, `sdpMid`, `sdpMLineIndex`,
   * and `usernameFragment`; `candidate` is empty (no more candidates) or a `candidate:`
   * attribute on one line.
   */
  @SerialName("candidate")
  public val candidate: String,
) : VoiceSFUClientMessage,
    VoiceSFUNodeMessage
