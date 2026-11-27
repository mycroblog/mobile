package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Int
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A PNG rendition of a brand image, served at a URL whose content never changes.
 */
@Serializable
public data class BrandFile(
  /**
   * A PNG rendition of a brand image, drawn from its SVG for the places that take no SVG:
   * `favicon-32` and `favicon-48` (32 and 48 pixels), `apple-touch-180` (180 pixels, opaque, the
   * iOS home screen), `pwa-192` and `pwa-512` (the app icons), and `maskable-512` (512 pixels,
   * opaque, the mark within the middle 80%) of the icon; `logo-mail` of the logo and
   * `logo-dark-mail` of the dark logo (96 pixels high and as wide as the aspect ratio makes it, up
   * to 600 pixels, which then sets the height; a pixel off is accepted), shown in emails; and
   * `preview-1200x630` of the logo (1200 × 630 pixels, opaque), the link preview image when the
   * instance has no `preview_image`.
   */
  @SerialName("rendition")
  public val rendition: BrandRendition,
  @Contextual
  @SerialName("url")
  public val url: URI,
  @SerialName("width")
  public val width: Int,
  @SerialName("height")
  public val height: Int,
)
