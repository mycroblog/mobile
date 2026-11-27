package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A whole token set; tokens left out keep their defaults.
 */
@Serializable
public data class SetThemeRequest(
  @SerialName("tokens")
  public val tokens: List<ThemeTokenValue>,
)
