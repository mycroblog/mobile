package social.mycroblog.api.models

import kotlin.ByteArray
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An SVG to check before setting it as a brand image.
 */
@Serializable
public data class SanitizeBrandRequest(
  /**
   * A brand image of the instance: the square `icon` (the favicon, the app icons, and the
   * instance's avatar), the wide `logo` (a wordmark that includes the instance's name), or the
   * `logo_dark` for dark backgrounds (the `logo` is used there without it).
   */
  @SerialName("kind")
  public val kind: BrandKind,
  /**
   * The SVG file, at most 262,144 bytes.
   */
  @Contextual
  @SerialName("svg")
  public val svg: ByteArray,
)
