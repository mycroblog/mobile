package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What an import of domain blocks did.
 */
@Serializable
public data class DomainBlockImportResult(
  /**
   * The actions issued, in the order of the blocks.
   */
  @SerialName("issued")
  public val issued: List<AdminDomainAction>,
  /**
   * The blocks that issued nothing, in their order.
   */
  @SerialName("skipped")
  public val skipped: List<DomainBlockImportSkipped>,
)
