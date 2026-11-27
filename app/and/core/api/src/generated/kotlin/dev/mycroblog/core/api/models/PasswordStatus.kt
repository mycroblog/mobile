package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Whether the authenticated user has a password.
 */
@Serializable
public data class PasswordStatus(
  /**
   * Whether the password signs in; false after the user removed it, when only the
   * passkeys sign in.
   */
  @SerialName("has_password")
  public val hasPassword: Boolean,
)
