package social.mycroblog.api.models

import java.net.URI
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The service that translated a post, for the attribution next to the translation.
 */
@Serializable
public data class TranslationProvider(
  /**
   * The identifier of the provider on this server.
   */
  @SerialName("id")
  public val id: String,
  /**
   * The name to show.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The address of the provider; omitted when there is none.
   */
  @Contextual
  @SerialName("url")
  public val url: URI? = null,
  /**
   * The address of the logo to show, for a provider that asks for one; omitted when there is none.
   */
  @Contextual
  @SerialName("logo_url")
  public val logoUrl: URI? = null,
)
