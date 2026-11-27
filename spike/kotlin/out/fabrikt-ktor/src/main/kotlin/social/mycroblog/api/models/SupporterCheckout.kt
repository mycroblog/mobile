package social.mycroblog.api.models

import java.net.URI
import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The payment provider's hosted checkout of a subscription.
 */
@Serializable
public data class SupporterCheckout(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("subscription_id")
  public val subscriptionId: UUID,
  /**
   * Where to go next, in the same tab, which is the checkout unless it was paid already.
   */
  @Contextual
  @SerialName("redirect_url")
  public val redirectUrl: URI,
)
