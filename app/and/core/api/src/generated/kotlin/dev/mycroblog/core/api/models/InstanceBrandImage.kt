package dev.mycroblog.core.api.models

import java.net.URI
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A brand image that the administrators set: the sanitized SVG and its PNG renditions, at URLs
 * under `/brand/` on the server that name the revision, so their content never changes and
 * they may be cached for good. Show the SVG only as an image (`<img>` or a CSS background),
 * never inline. `Instance` has the `icon`, the `logo` (a wordmark that includes the instance's
 * name, which clients show instead of the name), and the `logo_dark` for dark backgrounds
 * (`logo` is used there without it); each is omitted while the instance uses the built-in
 * image.
 */
@Serializable
public data class InstanceBrandImage(
  @SerialName("revision")
  public val revision: String,
  @Contextual
  @SerialName("svg_url")
  public val svgUrl: URI,
  /**
   * The width of the SVG's viewBox, rounded.
   */
  @SerialName("width")
  public val width: Int,
  /**
   * The height of the SVG's viewBox, rounded.
   */
  @SerialName("height")
  public val height: Int,
  @SerialName("renditions")
  public val renditions: List<BrandFile>,
)
