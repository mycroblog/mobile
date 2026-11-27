package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The moving renditions of an animation, absent for a still image: animated WebP for
 * `small` and `medium`, and a GIF for `large`.
 */
@Serializable
public data class MediaAnimatedVariants(
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
)
