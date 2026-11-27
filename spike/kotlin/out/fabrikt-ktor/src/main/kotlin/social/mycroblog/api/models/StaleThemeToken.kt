package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A stored override that no longer applies, as it was saved, and why.
 */
@Serializable
public data class StaleThemeToken(
  @SerialName("token")
  public val token: String,
  @SerialName("light")
  public val light: String? = null,
  @SerialName("dark")
  public val dark: String? = null,
  @SerialName("value")
  public val `value`: String? = null,
  @SerialName("reason")
  public val reason: StaleThemeTokenReason,
)
