package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A media file, as an upload of the user (the media operations) or as an attachment of
 * a status (`Status.media_attachments`). `url`, `preview_url`, `variants`, and `blurhash`
 * are present once the file is `ready`; statuses attach only ready files. In a status,
 * `description` is the status revision's own copy of the alternative text, absent when the
 * attachment has none.
 *
 * A file of a remote status is always `ready`: the instance does not process it, so it has
 * no `variants`; `url` and `preview_url` are its media proxy URLs,
 * `remote_url` its URL on the remote server, and `meta`, `blurhash`, and `type` what the
 * remote server declared.
 */
@Serializable
public data class MediaAttachment(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The kind of media. An accepted upload is an `image`, a `video`, or an `audio` file;
   * a file that failed processing is `unknown`. `gifv` is a remote video that plays like
   * an animated image: muted, looped, without controls.
   */
  @SerialName("type")
  public val type: MediaAttachmentType,
  /**
   * The processing state: `processing` until the server has re-encoded the file,
   * then `ready`, or `failed` when the content could not be processed.
   */
  @SerialName("state")
  public val state: MediaAttachmentState,
  /**
   * The alternative text; absent when there is none.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * The blurhash (https://blurha.sh) of the image, to show while it loads.
   */
  @SerialName("blurhash")
  public val blurhash: String? = null,
  /**
   * What the server knows about the media file.
   */
  @SerialName("meta")
  public val meta: MediaMeta,
  /**
   * The renditions of a ready media file within the sizes of its purpose (see
   * `MediaUpload.purpose`), never larger than the file. `small`, `medium`, and `large` are
   * still: for an animation, its first frame, and for a video or audio file, its poster
   * (absent without one). Clients pick one by its width (`srcset`), and
   * show `animated` instead only when the user lets animations play and does not prefer
   * reduced motion.
   */
  @SerialName("variants")
  public val variants: MediaVariants? = null,
  /**
   * Where the file is served on this instance; for an upload, `variants.large.url`, or
   * `variants.animated.large.url` for an animation, and the file itself for a video or
   * audio file.
   */
  @Contextual
  @SerialName("url")
  public val url: URI? = null,
  /**
   * Where its preview is served; for an upload, `variants.small.url` (still). A video or
   * audio file without a poster has none.
   */
  @Contextual
  @SerialName("preview_url")
  public val previewUrl: URI? = null,
  /**
   * The file's URL on the remote server, for a file of a remote status; clients load `url` instead.
   */
  @Contextual
  @SerialName("remote_url")
  public val remoteUrl: URI? = null,
)
