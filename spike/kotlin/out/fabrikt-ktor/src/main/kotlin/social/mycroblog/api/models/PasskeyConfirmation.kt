package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.Map
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The start of a confirmation with a passkey.
 */
@Serializable
public data class PasskeyConfirmation(
  /**
   * The `PublicKeyCredentialRequestOptionsJSON` of WebAuthn Level 3 (§5.1.9): binary
   * values in base64url, for `PublicKeyCredential.parseRequestOptionsFromJSON()`.
   */
  @SerialName("options")
  public val options: Map<String, JsonElement?>,
  /**
   * An opaque token that carries the confirmation to the operation it confirms.
   */
  @SerialName("confirmation_token")
  public val confirmationToken: String,
  /**
   * When `confirmation_token` stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
)
