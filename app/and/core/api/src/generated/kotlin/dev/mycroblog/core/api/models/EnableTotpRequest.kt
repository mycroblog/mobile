package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The setup to enroll and a code of the app that shows it has the secret.
 */
@Serializable
public data class EnableTotpRequest(
  /**
   * The `setup_token` of `POST /two_factor/totp/setup`.
   */
  @SerialName("setup_token")
  public val setupToken: String,
  /**
   * The six-digit code that the app shows now.
   */
  @SerialName("code")
  public val code: String,
)
