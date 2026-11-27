package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new client secret of a login provider.
 */
@Serializable
public data class RotateLoginProviderSecretRequest(
  /**
   * The client secret that the provider gave, up to 1024 bytes. It is never shown again.
   */
  @SerialName("client_secret")
  public val clientSecret: String,
)
