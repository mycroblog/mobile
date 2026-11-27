package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.ClientErrorReport
import dev.mycroblog.core.api.models.ClientMeasurementReport
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException

public class ClientErrorsClient(
  private val httpClient: HttpClient,
) {
  /**
   * Report an uncaught error of the web client
   * Reports an error that the web client did not handle, for the operators of the server. The
   * body is at most 16 KiB of `application/json`. A report that reaches the operation is
   * answered 204, which never tells whether it was kept; the limit of requests per client IP
   * (429) and an unavailable server (503) answer as for every operation. The server ignores a body of another content type or over the
   * size, a body it cannot read, and a report whose `stack` has no frame of a script that this
   * server serves, such as the errors of a browser extension. It keeps at most 10 reports a
   * minute from each client IP and ignores the rest.
   *
   * The `stack` is the browser's `Error.stack`, in either the V8 form
   * (`at fn (https://host/assets/app-1a2B3c4D.js:1:23)`) or the Firefox and Safari form
   * (`fn@https://host/assets/app-1a2B3c4D.js:1:23`). Only the frames of this server's scripts
   * are kept, without their query or fragment. The server groups the reports by `name`,
   * `message` (with the URLs, addresses, and long numbers in it left out), `route`, and the
   * name of the first script of the stack without its content hash.
   *
   * A report may be sent without an access token, with `navigator.sendBeacon` while the page
   * is left: the server counts it but never notifies the administrators of a new error from
   * it. Only the token of one of the server's own clients counts as signed in; any other is
   * treated as no token. An access token that is sent must be valid, as for every operation.
   *
   *
   * Parameters:
   * 	 @param clientErrorReport 
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
  public suspend fun reportClientError(
    clientErrorReport: ClientErrorReport,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/client_errors"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(clientErrorReport)
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
   * Report samples of how fast the web client responds
   * Reports samples of how fast the web client responded, for the operators of the server: up
   * to 50 of them in a body of at most 16 KiB of `application/json`. A report that reaches the
   * operation is answered 204, which never tells whether it was kept; the limit of requests per
   * client IP (429) and an unavailable server (503) answer as for every operation. The server
   * ignores a body of another content type or over the size, and a body it cannot read; it
   * drops each sample whose name is not one of its metric or whose value is out of range, keeps
   * at most 10 reports a minute from each client IP, and takes nothing while the instance
   * setting `ux_measurement` is off (`ux_measurement` of the instance).
   *
   * The client sends a sample of its page views, and nothing that identifies a person: a sample
   * names a route by its ID, never by its URL. A report may be sent without an access token,
   * with `navigator.sendBeacon` while the page is left. An access token that is sent must be
   * valid, as for every operation.
   *
   *
   * Parameters:
   * 	 @param clientMeasurementReport 
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
  public suspend fun reportClientMeasurements(
    clientMeasurementReport: ClientMeasurementReport,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/client_measurements"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(clientMeasurementReport)
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
