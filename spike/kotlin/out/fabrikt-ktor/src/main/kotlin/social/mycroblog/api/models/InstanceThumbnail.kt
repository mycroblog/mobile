package social.mycroblog.api.models

import java.net.URI
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The image that represents the instance (the instance setting `thumbnail`): the large still
 * variant of the media file. Omitted when there is none, or while the file is processing.
 */
@Serializable
public data class InstanceThumbnail(
  @Contextual
  @SerialName("url")
  public val url: URI,
  @SerialName("width")
  public val width: Int,
  @SerialName("height")
  public val height: Int,
  /**
   * The alt text (the instance text `thumbnail_alt`); omitted when there is none.
   */
  @SerialName("alt")
  public val alt: String? = null,
)
