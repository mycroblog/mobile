package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The brand images of the instance; a kind that uses the built-in images is omitted.
 */
@Serializable
public data class AdminBrands(
  /**
   * A brand image as the administrators see it: the image of `Instance` with the background color
   * of the icon and when it was set, and, in the answer to `setAdminBrand`, what the sanitizer
   * removed from the upload.
   */
  @SerialName("icon")
  public val icon: AdminBrandImage? = null,
  /**
   * A brand image as the administrators see it: the image of `Instance` with the background color
   * of the icon and when it was set, and, in the answer to `setAdminBrand`, what the sanitizer
   * removed from the upload.
   */
  @SerialName("logo")
  public val logo: AdminBrandImage? = null,
  /**
   * A brand image as the administrators see it: the image of `Instance` with the background color
   * of the icon and when it was set, and, in the answer to `setAdminBrand`, what the sanitizer
   * removed from the upload.
   */
  @SerialName("logo_dark")
  public val logoDark: AdminBrandImage? = null,
)
