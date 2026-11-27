package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The cover image of an article: one of the user's own uploads that finished processing (422
 * otherwise), with its alternative text; omitted for none.
 */
@Serializable
public data class ArticleCoverInput(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The alternative text; omitted or empty for none. While the instance requires alt
   * text (`limits.require_media_description` of `GET /instance`), a post or an edit
   * whose media file has none, or only spaces, is a 422.
   */
  @SerialName("description")
  public val description: String? = null,
)
