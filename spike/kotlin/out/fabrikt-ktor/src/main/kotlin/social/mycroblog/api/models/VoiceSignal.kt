package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A WebRTC signal from a session of the user to another session of a voice chat room. An
 * `offer` and an `answer` carry `sdp` and no `candidate`, a `candidate` carries `candidate` and
 * no `sdp`, and a `restart`, which asks the other session to restart ICE, carries neither.
 */
@Serializable
public data class VoiceSignal(
  /**
   * The `session` of the user's join.
   */
  @SerialName("session")
  public val session: String,
  /**
   * The session that receives the signal.
   */
  @SerialName("to")
  public val to: String,
  /**
   * The kind of a WebRTC signal.
   */
  @SerialName("kind")
  public val kind: VoiceSignalKind,
  /**
   * The session description of an offer or an answer.
   */
  @SerialName("sdp")
  public val sdp: String? = null,
  /**
   * An ICE candidate, as the JSON of an `RTCIceCandidateInit` with only `candidate`, `sdpMid`,
   * `sdpMLineIndex`, and `usernameFragment`; `candidate` is empty (no more candidates) or a
   * `candidate:` attribute on one line.
   */
  @SerialName("candidate")
  public val candidate: String? = null,
)
