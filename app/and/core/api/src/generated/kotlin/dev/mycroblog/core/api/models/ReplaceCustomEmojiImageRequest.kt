package dev.mycroblog.core.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The new image of a local custom emoji.
 */
@Serializable
public data class ReplaceCustomEmojiImageRequest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("media_id")
  public val mediaId: UUID,
)
