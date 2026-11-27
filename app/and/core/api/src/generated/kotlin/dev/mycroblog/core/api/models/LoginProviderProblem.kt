package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a login provider that breaks a rule (422,
 * `/api/v1/problems/invalid-login-provider`); `reason` says which. Another 422 (a malformed
 * request) has none.
 */
@Serializable
public data class LoginProviderProblem(
  /**
   * A URI that identifies the problem type, stable for clients to branch on.
   * It is `about:blank` when the problem means no more than its HTTP status.
   */
  @SerialName("type")
  public val type: String,
  /**
   * A short summary of the problem type, localized from `Accept-Language`.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The HTTP status code.
   */
  @SerialName("status")
  public val status: Int,
  /**
   * An explanation of this occurrence, localized from `Accept-Language`.
   */
  @SerialName("detail")
  public val detail: String? = null,
  /**
   * The request ID (the `X-Request-Id` response header), for reporting the problem.
   */
  @SerialName("request_id")
  public val requestId: String? = null,
  /**
   * The member of the request that a 422 is about, by its name in the request: a property of
   * the JSON body by its path (`display_name`, `fields[].name`, `poll.options[1]`), a form
   * field, a query parameter, or a header (`Idempotency-Key`). `detail` then says what is
   * wrong with its value without naming it, for the client to show beside that field. Absent
   * when the problem is not about one member.
   */
  @SerialName("field")
  public val `field`: String? = null,
  /**
   * Why a login provider is refused. `kind`: not a known kind. `display_name`: empty, longer
   * than 100 characters, or with control characters. `issuer`: not what the kind takes (an
   * `https` URL without user info, a query, or a fragment for `oidc`; a server without a path
   * for `mastodon`; none for `github` and `discord`), or a Mastodon server that cannot be reached or serves no
   * authorization server metadata (Mastodon 4.3 or later is required). `client_id`: empty,
   * longer than 512 bytes, or with control characters, or a Mastodon server that refused to
   * register a client: register one there and give its `client_id` and `client_secret`. `client_secret`: empty, longer than 1024 bytes, or with control
   * characters. `scopes`: not scope tokens, too many, or any for `mastodon`. `conditions`:
   * claims for another kind than `oidc`, Organizations for another kind than `github`, guilds for another kind than `discord`, or
   * ones that are not valid.
   */
  @SerialName("reason")
  public val reason: LoginProviderProblemReason? = null,
)
