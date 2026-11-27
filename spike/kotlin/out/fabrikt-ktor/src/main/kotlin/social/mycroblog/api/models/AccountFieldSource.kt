package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A metadata field of a profile as the user typed it.
 */
@Serializable
public data class AccountFieldSource(
  /**
   * The label, at most 255 characters.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The value as plain text, at most 255 characters.
   */
  @SerialName("value")
  public val `value`: String,
)
