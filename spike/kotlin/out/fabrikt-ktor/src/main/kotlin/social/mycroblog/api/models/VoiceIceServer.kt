package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A STUN or TURN server, the `RTCIceServer` of the browser.
 */
@Serializable
public data class VoiceIceServer(
  @SerialName("urls")
  public val urls: List<String>,
  @SerialName("username")
  public val username: String? = null,
  @SerialName("credential")
  public val credential: String? = null,
)
