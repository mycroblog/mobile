package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new OAuth app, with the secret of a confidential app.
 */
@Serializable
public data class OAuthAppCreated(
  /**
   * An OAuth app that the authenticated user registered. Like every third-party app,
   * it gets no first-party-only scope and calls no account security operation.
   */
  @SerialName("app")
  public val app: OAuthApp,
  /**
   * The secret of a confidential app, `my_cs_` and 43 base64url characters; absent for a
   * public app. It is shown only now: the server keeps only its SHA-256 digest.
   */
  @SerialName("client_secret")
  public val clientSecret: String? = null,
)
