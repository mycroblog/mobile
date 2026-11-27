package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The node's offer for the session's connection, which the client answers with an `answer`:
 * the first one on connecting, then one each time the speakers change or ICE restarts.
 */
@SerialName("offer")
@Serializable
public data class VoiceSFUOffer(
  /**
   * The version of the messages of the media node's WebSocket. A message of a version that the
   * receiver does not know is refused with an `error`.
   */
  @SerialName("v")
  public val v: Int,
  /**
   * The session description of the offer.
   */
  @SerialName("sdp")
  public val sdp: String,
  /**
   * Which transceiver carries the audio of which speaker. The transceiver that receives the
   * user's microphone is not listed.
   */
  @SerialName("tracks")
  public val tracks: List<VoiceSFUTrack>,
) : VoiceSFUNodeMessage
