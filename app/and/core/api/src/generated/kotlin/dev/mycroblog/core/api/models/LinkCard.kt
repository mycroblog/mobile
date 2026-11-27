package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The card of the first link in a status: the title, description, and image
 * that the linked page declares (Open Graph, X Card, and JSON-LD), fetched by the server
 * after the status was posted or received. Omitted from a status with attachments or a
 * poll, from a status whose link has no card yet, from a status whose link is on a domain
 * where a rule of the crawler stops fetching, and when the instance setting
 * `link_cards` is off. A card appears after its fetch, so a status read right after
 * it was posted may have none.
 *
 * Clients show the card as one link to `url` and, under a content warning, only once the
 * warning is opened. When the status is `sensitive`, the image is hidden until the viewer
 * shows it. A rule of the crawler can leave the card without its image.
 */
@Serializable
public data class LinkCard(
  /**
   * The page that the card links to, after redirects.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * The title of the page, as plain text.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The description of the page, as plain text; empty when it has none.
   */
  @SerialName("description")
  public val description: String,
  /**
   * The name of the site, as plain text; the host of `url` when the page names none.
   */
  @SerialName("site_name")
  public val siteName: String,
  /**
   * The author that the page names, as plain text; omitted when it names none.
   */
  @SerialName("author_name")
  public val authorName: String? = null,
  /**
   * When the page says it was published; omitted when it does not say.
   */
  @SerialName("published_at")
  public val publishedAt: Instant? = null,
  /**
   * The BCP 47 language of the page; omitted when it does not say.
   */
  @SerialName("language")
  public val language: String? = null,
  /**
   * The image of a link card, served by this server's media proxy in the small size of an
   * attachment's image. An animated image is its first frame.
   */
  @SerialName("image")
  public val image: LinkCardImage? = null,
  /**
   * An account, as clients show it.
   */
  @SerialName("author")
  public val author: Account? = null,
)
