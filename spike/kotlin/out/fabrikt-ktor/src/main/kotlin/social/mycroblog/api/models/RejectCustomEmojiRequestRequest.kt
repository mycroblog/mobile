package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rejection of a custom emoji request.
 */
@Serializable
public data class RejectCustomEmojiRequestRequest(
  /**
   * Why, up to 500 characters, which the requester sees; empty or absent for none.
   */
  @SerialName("reason")
  public val reason: String? = null,
)
