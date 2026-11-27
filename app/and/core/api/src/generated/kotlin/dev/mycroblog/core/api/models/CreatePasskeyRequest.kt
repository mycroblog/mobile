package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The browser's answer to a passkey registration.
 */
@Serializable
public data class CreatePasskeyRequest(
  /**
   * The `registration_token` of `POST /passkeys/registration`.
   */
  @SerialName("registration_token")
  public val registrationToken: String,
  /**
   * The `RegistrationResponseJSON` of WebAuthn Level 3 (§5.1): what
   * `PublicKeyCredential.toJSON()` returns for the new credential.
   */
  @SerialName("credential")
  public val credential: Map<String, JsonElement?>,
)
