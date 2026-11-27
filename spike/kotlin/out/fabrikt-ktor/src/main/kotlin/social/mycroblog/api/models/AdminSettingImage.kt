package social.mycroblog.api.models

import java.net.URI
import kotlin.Int
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The image of a `media` setting (`preview_image`, `thumbnail`): the large still variant of its
 * media file, as link previews and the about page use it.
 */
@Serializable
public data class AdminSettingImage(
  @Contextual
  @SerialName("url")
  public val url: URI,
  @SerialName("width")
  public val width: Int,
  @SerialName("height")
  public val height: Int,
)
