package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The browser's answer to a confirmation with a passkey, which confirms an operation.
 */
@Serializable
public data class PasskeyAssertion(
  /**
   * The `confirmation_token` of `POST /passkeys/confirmation`.
   */
  @SerialName("confirmation_token")
  public val confirmationToken: String,
  /**
   * The `AuthenticationResponseJSON` of WebAuthn Level 3 (§5.1): what
   * `PublicKeyCredential.toJSON()` returns for the passkey.
   */
  @SerialName("credential")
  public val credential: Map<String, JsonElement?>,
)
