package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new TOTP secret to add to an authenticator app.
 */
@Serializable
public data class TotpSetup(
  /**
   * The secret in base32, for typing it into the app.
   */
  @SerialName("secret")
  public val secret: String,
  /**
   * The `otpauth://totp/...` URI of the secret.
   */
  @SerialName("uri")
  public val uri: String,
  /**
   * A QR code as an SVG path: draw `path` filled in a `size` × `size` view box (the quiet
   * zone of four modules included) on a light background.
   */
  @SerialName("qr_code")
  public val qrCode: QrCode,
  /**
   * An opaque token that carries the secret to `POST /two_factor/totp`.
   */
  @SerialName("setup_token")
  public val setupToken: String,
  /**
   * When `setup_token` stops working.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant,
)
