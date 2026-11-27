package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A session of the user in a voice chat room: the room, the new session, and its grant. The
 * client sends the session's heartbeats every 15 seconds.
 */
@Serializable
public data class VoiceRoomJoin(
  /**
   * An open voice chat room: its settings, its host, and its participants in the order they
   * joined. Everyone who sees the room sees its participants.
   */
  @SerialName("room")
  public val room: VoiceRoom,
  /**
   * The ID of the session, which replaces the previous session of the user in the room.
   */
  @SerialName("session")
  public val session: String,
  /**
   * What the client of a session needs to connect to the audio of a room. `kind` is the method
   * of the server (`p2p` or `sfu`); the client asks for a new grant before `expires_at`.
   *
   * With `sfu`, `url` is the `wss:` URL of the server's media node that holds the room and `token`
   * a ticket for it, which connects once and only within 30 seconds. The client opens a
   * WebSocket to `url` with the query parameter `ticket` set to `token`
   * (`GET <url>?ticket=<token>`, an upgrade to the WebSocket protocol; browsers cannot send
   * headers on a WebSocket, so the ticket travels in the URL). An expired or used ticket, or
   * one for another node, is refused. Each message is a JSON text frame with a version `v` (1)
   * and a `type`: the client sends a `VoiceSFUClientMessage` and receives a
   * `VoiceSFUNodeMessage`. The node always makes the offer, on one `RTCPeerConnection` for the
   * session: its first `offer` has one transceiver that receives the user's microphone and one
   * that sends the audio of each speaker, and it offers again only when the speakers change. A
   * client that changes network sends `restart` and gets an offer that restarts ICE. When the
   * WebSocket closes while the connection lives, or on `migrate`, the client asks for a new
   * grant (`POST /voice/rooms/{id}/grant`) and connects to its `url` with the same session.
   */
  @SerialName("grant")
  public val grant: VoiceGrant,
)
