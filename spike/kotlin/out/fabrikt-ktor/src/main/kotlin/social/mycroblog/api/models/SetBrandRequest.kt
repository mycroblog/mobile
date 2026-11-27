package social.mycroblog.api.models

import kotlin.ByteArray
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A brand image: the SVG and every PNG rendition of its kind, each in the part named after the
 * rendition with `_` for `-` (`BrandRendition`). The icon takes `favicon_32`, `favicon_48`,
 * `apple_touch_180`, `pwa_192`, `pwa_512`, and `maskable_512`; the logo `logo_mail` and
 * `preview_1200x630`; the dark logo `logo_dark_mail`. Other parts are ignored.
 */
@Serializable
public data class SetBrandRequest(
  /**
   * The SVG file, at most 262,144 bytes.
   */
  @Contextual
  @SerialName("svg")
  public val svg: ByteArray,
  /**
   * The `#rrggbb` color behind the opaque renditions of the icon; the theme's light surface
   * color when it is left out. A logo takes none.
   */
  @SerialName("background")
  public val background: String? = null,
  @Contextual
  @SerialName("favicon_32")
  public val favicon32: ByteArray? = null,
  @Contextual
  @SerialName("favicon_48")
  public val favicon48: ByteArray? = null,
  @Contextual
  @SerialName("apple_touch_180")
  public val appleTouch180: ByteArray? = null,
  @Contextual
  @SerialName("pwa_192")
  public val pwa192: ByteArray? = null,
  @Contextual
  @SerialName("pwa_512")
  public val pwa512: ByteArray? = null,
  @Contextual
  @SerialName("maskable_512")
  public val maskable512: ByteArray? = null,
  @Contextual
  @SerialName("logo_mail")
  public val logoMail: ByteArray? = null,
  @Contextual
  @SerialName("logo_dark_mail")
  public val logoDarkMail: ByteArray? = null,
  @Contextual
  @SerialName("preview_1200x630")
  public val preview1200x630: ByteArray? = null,
)
