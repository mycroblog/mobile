package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of a cost item's label; an omitted property keeps its value.
 */
@Serializable
public data class CostItemLabelRequest(
  @SerialName("name")
  public val name: String? = null,
  @SerialName("provider")
  public val provider: String? = null,
  /**
   * The fixed category of a cost; clients translate its name.
   */
  @SerialName("category")
  public val category: CostCategory? = null,
  /**
   * The note; empty removes it.
   */
  @SerialName("memo")
  public val memo: String? = null,
)
