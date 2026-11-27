package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.CreatePasskeyRequest
import dev.mycroblog.core.api.models.Passkey
import dev.mycroblog.core.api.models.PasskeyConfirmation
import dev.mycroblog.core.api.models.PasskeyList
import dev.mycroblog.core.api.models.PasskeyRegistration
import dev.mycroblog.core.api.models.PasskeyRegistrationRequest
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

public class PasskeysClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the authenticated user's passkeys
   * The passkeys that sign the user in, oldest first; there are at most 20.
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.PasskeyList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listPasskeys(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<PasskeyList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/passkeys"""

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
   * Register a passkey with the browser's response to a registration
   * Verifies `credential`, the browser's answer to the `options` of
   * `POST /passkeys/registration`, and stores the passkey under the name given there. From
   * then on the passkey signs in on the server's sign-in page instead of the password; a
   * user with two-factor authentication still enters a code after it. A response that does
   * not verify (another origin, no user verification, a credential that is registered
   * already) is a 422 `/api/v1/problems/invalid-passkey`, an expired or unknown
   * `registration_token` a 422 `/api/v1/problems/passkey-registration-expired`, and a user
   * with 20 passkeys a 409 `/api/v1/problems/too-many-passkeys`.
   *
   *
   * Parameters:
   * 	 @param createPasskeyRequest 
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
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.Passkey] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createPasskey(
    createPasskeyRequest: CreatePasskeyRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Passkey> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/passkeys"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createPasskeyRequest)
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

  /**
   * Start the registration of a passkey
   * While registration and sign-in are limited to the login providers
   * (`login_providers_only` of `GET /instance`), it is a 403
   * `/api/v1/problems/login-providers-only`: a new passkey could not sign in.
   * Checks the user's current `password` or a `passkey` of the user (a user without a
   * password confirms with a passkey) and returns the options of a WebAuthn registration (a
   * discoverable credential with user verification, no attestation) for the browser's
   * `navigator.credentials.create()`, with a `registration_token` that carries them and
   * `name` to `POST /passkeys` for 5 minutes. Nothing is stored until then. A wrong
   * password is a 422 `/api/v1/problems/wrong-password`, a passkey that does not verify a
   * 422 `/api/v1/problems/invalid-passkey`, and an expired confirmation a 422
   * `/api/v1/problems/passkey-confirmation-expired`; at most 5 wrong ones per user are
   * accepted every hour. A user with 20 passkeys gets a 409
   * `/api/v1/problems/too-many-passkeys`.
   * An `external_confirmation` from a linked login provider
   * (`POST /linked_identities/confirmation`) takes the place of the password; an unknown,
   * expired, or used one, or one for another operation, is a 422
   * `/api/v1/problems/external-confirmation-expired`.
   *
   *
   * Parameters:
   * 	 @param passkeyRegistrationRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.PasskeyRegistration] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun beginPasskeyRegistration(passkeyRegistrationRequest: PasskeyRegistrationRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<PasskeyRegistration> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/passkeys/registration"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(passkeyRegistrationRequest)
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
   * Start a confirmation with a passkey
   * Returns the options of a WebAuthn authentication (with user verification) for the
   * browser's `navigator.credentials.get()`, with a `confirmation_token` that carries them
   * for 5 minutes, once, in the `passkey` of an operation that a passkey of the user confirms
   * in place of the current password: deleting the account, deactivating it, moving it, changing
   * or removing the password, registering a passkey, and linking an identity. Nothing is stored.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.PasskeyConfirmation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun beginPasskeyConfirmation(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<PasskeyConfirmation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/passkeys/confirmation"""

    return try {
      val response = httpClient.post(url) {
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
   * Remove a passkey
   * The passkey no longer signs in. An unknown or removed passkey is a 404, and the last
   * passkey of a user without a password and without a linked account a 409
   * `/api/v1/problems/last-sign-in-method`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the passkey.
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
  public suspend fun deletePasskey(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/passkeys/${id}"""

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
