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
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.AppealHookDecisionRequest
import social.mycroblog.api.models.ArticlePreview
import social.mycroblog.api.models.ArticlePreviewRequest
import social.mycroblog.api.models.CreateArticleRequest
import social.mycroblog.api.models.CreateStatusRequest
import social.mycroblog.api.models.EditArticleRequest
import social.mycroblog.api.models.EditStatusRequest
import social.mycroblog.api.models.FollowedHashtagCollection
import social.mycroblog.api.models.Hashtag
import social.mycroblog.api.models.InteractionKind
import social.mycroblog.api.models.InteractionPolicyChange
import social.mycroblog.api.models.InteractionRequest
import social.mycroblog.api.models.InteractionRequestPage
import social.mycroblog.api.models.OwnHookAppeal
import social.mycroblog.api.models.ReactToStatusRequest
import social.mycroblog.api.models.Status
import social.mycroblog.api.models.StatusContext
import social.mycroblog.api.models.StatusHistory
import social.mycroblog.api.models.StatusPage
import social.mycroblog.api.models.StatusReactionPage
import social.mycroblog.api.models.StatusSource
import social.mycroblog.api.models.StatusTranslation
import social.mycroblog.api.models.StatusTranslationRequest
import social.mycroblog.api.models.TagSuggestionList
import social.mycroblog.api.models.VoteInPollRequest

