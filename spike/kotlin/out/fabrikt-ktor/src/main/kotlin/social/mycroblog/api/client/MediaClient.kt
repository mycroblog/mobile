package social.mycroblog.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.MediaAttachment
import social.mycroblog.api.models.MediaUpdate

public class MediaClient(
  private val httpClient: HttpClient,
) {
  /**
   * Upload a media file
   * Uploads an image to attach to a post or to use as an avatar, a header, or a custom
   * emoji (`purpose`), or a video or audio file to attach to a post. The server checks the
   * format from the content (images: JPEG, PNG, GIF, or WebP, the last two animated or not;
   * video: MP4, QuickTime, or WebM of H.264, VP8, VP9, or AV1; audio: MP3, Ogg, M4A, or WebM
   * of AAC, Opus, Vorbis, or MP3; the declared type must match it) and the size, dimension,
   * and duration limits before it accepts the file, then processes it in the background: it
   * removes the metadata (such as the location) by re-encoding the image into small,
   * medium, and large variants for its purpose, still and, for an animation, moving ones,
   * and makes a blurhash. A video or audio file is not re-encoded: the server writes its
   * streams again without metadata, and makes the variants and the blurhash of its
   * `thumbnail`, or of the cover image in the file. The answer is 202 with the file in its
   * current `state`, usually `processing`; poll `getMedia` until it is `ready` or
   * `failed`. A file used for another purpose later is processed again for it.
   *
   * The size limits of images and of video and audio files differ, and the limits of video
   * and audio files are set by the operator (`media_max_av_bytes`,
   * `media_max_video_duration`, `media_max_audio_duration` in the server configuration).
   *
   * A body larger than the size limit is refused with a 413 before it is read. Uploads
   * count against a limit per account besides the API-wide one. Only the uploader can
   * read and change the file.
   *
   *
   * Parameters:
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.MediaAttachment] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun uploadMedia(idempotencyKey: String? = null, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<MediaAttachment> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/media"""

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
   * Get a media file that the user uploaded
   * Returns a media file of the authenticated user with its processing state. A file of
   * another account is a 404: other accounts see a file only as an attachment of a post.
   *
   *
   * Parameters:
   * 	 @param id The ID of the media file.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.MediaAttachment] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getMedia(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<MediaAttachment> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/media/${id}"""

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
   * Change the description or focal point of a media file
   * Changes the description (alternative text) or the focal point of a media file of the
   * authenticated user that no post attaches yet; a property left out keeps its value. A
   * post copies the description when it attaches the file, so a file that a post attaches
   * is a 409 `media-attached`: edit the post instead. A file of another account is a 404.
   *
   *
   * Parameters:
   * 	 @param mediaUpdate 
   * 	 @param id The ID of the media file.
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.MediaAttachment] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun updateMedia(
    mediaUpdate: MediaUpdate,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<MediaAttachment> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/media/${id}"""

    return try {
      val response = httpClient.patch(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(mediaUpdate)
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
