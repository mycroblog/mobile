package social.mycroblog.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.Conversation
import social.mycroblog.api.models.ConversationPage

public class ConversationsClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the direct messages by conversation
   * Every conversation with a `direct` post that the user sent or received, whether or not the
   * user follows its author, the one with the newest such post first. Each shows its newest
   * direct post that the user may see, as the home timeline would show it: the posts of
   * accounts that the user blocks, is blocked by, or mutes, and of suspended accounts and
   * domains, are skipped for the one before, and a conversation with none left is left out.
   * The user's timeline languages do not apply. A conversation that the user hid
   * (`hideConversation`) is left out until a newer direct post arrives in it.
   *
   * A page may hold fewer conversations than `limit` while `next_cursor` is still given, when
   * some have no post left that the user may see.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.ConversationPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listConversations(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<ConversationPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/conversations""")
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
   * Get a conversation of the direct messages
   * The conversation as `listConversations` shows it, even when the user hid it. A conversation
   * in which the user sent or received no direct post, or none that the user may see, is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the conversation. The ID of a conversation that was merged into another, once a
   * reply showed both to be one thread, names the conversation it was merged into.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Conversation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getConversation(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<Conversation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/conversations/${id}"""

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
   * Mark a conversation read
   * Marks the conversation read up to its newest direct post, on every device of the user:
   * `unread` is false until a newer direct post by another account arrives. The user's open
   * streams receive the conversation again (`conversation`). Marking it again changes nothing.
   * A conversation in which the user sent or received no direct post is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the conversation. The ID of a conversation that was merged into another, once a
   * reply showed both to be one thread, names the conversation it was merged into.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.Conversation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun markConversationRead(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Conversation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/conversations/${id}/read"""

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
   * Hide a conversation
   * Takes the conversation out of `listConversations` until a newer direct post arrives in it,
   * on every device of the user. Its posts stay, and so do they for the other accounts. Hiding
   * it again changes nothing. A conversation in which the user sent or received no direct post
   * is a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the conversation. The ID of a conversation that was merged into another, once a
   * reply showed both to be one thread, names the conversation it was merged into.
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
  public suspend fun hideConversation(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/conversations/${id}/hide"""

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
