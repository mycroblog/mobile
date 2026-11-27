package dev.mycroblog.core.api.models

import java.net.URI
import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The payment provider's hosted checkout of a tip.
 */
@Serializable
public data class SupporterTipCheckout(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("tip_id")
  public val tipId: UUID,
  /**
   * Where to go next, in the same tab, which is the checkout unless the open tip was paid already.
   */
  @Contextual
  @SerialName("redirect_url")
  public val redirectUrl: URI,
)
