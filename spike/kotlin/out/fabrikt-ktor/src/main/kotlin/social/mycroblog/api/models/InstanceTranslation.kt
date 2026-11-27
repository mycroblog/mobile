package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the instance offers for translating posts, and what it sends where. With `browser` false and
 * no `providers`, nothing can be translated and clients show no action for it.
 */
@Serializable
public data class InstanceTranslation(
  /**
   * Whether clients may use the translation built into the browser, where the browser has one. It
   * sends the text of a post to neither this server nor a provider, and works on every visibility.
   */
  @SerialName("browser")
  public val browser: Boolean,
  /**
   * The enabled providers that run through this server, the first tried first.
   */
  @SerialName("providers")
  public val providers: List<InstanceTranslationProvider>,
  /**
   * Whether moderators may send the text of a report, its posts and its comment, to the providers.
   * Each provider still receives only the visibilities it lists, and a comment counts as `private`.
   * Absent on a server without translation, where it is false.
   */
  @SerialName("reports_to_server")
  public val reportsToServer: Boolean? = null,
)
