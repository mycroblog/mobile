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
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import kotlinx.serialization.json.JsonElement
import social.mycroblog.api.models.VoiceGrant
import social.mycroblog.api.models.VoiceHostChoice
import social.mycroblog.api.models.VoiceMic
import social.mycroblog.api.models.VoiceReaction
import social.mycroblog.api.models.VoiceRoom
import social.mycroblog.api.models.VoiceRoomHistoryPage
import social.mycroblog.api.models.VoiceRoomInvitation
import social.mycroblog.api.models.VoiceRoomInvitationCreate
import social.mycroblog.api.models.VoiceRoomInvitationPage
import social.mycroblog.api.models.VoiceRoomJoin
import social.mycroblog.api.models.VoiceRoomPage
import social.mycroblog.api.models.VoiceRoomSettings
import social.mycroblog.api.models.VoiceRoomSummary
import social.mycroblog.api.models.VoiceRoomUpdate
import social.mycroblog.api.models.VoiceSession
import social.mycroblog.api.models.VoiceSignal

public class VoiceClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the open voice chat rooms
   * The open voice chat rooms that the user sees: the rooms where the user follows the host or
   * a speaker first, then by the most participants. With `tag`, only the rooms with that
   * hashtag.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param tag A hashtag, with or without the `#`, that the rooms have.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listVoiceRooms(
    limit: Int? = null,
    tag: String? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoomPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/voice/rooms""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        tag?.let { add("tag=${it}") }
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
   * Open a voice chat room
   * Opens a room that the user hosts and joins the user to it as a speaker, with a new session.
   * The user must be a local, unrestricted user who may start rooms (the instance setting
   * `voice_room_starters`, or the `start_voice_rooms` permission): a 403
   * `/api/v1/problems/voice-not-eligible` otherwise. A 409 `/api/v1/problems/voice-too-many-rooms`
   * while as many rooms are open as the server allows, and a 409
   * `/api/v1/problems/voice-hosting-elsewhere` while the user hosts another open room. Joining
   * leaves the room the user was in. Invalid settings are a 422.
   * With `post`, the user's start post, which links the room, is posted with it: a 422 for a
   * room of the `following`, `mutuals`, or `invited` visibility, which has none, and the errors of posting a
   * status for a post that cannot be posted. The local followers of the user who see the room
   * get a `voice_room_started` notification.
   *
   *
   * Parameters:
   * 	 @param voiceRoomSettings 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomJoin] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun openVoiceRoom(voiceRoomSettings: VoiceRoomSettings, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<VoiceRoomJoin> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceRoomSettings)
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
   * Get a voice chat room
   * An open room that the user sees. A room that ended or that the user does not see is a
   * 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getVoiceRoom(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}"""

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
   * Change the settings of a voice chat room
   * Changes the settings of a room that the user hosts. The participants stay whatever the new
   * settings. A 403 `/api/v1/problems/voice-not-host` for a user who is not the host, and a 422
   * for invalid settings.
   *
   *
   * Parameters:
   * 	 @param voiceRoomUpdate 
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun updateVoiceRoom(
    voiceRoomUpdate: VoiceRoomUpdate,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}"""

    return try {
      val response = httpClient.patch(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceRoomUpdate)
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
   * End a voice chat room
   * Ends a room that the user hosts, for every participant. A 403
   * `/api/v1/problems/voice-not-host` for a user who is not the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
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
  public suspend fun endVoiceRoom(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}"""

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
   * Join a voice chat room
   * Joins a room that the user sees, as a listener, with a new session. A user who is in the
   * room already, from another device, keeps the place with a new session, and the previous
   * session is disconnected. Joining leaves the room the user was in; the host of an open room
   * leaves it first (a 409 `/api/v1/problems/voice-hosting-elsewhere`). A 409
   * `/api/v1/problems/voice-room-full` for a room that holds as many people as its
   * `max_participants` allows, also after the host lowered it below the people in the room.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomJoin] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun joinVoiceRoom(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<VoiceRoomJoin> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participation"""

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
   * Leave a voice chat room
   * Leaves a room that the user is in. The host of a room with other participants chooses the
   * next host (`next_host_id`, a participant who may host rooms) or ends the room (`end`): a
   * 409 `/api/v1/problems/voice-host-must-choose` without either, and a 422
   * `/api/v1/problems/voice-invalid-next-host` for a next host who may not take over. The room
   * ends when nobody is left in it. With `session`, only that session leaves: a session that no
   * longer holds the user's place in the room (another device joined since) changes nothing,
   * and the answer is a 204.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param end Whether the host ends the room on leaving.
   * 	 @param session The `session` of the join that leaves.
   * 	 @param nextHostId The participant who becomes the host when the host leaves.
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
  public suspend fun leaveVoiceRoom(
    id: UUID,
    end: Boolean? = null,
    session: String? = null,
    nextHostId: UUID? = null,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/voice/rooms/${id}/participation""")
      val params = buildList {
        end?.let { add("end=${it}") }
        session?.let { add("session=${it}") }
        nextHostId?.let { add("next_host_id=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

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
   * Keep a session of a voice chat room alive
   * Tells the server that the session is alive; the client sends it every 15 seconds. A session
   * without a heartbeat for 45 seconds leaves the room; the host keeps the room for 2 more
   * minutes, and then the earliest co-host who may host rooms takes over, or the room ends. A
   * session that is not the user's current session in the room is a 404: the client joins
   * again.
   *
   *
   * Parameters:
   * 	 @param voiceSession 
   * 	 @param id The ID of the voice chat room.
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
  public suspend fun sendVoiceHeartbeat(
    voiceSession: VoiceSession,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/heartbeat"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceSession)
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
   * Hand a voice chat room over to another host
   * Makes a participant who may host rooms the host of a room that the user hosts; the user
   * stays in the room. A 403 `/api/v1/problems/voice-not-host` for a user who is not the host,
   * and a 422 `/api/v1/problems/voice-invalid-next-host` for a participant who may not take
   * over.
   *
   *
   * Parameters:
   * 	 @param voiceHostChoice 
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun handOverVoiceRoom(
    voiceHostChoice: VoiceHostChoice,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/host"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceHostChoice)
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
   * Become a speaker in a voice chat room
   * Makes the user, a listener in the room, a speaker. The host, the co-hosts, the users whom
   * the room's speakers setting covers, and the users with a permission to speak (a granted
   * raised hand, a grant, or an invitation as a speaker) may; a user whom a moderator limited
   * needs a permission. A 403 `/api/v1/problems/voice-cannot-speak` for anyone else, who raises a
   * hand instead. A raised hand goes down. A speaker stays one.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun becomeVoiceSpeaker(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/speaker"""

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
   * Go back to listening in a voice chat room
   * Makes the user, a speaker in the room, a listener again. A permission to speak stays, so
   * the user may speak again. A listener stays one.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun becomeVoiceListener(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/speaker"""

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
   * Raise a hand in a voice chat room
   * Asks the host and the co-hosts for a permission to speak. A raised hand stays up, and a
   * speaker raises none.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun raiseVoiceHand(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/hand"""

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
   * Lower a hand in a voice chat room
   * Withdraws the user's request to speak. A hand that is down stays down.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun lowerVoiceHand(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/hand"""

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
   * Remove a participant from a voice chat room
   * Takes a participant out of the room, by the host or a co-host, and bans the account from
   * the room: it neither sees nor joins the room again. The host and the co-hosts do not remove
   * each other. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host, or a co-host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun removeVoiceParticipant(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}"""

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
   * Let a participant of a voice chat room speak
   * Gives a participant a permission to speak, by the host or a co-host, with or without a
   * raised hand: a raised hand is granted, and the participant then becomes a speaker. The
   * permission lasts while the room is open, until it is revoked. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun grantVoiceSpeaking(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/speaking_permission"""

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
   * Stop a participant of a voice chat room from speaking
   * Makes a speaker a listener again, by the host or a co-host, and takes back the
   * participant's permission to speak. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun revokeVoiceSpeaking(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/speaking_permission"""

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
   * Dismiss the raised hand of a participant of a voice chat room
   * Turns down a participant's request to speak, by the host or a co-host. A hand that is down
   * stays down. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun dismissVoiceHand(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/hand"""

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
   * Mute a participant of a voice chat room
   * Turns the microphone of a participant off, by the host or a co-host. The participant may
   * turn it on again. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
  public suspend fun muteVoiceParticipant(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/mute"""

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
   * Make a participant of a voice chat room a co-host
   * Makes a participant a co-host, by the host; a room has at most 3 co-hosts (a 409
   * `/api/v1/problems/voice-too-many-cohosts`). The participants in a block relation, either
   * way, with the new co-host leave the room. A co-host stays a co-host while the participation
   * lasts: one who leaves and joins again is a participant until the host makes them a co-host
   * again. A 403 `/api/v1/problems/voice-not-host` for anyone but the host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host, or one
   * in a block relation with the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun assignVoiceCohost(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/cohost"""

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
   * Stop a participant of a voice chat room being a co-host
   * Makes a co-host a participant without the role of a co-host, by the host. A 403
   * `/api/v1/problems/voice-not-host` for anyone but the host, and a 422 `/api/v1/problems/voice-invalid-participant` for an account that is not in the room, the user, or the host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoom] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun removeVoiceCohost(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoom> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/participants/${accountId}/cohost"""

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
   * List the invitations of a voice chat room
   * Lists the invitations of the room that were not revoked, oldest first, for the host and
   * the co-hosts. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomInvitationPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listVoiceInvitations(
    id: UUID,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoomInvitationPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/voice/rooms/${id}/invitations""")
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
   * Invite a user to a voice chat room
   * Invites a local user to the room as a listener or as a speaker, by the host or a co-host:
   * the user sees the room from now on, and an invitation as a speaker lets the user speak
   * without asking. A user is invited to a room once (a 409 `/api/v1/problems/voice-already-invited`,
   * also after a revocation). A 422 `/api/v1/problems/voice-invalid-invitee` for a user who is not
   * a local, active user, is the host or the user, is in a block relation with the host or a
   * co-host, or was removed from the room. A room sends at most 100 invitations an hour (a 429
   * with `Retry-After`). A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host.
   *
   *
   * Parameters:
   * 	 @param voiceRoomInvitationCreate 
   * 	 @param id The ID of the voice chat room.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomInvitation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun inviteToVoiceRoom(
    voiceRoomInvitationCreate: VoiceRoomInvitationCreate,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoomInvitation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/invitations"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceRoomInvitationCreate)
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
   * Revoke an invitation to a voice chat room
   * Revokes the invitation of a user, by the host or a co-host: the user no longer sees the
   * room unless in it, and the permission to speak of an invitation as a speaker goes too; a
   * participant stays in the room as a speaker or a listener. A 404 for a user without an
   * invitation that was not revoked. A 403 `/api/v1/problems/voice-not-host-or-cohost` for a participant who is neither the host nor a co-host.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param accountId The ID of the account in the voice chat room.
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
  public suspend fun revokeVoiceInvitation(
    id: UUID,
    accountId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/invitations/${accountId}"""

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
   * Stream the events of a voice chat room
   * A Server-Sent Events stream (`text/event-stream`) of a voice chat room for one session of
   * the user in it. Read it with `fetch` and send the access token in `Authorization`, like
   * every other operation (`EventSource` cannot send it). Only the user's current session in
   * the room, while it sends heartbeats, opens a stream; any other is a 404. A session holds
   * one stream: opening it again ends the one opened before.
   *
   * The stream starts with a comment line (`: connected`) and a `state` event, and a comment
   * line (`: heartbeat`) arrives every 15 seconds. Opening the stream does not keep the session
   * alive: keep sending its heartbeats. Events:
   *
   * - `state`: the room and its participants changed. `data` is a `VoiceRoom`; each
   *   participant has its `session` while it is alive, `blocked` marks the participants in a
   *   block relation with the user, and `can_speak` is the user's. At most one `state` arrives
   *   each second, with the newest room.
   * - `mic`: a participant turned the microphone on or off. `data` is a `VoiceStreamMic`.
   * - `reaction`: a participant reacted. `data` is a `VoiceStreamReaction`. The reactions of the
   *   participants in a block relation with the user do not arrive.
   * - `signal`: a signal from another session of the room (`POST /voice/rooms/{id}/signals`).
   *   `data` is a `VoiceStreamSignal`.
   * - `muted`: the host or a co-host muted the session; turn the microphone off. `data` is a
   *   `VoiceStreamMuted`.
   * - `removed`, `replaced`, `ended`: the session left the room, the user joined from another
   *   device, or the room ended. `data` is a `VoiceStreamEnd`, and the stream ends after it.
   *
   * When the stream ends otherwise, open it again with the same session; the first `state`
   * brings the room up to date, and nothing is replayed. While the server cannot receive the
   * events (it is shutting down) the answer is 503 with `Retry-After`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   * 	 @param session The `session` of the join.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlinx.serialization.json.JsonElement] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun streamVoiceRoom(
    id: UUID,
    session: String,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<JsonElement> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/voice/rooms/${id}/stream""")
      val params = buildList {
        add("session=${session}")
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
   * Send a signal to another session of a voice chat room
   * Relays a WebRTC signal of a room whose method is `p2p` from the user's session to another
   * live session of the room, which receives it as a `signal` event of its stream. Two sessions
   * connect when at least one of them is a speaker; of the two, the session with the greater ID
   * sends the first offer. The server relays only audio: the `sdp` of an offer or an answer
   * has `m=audio` sections only, and a participant who may not speak only receives
   * (`recvonly` or `inactive`). A signal it does not relay is a 422 with the field that is
   * wrong (`kind`, `to`, `sdp`, or `candidate`): a `to` that is not a live session of the room
   * that the user's session connects to, or an `sdp` and a `candidate` of more than 16 KiB.
   * A session sends at most 20 signals each second; past it the answer is 429 with
   * `Retry-After`. A signal that its receiver's stream does not take within 30 seconds is
   * lost; restart ICE. A session that is not the user's current session in the room is a 404.
   *
   *
   * Parameters:
   * 	 @param voiceSignal 
   * 	 @param id The ID of the voice chat room.
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
  public suspend fun sendVoiceSignal(
    voiceSignal: VoiceSignal,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/signals"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceSignal)
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
   * Turn the microphone on or off in a voice chat room
   * Tells the participants that the user's microphone is on or off, as a `mic` event of their
   * streams; the server records nothing. Only a speaker turns it on (403
   * `/api/v1/problems/voice-cannot-speak`). After the host or a co-host muted the session,
   * turning it on lifts the mute. A session that is not the user's current session in the room
   * is a 404.
   *
   *
   * Parameters:
   * 	 @param voiceMic 
   * 	 @param id The ID of the voice chat room.
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
  public suspend fun setVoiceMic(
    voiceMic: VoiceMic,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/mic"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceMic)
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
   * Get a new grant for a session of a voice chat room
   * Returns a new grant for the user's session, with new credentials of the STUN and TURN
   * servers; the client asks for it 10 minutes before its grant expires, sets the new ICE
   * servers on its connections, and restarts ICE. A session that is not the user's current
   * session in the room is a 404, and a 503 `/api/v1/problems/voice-unavailable` when the
   * server cannot give a grant now.
   *
   *
   * Parameters:
   * 	 @param voiceSession 
   * 	 @param id The ID of the voice chat room.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceGrant] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun refreshVoiceGrant(
    voiceSession: VoiceSession,
    id: UUID,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceGrant> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/grant"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceSession)
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
   * React in a voice chat room
   * Sends a reaction of the user, a participant of the room, to the participants, as a
   * `reaction` event of their streams; the server records nothing, and the participants in a
   * block relation with the user do not receive it. The emoji is a Unicode emoji or the
   * `:shortcode:` of an enabled custom emoji of this server; any other is a 422. A participant
   * sends at most 10 reactions in 10 seconds; past it the answer is 429 with `Retry-After`. A
   * 404 for a room the user is not in, and while the voice chat is off on the server.
   *
   *
   * Parameters:
   * 	 @param voiceReaction 
   * 	 @param id The ID of the voice chat room.
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
  public suspend fun reactInVoiceRoom(
    voiceReaction: VoiceReaction,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/reactions"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(voiceReaction)
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
   * Get the summary of a voice chat room
   * What the page of a room shows to a visitor who does not see the room: whether it is open or
   * ended, and its name, description, and host only when the server shows the summaries of its
   * rooms and the room is for every local user. It needs no sign-in and is the same for every
   * visitor. An unknown room is a 404, and so is every room while the voice chat is off on the
   * server.
   *
   *
   * Parameters:
   * 	 @param id The ID of the voice chat room.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomSummary] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getVoiceRoomSummary(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<VoiceRoomSummary> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/voice/rooms/${id}/summary"""

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
   * List the voice chat rooms the user hosted
   * The rooms that the user hosted at some time, from opening them or from taking them over,
   * newest first, with when each opened and ended and why it ended.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.VoiceRoomHistoryPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listVoiceHistory(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<VoiceRoomHistoryPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/voice/history""")
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
}
