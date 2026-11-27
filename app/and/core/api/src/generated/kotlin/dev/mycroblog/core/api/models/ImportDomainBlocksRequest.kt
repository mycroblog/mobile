package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The rows of a domain block CSV to import.
 */
@Serializable
public data class ImportDomainBlocksRequest(
  @SerialName("blocks")
  public val blocks: List<DomainBlock>,
  /**
   * The private note of every action issued.
   */
  @SerialName("private_note")
  public val privateNote: String? = null,
)
