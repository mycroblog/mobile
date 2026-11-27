package social.mycroblog.api.client

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
import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.AppealModerationActionRequest
import social.mycroblog.api.models.ModerationAction
import social.mycroblog.api.models.ModerationActionPage

public class ModerationClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the moderation actions on the authenticated user's account
   * The actions that moderators took on the user's own account, newest first,
   * lifted and expired ones included unless `active` is set, also those that the user was
   * not notified of. Each carries what the moderators chose to tell the user; never their
   * private note, the report the action follows from, or who filed it. `kind_label` and
   * `reason_category_label` are in the user's language.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param active Keep only the actions in effect now, neither lifted nor expired.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ModerationActionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listOwnModerationActions(
    limit: Int? = null,
    active: Boolean? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<ModerationActionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/moderation/actions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        active?.let { add("active=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

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
   * Appeal a moderation action on the authenticated user's account
   * Asks the moderators to reconsider an action on the user's own account, once
   * per action and until its `appeal_deadline` (the instance's appeal window from the
   * action, 180 days by default; for a `ban`, also no later than the deletion of the
   * account 30 days after it). The moderators get an `admin_appeal` notification, and
   * the user an `appeal_decided` one when they decide. A second appeal of the action is a
   * 422 `/api/v1/problems/already-appealed`, an appeal after the deadline a 422
   * `/api/v1/problems/appeal-window-closed`, and an appeal of a `ban` whose account is
   * deleted a 422 `/api/v1/problems/not-appealable`. A `text` that is empty, too long, or
   * contains U+0000 is a 422 naming `text`. An action that is not on the user's account is a
   * 404.
   *
   *
   * Parameters:
   * 	 @param appealModerationActionRequest 
   * 	 @param id The ID of the action.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ModerationAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun appealModerationAction(
    appealModerationActionRequest: AppealModerationActionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<ModerationAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/moderation/actions/${id}/appeal"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(appealModerationActionRequest)
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
