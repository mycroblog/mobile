package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A rule of the instance. Its text and hint hold one value each, written in any
 * language and the same in every locale. Reports cite it by `id`.
 */
@Serializable
public data class InstanceRule(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The rule, on one line, as plain text.
   */
  @SerialName("text")
  public val text: String,
  /**
   * An explanation of the rule, as plain text; omitted when there is none.
   */
  @SerialName("hint")
  public val hint: String? = null,
)
