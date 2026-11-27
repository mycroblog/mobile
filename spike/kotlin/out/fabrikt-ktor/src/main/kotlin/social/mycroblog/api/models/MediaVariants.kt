package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The renditions of a ready media file within the sizes of its purpose (see
 * `MediaUpload.purpose`), never larger than the file. `small`, `medium`, and `large` are
 * still: for an animation, its first frame, and for a video or audio file, its poster
 * (absent without one). Clients pick one by its width (`srcset`), and
 * show `animated` instead only when the user lets animations play and does not prefer
 * reduced motion.
 */
@Serializable
public data class MediaVariants(
  /**
   * A rendition of a media file, without metadata. Its URL never serves other content, so
   * it may be cached for good. `content_type`, `width`, `height`, and `size` are present
   * for an upload, and absent where unknown for a remote file served through the media
   * proxy.
   */
  @SerialName("small")
  public val small: MediaVariant,
  /**
   * A rendition of a media file, without metadata. Its URL never serves other content, so
   * it may be cached for good. `content_type`, `width`, `height`, and `size` are present
   * for an upload, and absent where unknown for a remote file served through the media
   * proxy.
   */
  @SerialName("medium")
  public val medium: MediaVariant,
  /**
   * A rendition of a media file, without metadata. Its URL never serves other content, so
   * it may be cached for good. `content_type`, `width`, `height`, and `size` are present
   * for an upload, and absent where unknown for a remote file served through the media
   * proxy.
   */
  @SerialName("large")
  public val large: MediaVariant,
  /**
   * The moving renditions of an animation, absent for a still image: animated WebP for
   * `small` and `medium`, and a GIF for `large`.
   */
  @SerialName("animated")
  public val animated: MediaAnimatedVariants? = null,
)
