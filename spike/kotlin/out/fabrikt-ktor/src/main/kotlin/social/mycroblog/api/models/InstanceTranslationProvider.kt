package social.mycroblog.api.models

import java.net.URI
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A provider that this instance sends the text of posts to when a reader asks for a translation.
 */
@Serializable
public data class InstanceTranslationProvider(
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
  /**
   * The visibilities of the posts that this provider may receive.
   */
  @SerialName("visibilities")
  public val visibilities: List<Visibility>,
)
