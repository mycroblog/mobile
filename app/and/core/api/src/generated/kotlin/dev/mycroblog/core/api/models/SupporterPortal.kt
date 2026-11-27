package dev.mycroblog.core.api.models

import java.net.URI
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The payment provider's portal of the user's payment methods and invoices.
 */
@Serializable
public data class SupporterPortal(
  /**
   * The portal, to open in the same tab.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
)
