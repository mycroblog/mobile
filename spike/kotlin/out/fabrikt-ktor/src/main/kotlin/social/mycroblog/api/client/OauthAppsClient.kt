package social.mycroblog.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.patch
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
import social.mycroblog.api.models.CreateOAuthAppRequest
import social.mycroblog.api.models.IssueOAuthAppTokenRequest
import social.mycroblog.api.models.OAuthApp
import social.mycroblog.api.models.OAuthAppCreated
import social.mycroblog.api.models.OAuthAppList
import social.mycroblog.api.models.OAuthAppSecret
import social.mycroblog.api.models.OAuthAppToken
import social.mycroblog.api.models.UpdateOAuthAppRequest

public class OauthAppsClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the OAuth apps that the authenticated user registered
   * The apps that the user registered and has not deleted, oldest first, and whether the user
   * may register one now (`can_create`).
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthAppList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listOAuthApps(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OAuthAppList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps"""

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
   * Register an OAuth app
   * Registers a public or confidential app of the user for a bot, a script, or a client of
   * their own. A confidential app's `client_secret` is in this response only.
   * Other users authorize the app at `/oauth/authorize` as any third-party app, and the user
   * may issue its tokens to their own account with `POST /oauth_apps/{id}/tokens`. The app
   * stops working while the user cannot use the server (suspended or deleted).
   *
   * A setting that breaks a rule is a 422 naming it, a user with 25 apps a 409
   * `/api/v1/problems/too-many-oauth-apps`, and a server that does not let users register
   * apps (the instance setting `user_app_registration`) a 403
   * `/api/v1/problems/oauth-app-registration-disabled`. A user registers at most 5 apps an
   * hour; past that the answer is 429.
   *
   *
   * Parameters:
   * 	 @param createOAuthAppRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthAppCreated] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createOAuthApp(createOAuthAppRequest: CreateOAuthAppRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OAuthAppCreated> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createOAuthAppRequest)
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
   * Get an OAuth app that the authenticated user registered
   * An unknown or deleted app, or one that another user registered, is a 404.
   *
   * Parameters:
   * 	 @param id The ID of the OAuth app (not its `client_id`).
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthApp] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getOAuthApp(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OAuthApp> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps/${id}"""

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
   * Change an OAuth app
   * Changes what the request names and keeps the rest, checked as for a new app; a setting
   * that breaks a rule is a 422 naming it. The type (public or confidential) never changes.
   * The tokens of the app keep working. An unknown or deleted app, or one that another user
   * registered, is a 404.
   *
   *
   * Parameters:
   * 	 @param updateOAuthAppRequest 
   * 	 @param id The ID of the OAuth app (not its `client_id`).
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthApp] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun updateOAuthApp(
    updateOAuthAppRequest: UpdateOAuthAppRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OAuthApp> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps/${id}"""

    return try {
      val response = httpClient.patch(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(updateOAuthAppRequest)
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
   * Delete an OAuth app
   * Deletes the app for good and revokes every token of it, also those of the other users
   * who authorized it. An unknown or deleted app, or one that another user registered, is a
   * 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the OAuth app (not its `client_id`).
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
  public suspend fun deleteOAuthApp(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps/${id}"""

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

  /**
   * Issue a new secret to a confidential OAuth app
   * The old secret stops working at once: refreshing the app's tokens takes the new one,
   * which is in this response only. The tokens of the app keep working. A public app is a
   * 409 `/api/v1/problems/public-oauth-app`. An unknown or deleted app, or one that another
   * user registered, is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the OAuth app (not its `client_id`).
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthAppSecret] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun rotateOAuthAppSecret(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OAuthAppSecret> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps/${id}/rotate_secret"""

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
   * Issue tokens of an OAuth app to the authenticated user
   * Issues an access token and a refresh token of the app for the user's own account,
   * without the consent screen and a redirect: the usual pair with the usual
   * lifetimes, refreshed and revoked at the token endpoints as any other. A scope beyond the
   * app's is a 422 naming `scope`, and an app that an administrator disabled a 409
   * `/api/v1/problems/oauth-app-disabled`. An unknown or deleted app, or one that another
   * user registered, is a 404.
   *
   *
   * Parameters:
   * 	 @param issueOAuthAppTokenRequest 
   * 	 @param id The ID of the OAuth app (not its `client_id`).
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OAuthAppToken] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun issueOAuthAppToken(
    issueOAuthAppTokenRequest: IssueOAuthAppTokenRequest,
    id: UUID,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OAuthAppToken> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/oauth_apps/${id}/tokens"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(issueOAuthAppTokenRequest)
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
