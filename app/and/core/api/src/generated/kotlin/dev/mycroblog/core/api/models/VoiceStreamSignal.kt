package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of a `signal` event of a voice chat room stream.
 */
@Serializable
public data class VoiceStreamSignal(
  /**
   * The session that sent the signal.
   */
  @SerialName("from")
  public val from: String,
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
   * The ICE candidate of a `candidate`.
   */
  @SerialName("candidate")
  public val candidate: String? = null,
)
