package dev.mycroblog.core.api.models

import kotlin.ByteArray
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An upload of a media file.
 */
@Serializable
public data class MediaUpload(
  /**
   * The file. Its declared type, if any, must match its content.
   */
  @Contextual
  @SerialName("file")
  public val `file`: ByteArray,
  /**
   * The alternative text. Text that is not UTF-8 or has U+0000 is a 422.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * What the file is for, which picks the sizes of its variants: an `attachment` of a
   * post (within 640 × 360, 1920 × 1080, and 3840 × 2160 pixels of area), an `avatar`
   * (cropped to squares of 96, 192, and 400 pixels around the focal point), a `header`
   * (within 750 × 250, 1500 × 500, and 3000 × 1000), or a custom `emoji` (within 64,
   * 128, and 256 pixels).
   */
  @SerialName("purpose")
  public val purpose: MediaUploadPurpose = MediaUploadPurpose.ATTACHMENT,
  /**
   * The poster of a video or audio file: an image that it shows before it plays, which
   * the client takes from the file. Its declared type is ignored; it must be an image
   * within the image limits. It is ignored for an image.
   */
  @Contextual
  @SerialName("thumbnail")
  public val thumbnail: ByteArray? = null,
)
