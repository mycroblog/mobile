package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.EnableTotpRequest
import dev.mycroblog.core.api.models.RecoveryCodes
import dev.mycroblog.core.api.models.TotpSetup
import dev.mycroblog.core.api.models.TwoFactor
import dev.mycroblog.core.api.models.TwoFactorCodeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException

public class TwoFactorClient(
  private val httpClient: HttpClient,
) {
  /**
   * Get where the authenticated user's two-factor authentication stands
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.TwoFactor] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getTwoFactor(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<TwoFactor> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/two_factor"""

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
   * Generate a TOTP secret for the authenticated user to add to an authenticator app
   * A new secret (RFC 6238: HMAC-SHA-1, six digits, 30-second steps) as text, as an
   * `otpauth://` URI, and as a QR code of the URI, with a `setup_token` that carries the
   * secret to `POST /two_factor/totp` for 15 minutes. Nothing is stored until then. A user
   * who has two-factor authentication enabled gets a 409
   * `/api/v1/problems/two-factor-enabled`.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.TotpSetup] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun setUpTotp(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<TotpSetup> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/two_factor/totp/setup"""

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
   * Enable two-factor authentication with the secret of a setup
   * Enrolls the secret of `setup_token` once `code` shows that the app has it, and returns
   * the recovery codes, which are shown only this once. From then on, signing in asks for
   * a code after the password. A wrong code is a 422
   * `/api/v1/problems/invalid-two-factor-code`, an expired or unknown setup a 422
   * `/api/v1/problems/totp-setup-expired`, and too many wrong codes a 429.
   *
   *
   * Parameters:
   * 	 @param enableTotpRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.RecoveryCodes] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun enableTotp(enableTotpRequest: EnableTotpRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<RecoveryCodes> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/two_factor/totp"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(enableTotpRequest)
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
   * Disable two-factor authentication
   * Ends the enrollment once `code`, a code of the app or an unused recovery code, confirms
   * it. A wrong code is a 422 `/api/v1/problems/invalid-two-factor-code`, too many wrong
   * codes a 429, and a user without two-factor authentication a 409
   * `/api/v1/problems/two-factor-not-enabled`.
   *
   *
   * Parameters:
   * 	 @param twoFactorCodeRequest 
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
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.TwoFactor] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun disableTotp(
    twoFactorCodeRequest: TwoFactorCodeRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<TwoFactor> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/two_factor/totp/disable"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(twoFactorCodeRequest)
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
   * Replace the recovery codes
   * Returns new recovery codes, shown only this once, once `code` confirms it; the previous
   * ones stop working and the app keeps working. The problems are those of
   * `POST /two_factor/totp/disable`.
   *
   *
   * Parameters:
   * 	 @param twoFactorCodeRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.RecoveryCodes] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun regenerateRecoveryCodes(twoFactorCodeRequest: TwoFactorCodeRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<RecoveryCodes> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/two_factor/recovery_codes"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(twoFactorCodeRequest)
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
