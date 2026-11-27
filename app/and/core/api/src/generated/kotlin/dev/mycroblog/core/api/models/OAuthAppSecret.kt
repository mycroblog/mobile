package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new secret of a confidential OAuth app.
 */
@Serializable
public data class OAuthAppSecret(
  /**
   * The new secret, as in `OAuthAppCreated`. It is shown only now.
   */
  @SerialName("client_secret")
  public val clientSecret: String,
)
