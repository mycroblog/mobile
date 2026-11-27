package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The name of a list.
 */
@Serializable
public data class ListRequest(
  /**
   * 1 to 50 characters (grapheme clusters) on one line, without control characters. White
   * space around it is removed.
   */
  @SerialName("name")
  public val name: String,
)
