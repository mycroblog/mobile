package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A 422 of the brand image operations. For the type `/api/v1/problems/invalid-brand`,
 * `brand_error` says why the image cannot be saved; another 422 (a malformed request) has none.
 */
@Serializable
public data class BrandProblem(
  /**
   * A URI that identifies the problem type, stable for clients to branch on.
   * It is `about:blank` when the problem means no more than its HTTP status.
   */
  @SerialName("type")
  public val type: String,
  /**
   * A short summary of the problem type, localized from `Accept-Language`.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The HTTP status code.
   */
  @SerialName("status")
  public val status: Int,
  /**
   * An explanation of this occurrence, localized from `Accept-Language`.
   */
  @SerialName("detail")
  public val detail: String? = null,
  /**
   * The request ID (the `X-Request-Id` response header), for reporting the problem.
   */
  @SerialName("request_id")
  public val requestId: String? = null,
  /**
   * The member of the request that a 422 is about, by its name in the request: a property of
   * the JSON body by its path (`display_name`, `fields[].name`, `poll.options[1]`), a form
   * field, a query parameter, or a header (`Idempotency-Key`). `detail` then says what is
   * wrong with its value without naming it, for the client to show beside that field. Absent
   * when the problem is not about one member.
   */
  @SerialName("field")
  public val `field`: String? = null,
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
  @SerialName("brand_error")
  public val brandError: BrandError? = null,
)
