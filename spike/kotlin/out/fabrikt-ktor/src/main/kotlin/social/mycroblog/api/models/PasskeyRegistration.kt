package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.Map
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The start of a passkey registration.
 */
@Serializable
public data class PasskeyRegistration(
  /**
   * The `PublicKeyCredentialCreationOptionsJSON` of WebAuthn Level 3 (§5.1.8): binary
   * values in base64url, for `PublicKeyCredential.parseCreationOptionsFromJSON()`.
   */
  @SerialName("options")
  public val options: Map<String, JsonElement?>,
  /**
   * An opaque token that carries the registration to `POST /passkeys`.
   */
  @SerialName("registration_token")
  public val registrationToken: String,
  /**
   * When `registration_token` stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
)
