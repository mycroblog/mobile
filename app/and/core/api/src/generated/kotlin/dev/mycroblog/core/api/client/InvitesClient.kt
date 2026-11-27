package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.CreateOwnInviteRequest
import dev.mycroblog.core.api.models.OwnInvite
import dev.mycroblog.core.api.models.OwnInvitePage
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
import kotlin.Int
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException

public class InvitesClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the user's own invites
   * The invites that the user created as a member, newest first, with how they
   * have been used, and what the user may do: whether they may create an invite now
   * (`can_create`) and why not (`cannot_create_reason`), the caps of the instance settings,
   * and how many usable invites they hold.
   * A user who may no longer create invites still lists and revokes the ones they made; an
   * otherwise usable one is then `creator_ineligible`. Invites of the staff
   * (`/admin/invites`) are not listed.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.OwnInvitePage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listOwnInvites(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OwnInvitePage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/invites""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
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
   * Create an invite of the user's own
   * Creates an invite with a new random code that people use to sign up while the
   * registration mode is `open`, `approval`, or `invite`. In `approval` mode a
   * person who signs up with it is approved at once. The user may create one when the
   * instance setting `invite_creators` is `everyone` or their roles have `create_invites`,
   * their account is neither suspended nor limited, their registration is approved, and the
   * registration mode is not `closed`; otherwise a 403 `/api/v1/problems/invites-not-allowed`
   * whose `reason` says which.
   * `max_uses` defaults to 1 and `expires_in` to 7 days (or the caps when they are lower); a
   * value over the cap of the instance settings (`member_invite_max_uses`,
   * `member_invite_max_lifetime_seconds`) is a 422 naming it. A user who already holds
   * `member_invite_max_active` usable invites gets a 409
   * `/api/v1/problems/too-many-invites`. The creation is recorded in the audit log with the
   * user as the actor. Every sign-up with the invite checks again that the user may create
   * invites; while they may not, the invite does not work.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param createOwnInviteRequest 
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
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.OwnInvite] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createOwnInvite(
    createOwnInviteRequest: CreateOwnInviteRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OwnInvite> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/invites"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createOwnInviteRequest)
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
   * Revoke an invite of the user's own
   * The invite stops working at once; the people who signed up with it keep their accounts.
   * Revoking a revoked invite changes nothing. The revocation is recorded in the audit log
   * with the user as the actor. A user revokes their own invites even when they may no longer
   * create invites. An unknown ID, or an invite that the user did not create as a member, is
   * a 404.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the invite.
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
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.OwnInvite] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun revokeOwnInvite(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OwnInvite> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/invites/${id}/revoke"""

    return try {
      val response = httpClient.post(url) {
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
