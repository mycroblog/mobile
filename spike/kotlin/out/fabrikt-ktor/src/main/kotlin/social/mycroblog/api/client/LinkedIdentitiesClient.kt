package social.mycroblog.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.ExternalAuthorizationStart
import social.mycroblog.api.models.ExternalConfirmationRequest
import social.mycroblog.api.models.LinkIdentityRequest
import social.mycroblog.api.models.LinkedIdentityList

public class LinkedIdentitiesClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the authenticated user's linked accounts and the login providers
   * The accounts at external login providers that the user links, which sign the user in and
   * confirm the user's operations, oldest first, and the login providers that users sign in
   * with, which the user can link an account at (`allow_link`) or confirm with. A server
   * without login providers answers two empty lists.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.LinkedIdentityList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listLinkedIdentities(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<LinkedIdentityList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/linked_identities"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Start linking an account at a login provider
   * Checks the confirmation (`password`, `passkey`, or `external_confirmation` of the
   * `link` operation) and returns the `start_url` that the browser opens to sign in at the
   * provider. The browser must be signed in to the server's sign-in pages as the same user;
   * one that is not signs in first. The provider sends the browser back to `return_to` with
   * the outcome in the fragment, next to `provider`: `linked=<provider ID>`, or `external_error=<reason>` where
   * the reason is `denied` (the provider's conditions refuse the account), `linked_elsewhere`
   * (another user links the account), `already_linked`, `unavailable`, `failed`,
   * `rate_limited`, or `expired`. A provider that is unknown, disabled, or does not allow
   * links is a 422 `/api/v1/problems/login-provider-unavailable`, a provider that the user
   * links an account at already a 409 `/api/v1/problems/login-provider-linked`, and a
   * wrong confirmation as for the other confirmed operations; at most 5 wrong ones per user
   * are accepted every hour.
   *
   *
   * Parameters:
   * 	 @param linkIdentityRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ExternalAuthorizationStart] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun beginLinkIdentity(linkIdentityRequest: LinkIdentityRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<ExternalAuthorizationStart> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/linked_identities"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(linkIdentityRequest)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Start a confirmation with a linked login provider
   * Returns the `start_url` that the browser opens to sign in again at a provider that the
   * user links an account at, which confirms `operation` in place of the password or a
   * passkey. The browser must be signed in to the server's sign-in pages as the same user;
   * one that is not signs in first. An OpenID Connect provider is asked to authenticate the
   * user again, and an authentication older than the request does not count. The provider
   * sends the browser back to `return_to` with the outcome in the fragment, next to
   * `operation` and `provider`: `external_confirmation=<token>`, which the operation takes
   * once in its `external_confirmation` within 10 minutes, or `external_error=<reason>` where
   * the reason is `mismatch` (the user signed in with an account that the user does not link),
   * `denied`, `unavailable`, `failed`, `rate_limited`, or `expired`. A mismatch and a token
   * that does not verify count against the wrong confirmations of the operation. A provider
   * that the user links no account at, or that is disabled, is a 422
   * `/api/v1/problems/login-provider-unavailable`.
   *
   *
   * Parameters:
   * 	 @param externalConfirmationRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ExternalAuthorizationStart] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun beginExternalConfirmation(externalConfirmationRequest: ExternalConfirmationRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<ExternalAuthorizationStart> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/linked_identities/confirmation"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(externalConfirmationRequest)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Unlink an account at a login provider
   * The account no longer signs the user in or confirms the user's operations. An unknown
   * or unlinked link is a 404, and the last way to sign in of a user without a password and
   * without a passkey a 409 `/api/v1/problems/last-sign-in-method`. While registration and
   * sign-in are limited to the login providers (`login_providers_only` of `GET /instance`), a
   * password and a passkey do not sign in, so the user's last link to a provider that signs in
   * is a 409 too.
   *
   *
   * Parameters:
   * 	 @param id The ID of the link.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unlinkIdentity(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/linked_identities/${id}"""

    return try {
      val response = httpClient.delete(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }
}
