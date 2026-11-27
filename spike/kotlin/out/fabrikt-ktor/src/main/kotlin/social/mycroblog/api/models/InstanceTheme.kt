package social.mycroblog.api.models

import java.net.URI
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The instance's theme, for clients that do not load the server's HTML shell: the
 * stylesheet to apply and the overridden tokens. `Instance.theme` omits it when the
 * instance has no theme.
 */
@Serializable
public data class InstanceTheme(
  @SerialName("revision")
  public val revision: String,
  @Contextual
  @SerialName("stylesheet_url")
  public val stylesheetUrl: URI,
  @SerialName("tokens")
  public val tokens: List<ThemeTokenValue>,
)
