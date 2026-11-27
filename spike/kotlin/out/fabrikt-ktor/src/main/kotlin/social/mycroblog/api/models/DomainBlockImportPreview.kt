package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What importing each row of a domain block CSV would do, in row order.
 */
@Serializable
public data class DomainBlockImportPreview(
  @SerialName("rows")
  public val rows: List<DomainBlockImportRow>,
)