public class StatusesClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the interaction requests to the authenticated user
   * The replies, boosts, and quotes of the user's statuses that wait for the user's approval,
   * newest first, of one kind when `kind` is given. A request whose status the user deleted, or
   * whose account the user may not see, is left out, so a page may hold fewer than `limit`
   * requests before the last.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param kind Only the requests of this kind.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.InteractionRequestPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInteractionRequests(
    limit: Int? = null,
    kind: InteractionKind? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<InteractionRequestPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/interaction_requests""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        kind?.let { add("kind=${it}") }
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
   * Approve an interaction request
   * Approves the reply, boost, or quote: a reply joins the thread of the status, a boost is counted, and a quote shows the status. The account is told, and its server gets the approval (an `Accept` with the authorization).
   * A request that is answered already is a 409; one of another user's status, or of a deleted
   * status, is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the interaction request.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.InteractionRequest] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun acceptInteractionRequest(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<InteractionRequest> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/interaction_requests/${id}/accept"""

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

  /**
   * Refuse an interaction request
   * Refuses the reply, boost, or quote: it stays out of the thread and the counts. The account is told, and its server gets a `Reject`.
   * A request that is answered already is a 409; one of another user's status, or of a deleted
   * status, is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the interaction request.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.InteractionRequest] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun rejectInteractionRequest(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<InteractionRequest> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/interaction_requests/${id}/reject"""

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

  /**
   * Show the user's appeal of a post that the automated review refused
   * Where the user's appeal of a refused post stands: `pending` until a moderator reviews it,
   * then `upheld` (the refusal stays) or `overturned` (the user's posts are no longer reviewed,
   * and the user posts again). A `hook_appeal_decided` notification tells the user when it is
   * reviewed. A decision that the user has not appealed, or that is not about the user's post,
   * is a 404, as is an appeal no longer kept.
   *
   *
   * Parameters:
   * 	 @param id The ID of the decision, `hook_decision_id` of the refusal.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OwnHookAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getOwnHookAppeal(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OwnHookAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/hook_decisions/${id}/appeal"""

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
   * Appeal a post that the automated review refused
   * Asks the moderators to let a refused post through (the 422
   * `/api/v1/problems/status-rejected` of `POST /statuses`), once per decision and until its
   * `appeal_deadline` (the instance's appeal window from the refusal, 180 days by default).
   * People review it; the refused post is not stored, so the appeal sends its text. When a
   * moderator overturns the decision, the user's posts are no longer reviewed and the user posts
   * again. A second appeal is a 422 `/api/v1/problems/already-appealed`, an appeal after the
   * deadline a 422 `/api/v1/problems/appeal-window-closed`, and an appeal of a decision that is
   * not such a refusal a 422 `/api/v1/problems/not-appealable`. A `status_text` that is blank or
   * too long, or a field that contains U+0000, is a 422 naming it. A decision that is not about
   * the user's post is a 404.
   *
   *
   * Parameters:
   * 	 @param appealHookDecisionRequest 
   * 	 @param id The ID of the decision.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OwnHookAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun appealHookDecision(
    appealHookDecisionRequest: AppealHookDecisionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<OwnHookAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/hook_decisions/${id}/appeal"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(appealHookDecisionRequest)
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
   * List the posts the authenticated user bookmarked, newest bookmark first
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listBookmarks(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/bookmarks""")
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
   * List the posts the authenticated user favourited, newest favourite first
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listFavourites(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/favourites""")
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
   * Post a status
   * Posts a status as the authenticated user (201). Retrying with the same `Idempotency-Key`
   * returns the status posted with it, before the post rate limit and the other checks of
   * a new post (a 409 when that status is deleted). Attached media must be the user's own uploads that
   * finished processing (422 otherwise). A status with a `poll` attaches no media, and a
   * poll outside the limits of `PollInput` is a 422.
   * A user whose account moved gets a 403 `/api/v1/problems/account-moved`.
   * When the server's automated review refuses the post, nothing is posted and the answer is a
   * 422 `/api/v1/problems/status-rejected` with fixed text and the decision the user may appeal.
   * On a one-person server (`Instance.single_user`) the visibility `local` is a 422, and
   * `CredentialAccount.posting_limits` leaves it out.
   *
   *
   * Parameters:
   * 	 @param createStatusRequest 
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createStatus(
    createStatusRequest: CreateStatusRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createStatusRequest)
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
   * Post an article
   * Posts an article as the authenticated user (201): a long post with a title, a summary, and
   * a cover, whose body is Markdown. The server renders the body once, now, and the article is
   * a `Status` with `article` from then on: its visibility, edits, interactions, reports, and
   * deletion (`deleteStatus`) are those of a post. The answer carries the body in `content`, as
   * `getStatus` does.
   *
   * The body is CommonMark with the tables, strikethrough, and autolinks of GitHub Flavored
   * Markdown; raw HTML is not rendered, a line break in a paragraph is kept, and `#` is the
   * second level of heading (the title is the first). An image whose destination is
   * `media:` followed by the ID of one of the user's own uploads that finished processing
   * (`![alt text](media:ID)`) shows that image; any other image is a link with its alt text,
   * so that readers never load an image from elsewhere. Mentions link to the accounts but notify
   * nobody, and `#name` in the body stays text: the hashtags are those of `tags`.
   *
   * A body whose HTML is over `Instance.limits.articles.max_bytes` bytes, or with more images
   * than `Instance.limits.articles.max_images`, is a 422; `previewArticle` tells both before
   * posting. An article is never `direct` (422). While the instance lets only some users write
   * articles (`Instance.article_authoring` is `permission`), a user without the permission
   * `create_articles` gets a 403; that user may still edit their earlier articles. Retrying
   * with the same `Idempotency-Key` returns the article posted with it, as `createStatus`
   * does. A user whose account moved gets a 403 `/api/v1/problems/account-moved`. When the
   * server's automated review refuses the article, nothing is posted and the answer is a 422
   * `/api/v1/problems/status-rejected`, as for a post.
   *
   *
   * Parameters:
   * 	 @param createArticleRequest 
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createArticle(
    createArticleRequest: CreateArticleRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/articles"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createArticleRequest)
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
   * Preview the body of an article
   * Renders the Markdown body of an article of the authenticated user as `createArticle` would
   * store it, without storing anything: the same HTML that the published article has, the
   * summary it would get, and the size and images of the body with their limits. A body over
   * the limits still renders, so that the editor can say by how much.
   *
   *
   * Parameters:
   * 	 @param articlePreviewRequest 
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ArticlePreview] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun previewArticle(
    articlePreviewRequest: ArticlePreviewRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<ArticlePreview> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/articles/preview"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(articlePreviewRequest)
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
   * Edit the authenticated user's article
   * Replaces the title, summary, cover, body, hashtags, language, and sensitivity of an
   * article, checked and rendered as `createArticle` does; the previous revision stays in the
   * edit history. The visibility never changes, and who may reply to, boost, and quote it
   * changes with `setStatusInteractionPolicy`. A status that is a post is a 422 (it is edited
   * with `editStatus`), and so is a body over the limits. When the server's automated review
   * refuses the edit, the answer is a 422 `/api/v1/problems/status-rejected` with no decision
   * to appeal, and the article stays as it was. Another account's status is a 403 when the user
   * may see it and a 404 otherwise.
   *
   *
   * Parameters:
   * 	 @param editArticleRequest 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun editArticle(
    editArticleRequest: EditArticleRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/articles/${id}"""

    return try {
      val response = httpClient.put(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(editArticleRequest)
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
   * Search the text of posts
   * The posts that contain every word of `q`, newest first, as `Status` objects like those of a
   * timeline. It looks in the text, the content warning, and the alternative texts of the current
   * version of a post. A word's matching, including in Japanese, depends on the search engine of
   * the server, and there is no order by relevance. `q` is plain text: nothing in it is an operator.
   *
   * Read `Instance.full_text_search` before offering this: it says whether the server has a search
   * engine (`available`) and which visibilities the search covers (`visibilities`). Without one,
   * the response is a 404 of the type `/api/v1/problems/search-unavailable`. When the engine fails,
   * it is a 503 of the type `/api/v1/problems/search-failed`, which only this operation answers;
   * try again later.
   *
   * Only signed-in users can search, and only `public` and `local` posts of accounts with
   * `indexable` turned on are found, within the visibilities the server covers. The results never
   * include `unlisted`, `private`, or `direct` posts, even the viewer's own. Posts of accounts or
   * domains that the viewer blocks, mutes, or is blocked by are left out.
   *
   * A page can hold fewer posts than `limit`, or none at all, while more follow, because posts are
   * left out after the search engine answered. Continue while the response has a `next_cursor`, and
   * stop only when it has none. A cursor works only on the search it came from: changing `q` needs
   * a new first page. A cursor the search engine did not issue is a 400.
   *
   * The query is part of the URL, so a reverse proxy or CDN in front of the server may log it. The
   * server itself writes it to no log or trace, and the response is `private, no-store`.
   *
   * Past 30 searches in 5 minutes by one account, in addition to the limit of the API as a whole,
   * the response is a 429 with `Retry-After`.
   *
   *
   * Parameters:
   * 	 @param q The words to find, 1 to 200 characters. Anything else is a 400.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun searchStatuses(
    q: String,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/statuses/search""")
      val params = buildList {
        add("q=${q}")
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
   * Look up or resolve a status by its URL
   * Finds the status whose ActivityPub id is `uri`, among the statuses the instance knows, or
   * else fetches it from its server, as a reply button of another server asks to (the page that
   * `Create` intents of WebFinger open). A page that serves the post, such as its `url`, is
   * followed to the post's id. The post's parent is fetched too, one level. Posts of this
   * instance are found by their id only.
   *
   * The answer is the status as `getStatus` gives it: a status that the viewer may not see is a
   * 404, the same as a URL that leads to no post, or to an account.
   *
   * Only signed-in users can look a status up (anonymous requests get the 401 of
   * `/api/v1/problems/unauthenticated`). Fetching an unknown status shares the limit of
   * `lookupAccount`'s `resolve`, 30 fetches per account every 5 minutes (429 with
   * `Retry-After`); finding a known one is not limited. While the instance takes nothing in from
   * other servers (`Instance.federation.mode` `off` or `outbound`), only the known statuses are
   * found: an unknown one is a 403 `/api/v1/problems/federation-restricted`, and nothing is
   * fetched.
   *
   *
   * Parameters:
   * 	 @param uri The `https` URL of the post.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun lookupStatus(uri: String, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/statuses/lookup""")
      val params = buildList {
        add("uri=${uri}")
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
   * Get a status
   * A status that the viewer may not see is a 404, the same as a missing one. An article comes
   * with its body in `content`. A post of the board of an event (`listEventBoard`) is seen only
   * by the users who read the board.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getStatus(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}"""

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
   * Edit the authenticated user's status
   * Replaces the text, content warning, sensitivity, language, and media of a status; the
   * previous content stays in the edit history. The visibility and reply target never
   * change. Attached media must be the user's own uploads that finished processing (422
   * otherwise). A status with a poll cannot be edited (422): its votes belong to its poll. An
   * article is edited with `editArticle` (422 here).
   * When the server's automated review refuses the edit, the answer is a 422
   * `/api/v1/problems/status-rejected` with fixed text and no decision to appeal, and the
   * status stays as it was. Another account's status is a 403 when the user may see it and a 404 otherwise. A `direct`
   * status is a 404 to a token without `read:direct`, the user's own included.
   * A post of the board of an event is edited here while its author may post on the board: a 403
   * `/api/v1/problems/event-board-not-reader` or `/api/v1/problems/event-board-not-writer`
   * otherwise, and a 409 `/api/v1/problems/event-board-closed` once the board is read-only.
   *
   *
   * Parameters:
   * 	 @param editStatusRequest 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun editStatus(
    editStatusRequest: EditStatusRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}"""

    return try {
      val response = httpClient.put(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(editStatusRequest)
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
   * Delete the authenticated user's status
   * Another account's status is a 403 when the user may see it and a 404 otherwise. A `direct`
   * status is a 404 to a token without `read:direct`, the user's own included, and nothing
   * is deleted. The author deletes a post of the board of an event here, the board read-only or
   * not.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
  public suspend fun deleteStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}"""

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
   * Bookmark a status
   * Bookmarking a status that is already bookmarked changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun bookmarkStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/bookmark"""

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

  /**
   * Remove the bookmark of a status
   * Without a bookmark it changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unbookmarkStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/bookmark"""

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
   * Mute the thread of a status
   * Mutes the thread (conversation) that the status belongs to: from then on, replies and
   * mentions in it, boosts, favourites, reactions, and quotes of its posts, and new posts
   * in it by accounts the user is notified of no longer notify the user, the user's own
   * posts in the thread included. Notifications that came before stay, and the posts still
   * appear in timelines. The mute has no expiry and follows the thread when it is joined
   * with another. Muting a thread that is already muted changes nothing; the status's
   * `muted` tells the state.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun muteStatusThread(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/mute"""

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

  /**
   * Unmute the thread of a status
   * Ends the mute of the thread that the status belongs to. What happened in the thread
   * while it was muted does not notify the user afterwards. Without a mute it changes
   * nothing.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unmuteStatusThread(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/mute"""

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
   * Boost a status
   * Boosting a status that is already boosted changes nothing. A status whose visibility
   * does not allow boosting is a 422.
   * A user whose account moved gets a 403 `/api/v1/problems/account-moved`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun boostStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/boost"""

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

  /**
   * Retract the boost of a status
   * Without a boost it changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unboostStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/boost"""

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
   * Get the thread around a status
   * The ancestors and descendants of a status that the viewer may see. A status that the
   * viewer may not see is a 404, the same as a missing one. For a remote status, any request,
   * an anonymous one included, may start fetching the rest of its thread from other servers;
   * a thread is fetched once at a time, 20 levels deep and at most 100 documents, so the
   * answer may grow on a later request.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusContext] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getStatusContext(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<StatusContext> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/context"""

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
   * Favourite a status
   * Favouriting a status that is already favourited changes nothing. A user has one
   * favourite or one emoji reaction on a status, so favouriting takes back the reaction.
   * A user whose account moved gets a 403 `/api/v1/problems/account-moved`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun favouriteStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/favourite"""

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

  /**
   * Remove the favourite of a status
   * Without a favourite it changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unfavouriteStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/favourite"""

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
   * Get the edit history of a status
   * Every revision of a status, oldest first; the last one is the current content. A
   * status that the viewer may not see is a 404, the same as a missing one.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusHistory] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getStatusHistory(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<StatusHistory> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/history"""

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
   * Pin the authenticated user's status to the profile
   * Pinning a status that is already pinned changes nothing. Another account's status is a
   * 403, as for editing it, and a direct status or a pin beyond the limit a 422.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun pinStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/pin"""

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

  /**
   * Unpin a status
   * Without a pin it changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unpinStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/pin"""

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
   * Vote in the poll of a status
   * Votes for one or more choices of the poll of a status. `poll_id` names the poll
   * the user saw: a poll that an edit of the status replaced is a 409. A status without a
   * poll, a poll that has ended, a second vote, or choices the poll does not take (none, an
   * unknown one, the same one twice, or several in a single-choice poll) is a 422. A vote in
   * a poll of another server is sent to that server, whose counts in `poll` include it once
   * the server reports them again.
   * A user whose account moved gets a 403 `/api/v1/problems/account-moved`.
   *
   *
   * Parameters:
   * 	 @param voteInPollRequest 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun voteInPoll(
    voteInPollRequest: VoteInPollRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/poll/votes"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voteInPollRequest)
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
   * React to a status with an emoji
   * Sets the authenticated user's emoji reaction to a status: a Unicode emoji, or a custom
   * emoji of this server that is enabled. A user has one favourite or one reaction on a
   * status, so reacting takes back the favourite and the reaction with another emoji;
   * reacting again with the same emoji changes nothing. A custom emoji of another server,
   * or anything else that is not one emoji, is a 422.
   * A user whose account moved gets a 403 `/api/v1/problems/account-moved`.
   *
   *
   * Parameters:
   * 	 @param reactToStatusRequest 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun reactToStatus(
    reactToStatusRequest: ReactToStatusRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/reaction"""

    return try {
      val response = httpClient.put(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(reactToStatusRequest)
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
   * Take back the emoji reaction to a status
   * Without a reaction it changes nothing.
   *
   * Parameters:
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unreactToStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/reaction"""

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
   * List the emoji reactions to a status
   * The emoji reactions to a status that the viewer may see, newest first, each with the
   * account that reacted, only those with one emoji when `emoji` is given. Accounts that
   * block the viewer, are deactivated, or are deleted are left out, so a page may hold fewer
   * than `limit` reactions before the last. A status that the viewer may not see is a 404,
   * the same as a missing one.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param emoji The `name` of an emoji (`ReactionEmoji`) to list the reactions with; an emoji that no
   * reaction has gives an empty page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusReactionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listStatusReactions(
    id: UUID,
    limit: Int? = null,
    emoji: String? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusReactionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/statuses/${id}/reactions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        emoji?.let { add("emoji=${it}") }
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
   * Translate a post into a language
   * Returns the current version of a post in `target_language`: its text, content warning, the
   * alt text of its media, and its poll options. A post that the viewer cannot see is a 404, as
   * for `GET /statuses/{id}`, so a token without `read:direct` gets a 404 for a direct post.
   *
   * `provider` is omitted when no machine translated the answer: the post already has a body in
   * the language, whose author wrote it, or it has no words to translate. Otherwise the server
   * answers with a translation that it keeps, or asks the providers that its operator enabled.
   * Only the text is sent to a provider, with no address, author, viewer, mention, or link.
   * Characters sent count against monthly limits of the user and of the server, whichever app
   * asks. `read:statuses` is enough on purpose: a translation is a way to read a post and changes
   * nothing of the user's, so any app that shows posts may offer it, and the user's monthly limit
   * bounds what one app can spend.
   *
   * A 422 `/api/v1/problems/translation-unavailable` when no provider may receive this post: none
   * is enabled, or none is allowed its visibility. A 422
   * `/api/v1/problems/translation-unsupported-language` when the providers do not translate the
   * language, and a 422 without a type for a `target_language` that is not a language tag. A 429
   * `/api/v1/problems/translation-limit` over a monthly limit, until the next month in UTC, with
   * `Retry-After` the seconds until then. A 503
   * `/api/v1/problems/translation-failed` when no provider returned a usable translation, which
   * may pass.
   *
   *
   * Parameters:
   * 	 @param statusTranslationRequest 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusTranslation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun translateStatus(
    statusTranslationRequest: StatusTranslationRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusTranslation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/translation"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(statusTranslationRequest)
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
   * Change who may reply to, boost, and quote the authenticated user's status
   * Changes the `interaction_policy` of a status of the user for the kinds the request has;
   * the others stay. Interactions approved before stay, and an approval of a quote is revoked
   * on its own. Another account's status is a 403 when the user may see it and a 404
   * otherwise. A `direct` status is a 404 to a token without `read:direct`, the user's own
   * included.
   *
   *
   * Parameters:
   * 	 @param interactionPolicyChange 
   * 	 @param id The ID of the status.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Status] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun setStatusInteractionPolicy(
    interactionPolicyChange: InteractionPolicyChange,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Status> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/interaction_policy"""

    return try {
      val response = httpClient.put(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(interactionPolicyChange)
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
   * List the statuses that quote the authenticated user's status
   * The statuses that quote a status of the user, newest first, each with its `quote` and
   * its state. Statuses the user may not see, and quotes taken away, are left
   * out, so a page may hold fewer than `limit` statuses before the last. Another account's
   * status is a 404, and so is the user's own `direct` status without `read:direct`, which
   * also leaves out the `direct` statuses that quote one.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listStatusQuotes(
    id: UUID,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/statuses/${id}/quotes""")
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
   * Revoke the approval of a quote of the authenticated user's status
   * Withdraws this server's approval of the quote: the quote shows the quoted
   * status no more, and the quoting server is told. A quote without this server's approval
   * (from software without approvals, or of the user's own status) is a 404, as is one revoked
   * already.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   * 	 @param quoteId The ID of the status that quotes.
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
  public suspend fun revokeStatusQuoteApproval(
    id: UUID,
    quoteId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/quotes/${quoteId}/approval"""

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
   * Get the source of the authenticated user's status, for editing it
   * Another account's status is a 404, the same as a missing one, and so is a `direct` status
   * without `read:direct`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the status.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.StatusSource] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getStatusSource(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<StatusSource> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/statuses/${id}/source"""

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
   * Search hashtags by prefix
   * The hashtags whose name starts with `q`, for the autocomplete of hashtags in the compose
   * form and the hashtag search. `q` and the names are compared
   * under NFKC and lowercase.
   *
   * Only the hashtags that a post of visibility `public`, `local`, or `unlisted` carries now
   * are found: a hashtag of private and direct posts only, or one whose posts are all deleted
   * or edited away, is left out. The results come in this order: the hashtag equal to `q`,
   * then those carried by more posts, then the more recently used. There is no paging.
   *
   *
   * Parameters:
   * 	 @param q The start of a hashtag: 1 to 100 characters after a leading `#`, which is ignored.
   * Anything else is a 400.
   * 	 @param limit The maximum number of results of a prefix search for the autocomplete.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.TagSuggestionList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun searchTags(
    q: String,
    limit: Int? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<TagSuggestionList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/tags/search""")
      val params = buildList {
        add("q=${q}")
        limit?.let { add("limit=${it}") }
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
   * Get a hashtag
   * The hashtag and whether the user follows it. A hashtag that no post has used yet is
   * answered too, so that it can be followed.
   *
   *
   * Parameters:
   * 	 @param hashtag The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters,
   * marks, digits, and underscores with at least one letter or mark. Anything else is a 422
   * naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the
   * search of hashtags.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Hashtag] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getTag(hashtag: String, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<Hashtag> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/tags/${hashtag}"""

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
   * Follow a hashtag
   * The home timeline then also shows the `public` and `local` posts with the hashtag that the
   * server has, from accounts the user does not follow, as the hashtag's timeline shows them:
   * without boosts, and without the posts of accounts and servers kept from public timelines
   * or that the user blocks or mutes. Such a post carries `followed_tags`. Following a
   * hashtag that the user follows changes nothing, and a hashtag that no post has used yet
   * can be followed. A user follows at most 100 hashtags; one more is a 422
   * `/api/v1/problems/too-many-followed-tags`.
   *
   *
   * Parameters:
   * 	 @param hashtag The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters,
   * marks, digits, and underscores with at least one letter or mark. Anything else is a 422
   * naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the
   * search of hashtags.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Hashtag] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun followTag(
    hashtag: String,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Hashtag> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/tags/${hashtag}/follow"""

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

  /**
   * Unfollow a hashtag
   * Without a follow it changes nothing.
   *
   * Parameters:
   * 	 @param hashtag The hashtag, with or without a leading `#` (`%23`), matched under NFKC and lowercase: letters,
   * marks, digits, and underscores with at least one letter or mark. Anything else is a 422
   * naming `hashtag`. The hashtag `search` is written `%23search`, since `/tags/search` is the
   * search of hashtags.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Hashtag] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unfollowTag(
    hashtag: String,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Hashtag> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/tags/${hashtag}/follow"""

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
   * List the hashtags the user follows
   * Every hashtag that the user follows (at most 100), the latest followed first.
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.FollowedHashtagCollection] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listFollowedTags(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<FollowedHashtagCollection> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/followed_tags"""

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
}
