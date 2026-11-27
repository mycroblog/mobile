package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The alt text of an attached media file in the language of the translation.
 */
@Serializable
public data class StatusTranslationAttachment(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The alt text.
   */
  @SerialName("description")
  public val description: String,
)
