package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new webhook and its secret.
 */
@Serializable
public data class AdminWebhookCreated(
  /**
   * A webhook endpoint. Its secret is never shown again after it is made.
   */
  @SerialName("webhook")
  public val webhook: AdminWebhook,
  /**
   * The secret that signs the messages, `whsec_` and the base64 of 32 bytes, as the
   * Standard Webhooks libraries take it. It is shown only now.
   */
  @SerialName("secret")
  public val secret: String,
)
