package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Why a brand image cannot be saved. `code` is `malformed` (not well-formed XML in UTF-8),
 * `doctype` (a DOCTYPE or an entity declaration), `not_svg` (the root is not `svg`),
 * `no_viewbox` (no `viewBox` of four numbers with a positive size), `too_large` (the SVG is
 * over 262,144 bytes, or 131,072 once sanitized, or a PNG is over 1,048,576), `too_many_elements`
 * (over 5,000), `too_deep` (elements nested over 32 deep), `too_many_uses` (over 200 `use`
 * elements, references that expand to too many elements, or a reference cycle),
 * `aspect_ratio` (an icon that is not square within 1%, a logo not 1 to 8 times as wide as
 * it is high), `text` (`text` or `tspan`: convert the text to outlines), `unsafe_css` (CSS with
 * `@import`, `@font-face`, `@namespace`, a `url()` other than `url(#id)`, `expression(`, an
 * escape, or `<`), `missing_rendition`, `unexpected_rendition` (a rendition of another
 * kind), `invalid_png`, `dimensions` (a PNG of the wrong size), `not_opaque` (a rendition that
 * must be opaque has transparent pixels), or `invalid_color` (the background is not `#rrggbb`,
 * or a logo has one). `rendition` names the file, `svg` for the SVG.
 */
@Serializable
public data class BrandError(
  @SerialName("code")
  public val code: BrandErrorCode,
  @SerialName("rendition")
  public val rendition: String? = null,
  /**
   * More about the problem, in English.
   */
  @SerialName("detail")
  public val detail: String? = null,
)
