package social.mycroblog.api.models

import java.net.URI
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A publication of a token set, which made it the theme in effect.
 */
@Serializable
public data class AdminThemeRevision(
  /**
   * The ID of the publication, which names its stylesheet.
   */
  @SerialName("revision")
  public val revision: String,
  /**
   * The stylesheet of the theme; omitted when the set overrides nothing.
   */
  @Contextual
  @SerialName("stylesheet_url")
  public val stylesheetUrl: URI? = null,
  /**
   * A saved token set of the instance theme.
   */
  @SerialName("set")
  public val `set`: ThemeSet,
  @SerialName("published_at")
  public val publishedAt: Instant,
)
