package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new secret of a webhook.
 */
@Serializable
public data class AdminWebhookSecret(
  /**
   * The new secret, as in `AdminWebhookCreated`. It is shown only now.
   */
  @SerialName("secret")
  public val secret: String,
)
