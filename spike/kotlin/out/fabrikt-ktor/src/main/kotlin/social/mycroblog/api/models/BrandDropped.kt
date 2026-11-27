package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Something the server removed from an uploaded SVG: an `element` with everything in it, or an
 * `attribute`, by its local name.
 */
@Serializable
public data class BrandDropped(
  @SerialName("kind")
  public val kind: BrandDroppedKind,
  @SerialName("name")
  public val name: String,
)
