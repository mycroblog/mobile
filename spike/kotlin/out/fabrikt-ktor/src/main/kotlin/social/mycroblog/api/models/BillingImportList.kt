package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The billing sources and the state of their imports.
 */
@Serializable
public data class BillingImportList(
  @SerialName("items")
  public val items: List<BillingImport>,
)
